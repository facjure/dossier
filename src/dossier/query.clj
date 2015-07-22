(ns dossier.query
  (:require [clojure.string :as str]
            [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as esi]
            [clojurewerkz.elastisch.rest.document :as esd]
            [clojurewerkz.elastisch.rest.response :as esrsp]
            [clojurewerkz.elastisch.query :as q]))

(defn match [field term]
  {:match {field term}})

(defn match-parent [type id]
  {:has_parent {:type type :query (match :_id id)}})

(defn match-nested [path field term]
  {:nested {:path path :query (match (str path "." field) term)}})

(defn match-children [type field term]
  {:has_child {:type type :query (match field term)}})

(defn find-by-url [conn indx sch url]
  "Return content by document url"
  (:_source
   (esd/get conn sch url)))
