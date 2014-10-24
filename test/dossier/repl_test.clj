(ns dossier.repl-test
  (:require [clojure.java.io :as io]
            [dossier.utils :refer :all]
            [dossier.document :as doc]
            [dossier.api :as api]))


(def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")

;; Search

;(doc/cleanup)
;(doc/status)
;(doc/setup)

;(doc/index :resource sample (gen-short-url))
;(doc/status)
;(doc/index-all authors-10)
;(doc/search :title "A Blockhead" 0 :match)
;(doc/search :author "amy" 20)
;(doc/search :tags "coffee" 5)
;(doc/search :content "After a week of physical" 5 :google)
;(doc/search :content "After week physical" 5 :google)

;; Publish

;(api/publish :resource "poems/rattlesnake.txt")
;(def doc-url "<copy-from-out-above>")
;(doc/search :uri doc-url 0 :match)
