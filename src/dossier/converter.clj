(ns dossier.converter
  (:require [clojure.string :as str]
            [markdown.core :as md]
            [clj-yaml.core :as yaml]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [dossier.utils :refer :all]))

(defn read-zenup
  "Process a zenup doc into clj ds: map of metadata :content :tags.
   io-type can be- :resource :url :file."
  [io-type file]
  (let [poem (fetch io-type file)
        [_ yml raw] (str/split poem #"---")
        metadata (yaml/parse-string yml)
        tags (into #{} (:tags metadata))]
    (assoc metadata :content (str/triml raw) :tags tags)))

(defn with-html5 [document]
  "Converts a parsed document into HTML5 sections"
  (html5
   [:head
    [:title (str (:title document) " by " (:author document))]]
   [:body
    (:section (:section document))]))

(defmulti convert :type)
(defmethod convert :text [content]
  ((:body content)))
(defmethod convert :md [content]
  (md/md-to-html-string (:body content)))
(defmethod convert :zenup [content]
  (read-zenup :resource content))
(defmethod convert :pdf [content]
  (str "TODO"))
