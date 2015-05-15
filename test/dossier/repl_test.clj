(ns dossier.repl-test
  (:require [clojure.java.io :as io]
            [dossier.utils :refer :all]
            [dossier.document :as doc]
            [zendown.core :as zen]
            [clojurewerkz.elastisch.rest.index :as esi]))

(def conn (doc/connect "http://127.0.0.1:9200"))
(def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")

(defn hello-es [conn indx]
  (let [mapping-types {:person {:properties {:username   {:type "string" :store "yes"}
                                             :first-name {:type "string" :store "yes"}
                                             :last-name  {:type "string"}
                                             :age        {:type "integer"}
                                             :title      {:type "string" :analyzer "snowball"}
                                             :planet     {:type "string"}
                                             :biography  {:type "string" :analyzer "snowball" :term_vector "with_positions_offsets"}}}}]
    (esi/create conn indx :mappings mapping-types)))

(comment
  (def index (str (java.util.UUID/randomUUID)))

  index
  ;;  (hello-es conn index)

  (doc/status conn index)

  (doc/create-index! conn index :documents doc/document-schema)

  (doc/status conn index)

  (doc/index-document conn index :documents sample :resource)

  (doc/match-query conn index :documents :title "The old man and the bridge")

  (doc/fuzzy-query conn index :documents :title "The old men" 1)

  (doc/search conn index :documents :title "The old man and the bridge" 10)

  (doc/search conn index :documents :author "Hemingway" 20)

  (doc/search conn index :documents :tags "spanish" 5)

  (doc/search conn index :documents :content "a grey overcast day" 5 :fuzzy)

  (esi/delete conn index) )
