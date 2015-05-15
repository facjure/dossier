(ns dossier.document-test
  (:refer-clojure :exclude [name parents])
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [clojurewerkz.elastisch.rest :as esr]
            [clojurewerkz.elastisch.rest.index :as idx]
            [dossier.document :as doc]))

(def conn (esr/connect))
(def index (str (java.util.UUID/randomUUID)))
(def schema "test")

(use-fixtures :once
  (fn [tests]
    (doc/create-index! conn index schema doc/document-schema)
    (tests)
    (idx/delete conn index)))

(deftest test-indexing
  (testing "Indexes"
    (is (= :running
           (doc/status conn index)))
    (is (not-empty
         (doc/index-document conn index schema "samples/sappho-you-may-forget.txt" :resource)))))

(deftest test-match-query
  (testing "Match Queries"
    (is (not (nil? (doc/match-query conn index schema :title "You May Forget"))))
    (is (not (nil? (doc/match-query conn index schema :author "Sappho"))))
    (is (not (nil? (doc/match-query conn index schema :year "600"))))
    (is (not (nil? (doc/match-query conn index schema :country "Greece"))))
    (is (not (nil? (doc/match-query conn index schema :content "someone in"))))))

(deftest test-fuzzy-query
  (testing "Fuzzy Queries"
    (is (not (nil? (doc/fuzzy-query conn index schema :author "Sap" 10))))
    (is (not (nil? (doc/fuzzy-query conn index schema :title "May AND Forget" 10))))))
