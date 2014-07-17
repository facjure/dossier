(ns dossier.document
  (:require [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as idx]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojurewerkz.elastisch.query :as q]
            [me.raynes.fs :refer [glob]]
            [dossier.config :refer :all]
            [dossier.converter :as conv]
            [dossier.utils :refer :all]))

(def document-schema
  "A Schema to hold Documents"
  {
   :url       {:type "string" :store "no" :index "not_analyzed"}
   :title     {:type "string" :store "yes" :analyzer "snowball"}
   :date      {:type "string" :store "yes" :index "not_analyzed"}
   :author    {:type "string" :store "yes" :analyzer "snowball"}
   :bio       {:type "string" :store "yes" :analyzer "snowball"}
   :gender    {:type "string" :store "yes" :index "not_analyzed"}
   :country   {:type "string" :store "yes" :analyzer "snowball"}
   :tags      {:type "string" :index_name "tag"}
   :content   {:type "string" :store "yes" :analyzer "snowball"}})

(def collections-schema
  "A schema to hold Collections"
  {
   :url       {:type "string" :store "no" :index "not_analyzed"}
   :name      {:type "string" :store "yes" :analyzer "snowball"}
   :author    {:type "string" :store "yes" :analyzer "snowball"}
   :date      {:type "string" :store "yes" :index "not_analyzed"}
   :tags      {:type "string" :index_name "tag"}
   :intro     {:type "string" :store "yes" :analyzer "snowball"}
   :documents {:type "string" :store "yes"}})

(defn setup [indx]
  "Setup Elastic Search with a defined schema."
  (esr/connect *es*)
  (let [documents {:docs {:properties document-schema}}
        collections {:docs {:properties collections-schema}}]
    (idx/create indx :mappings documents)
    (idx/create indx :mappings collections)))

(defn index
  "Index a document in Elasticsearch."
  ([doc uri]
   "doc - processed document in clj data structure"
   (let [doc-data (merge doc {:uri uri})]
     (esd/create *es-index* :docs doc-data)))
  ([io-type file uri]
   "io-type - :resource :file :url
   file - file name"
   (index (conv/read-zenup io-type file) uri)))

;; NOTE: May not work on Heroku with multiple dynos
(defn index-all [collection]
  "Index all local files based on local folder"
  (let [cwd (. (java.io.File. ".") getCanonicalPath)
        coll-path (str cwd "/resources/" collection)
        _ (println coll-path)]
    (doseq [f (glob coll-path)]
      (index :file f (gen-short-url)))
    :success))

(defn google-like-query [^{:kind keyword} attr query size]
  "Search with Google like queries."
  (let [query {:query_string {:query (str query "*")
                              :analyzer "snowball"
                              :allow_leading_wildcard false
                              :default_field (name attr)
                              :default_operator "OR"}}]
    (if (= attr :author)
      (esd/search *es-index* :docs :size size :query query :facets { :author {:terms {:field "author"}}})
      (esd/search *es-index* :docs :size size :query query))))

(defn match-query [^{:kind keyword} attr query _]
  "Exact match."
  (let [query {:match {attr query}}]
    (esd/search *es-index* :docs :query query)))

(defn status []
  "Status of Index
  Returns :running :not-running :index-not-setup"
  (let [status (idx/stats *es-index*)
        resp   (cond
                (= (:status status) 404) :index-not-setup
                (contains? status :_shards) :running
                :else :not-running)]
    resp))

(defn find-by-id [id]
  "Return content by id"
  (:_source
   (esd/get *es-index* :docs id)))

(defn cleanup
  "****WARNING*****
  Deletes Poetroid Index!"
  ([] (cleanup *es*))
  ([url]
   (esr/connect url)
   (idx/delete *es-index*)))
