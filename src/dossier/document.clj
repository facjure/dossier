(ns dossier.document
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as esi]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojurewerkz.elastisch.query :as q]
            [clj-http.conn-mgr :as conn-mgr]
            [zendown.core :as zen]
            [dossier.utils :refer :all]))

(defn connect [url]
  "Connects to ES using a Persistent Connection. Returns a map of conn and index.
   https://github.com/dakrone/clj-http#using-persistent-connections"
  (esr/connect url {:connection-manager
                    (conn-mgr/make-reusable-conn-manager {:timeout 10})}))

(def document-schema
  "A Schema to hold Documents"
  {:date      {:type "string" :store "yes" :index "not_analyzed"}
   :title     {:type "string" :store "yes" :analyzer "snowball"}
   :author    {:type "string" :store "yes" :analyzer "snowball"}
   :bio       {:type "string" :store "yes" :analyzer "snowball"}
   :gender    {:type "string" :store "yes" :index "not_analyzed"}
   :country   {:type "string" :store "yes" :analyzer "snowball"}
   :tags      {:type "string" :index_name "tag"}
   :content   {:type "string" :store "yes" :analyzer "snowball"}})

(def collections-schema
  "A schema to hold Collections of Document References"
  {:name      {:type "string" :store "yes" :analyzer "snowball"}
   :author    {:type "string" :store "yes" :analyzer "snowball"}
   :date      {:type "string" :store "yes" :index "not_analyzed"}
   :tags      {:type "string" :index_name "tag"}
   :intro     {:type "string" :store "yes" :analyzer "snowball"}
   :documents {:type "string" :store "yes"}})

(defn create-index! [conn indx type schema]
  "Create schema from props. Add a unique short-url as external id."
  (let [schema' (merge schema
                       {:url {:type "string" :store "no" :index "not_analyzed"}})
        schema' {type {:properties schema}}]
    (esi/create conn indx :mappings schema')))

(defn index-document
  "Index a document with the given schema. Returns a unique URL."
  ([conn indx sch data]
   "Accept document in clj data structure"
   (let [url (gen-short-url)
         doc-data (merge data {:uri url})]
     (esd/create conn indx sch doc-data)
     url))
  ([conn indx sch file io-type]
   "Accept document as a raw file from io-types :resource :file :url"
   (index-document conn indx sch (zen/readany io-type file))))

(defn fuzzy-query [conn indx sch ^{:kind keyword} attr query size]
  "Search docs with Fuzzy queries."
  (let [query {:query_string {:query (str query "*")
                              :analyzer "snowball"
                              :allow_leading_wildcard false
                              :default_field (name attr)
                              :default_operator "OR"}}]
    (if (= attr :author)
      (esd/search conn indx sch :size size :query query :facets {:author {:terms {:field "author"}}})
      (esd/search conn indx sch :size size :query query))))

(defn match-query [conn indx sch ^{:kind keyword} attr query & size]
  "Search docs with exact match."
  (let [query {:match {attr query}}]
    (esd/search conn indx sch :query query)))

(defn search
  "Convenient wrapper for search queries. Accepts meta data or fuzzy :google
  like search. attr and value map to the document's schema. Optionally attr can
  be a vector. query-type can be :google or :match"
  ([conn indx sch attr query]
   (search conn indx sch attr query 100 :fuzzy))
  ([conn indx sch attr query size]
   (search conn indx sch attr query size :fuzzy))
  ([conn indx sch attr query size query-type]
   (let [queryfn (cond (= query-type :fuzzy) fuzzy-query
                       (= query-type :match) match-query)
         res (queryfn conn indx sch attr query size)
         total (:total (:hits res))]
     (if (:facets res)
       (get-in res [:facets attr :terms])
       (map #(:_source %) (:hits (:hits res)))))))

(defn status [conn indx]
  "Status of Index. Returns :running :not-running :index-not-setup"
  (let [stats (esi/stats conn indx)
        resp (cond
               (= (:status stats) 404) :index-not-setup
               (contains? stats :_shards) :running
               :else :not-running)]
    resp))

