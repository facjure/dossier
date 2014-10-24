(ns dossier.document-test
  (:refer-clojure :exclude [name parents])
  (:require [clojure.java.io :as io]
            [me.raynes.fs :refer [glob]]
            [clojure.test :refer :all]
            [dossier.document :refer :all]))


(use-fixtures :once
  (fn [tests]
    (setup *es-index*)
    (index :resource "samples/sappho-you-may-forget.txt" "123df1")
    (tests)))

(deftest test-search
  (testing "Google Like Queries"
    (is (= (status) :running))
    (is (not (nil? (match-query :title "You May Forget"))))
    (is (not (nil? (match-query :author "Sappho"))))
    (is (not (nil? (match-query :year "600"))))
    (is (not (nil? (match-query :country "Greece"))))
    (is (not (nil? (match-query :content "someone in")))))

  (testing "Google-like-query"
    (is (not (nil? (google-like-query :author "Sap" ))))
    (is (not (nil? (google-like-query :title "May AND Forget" :google))))))

