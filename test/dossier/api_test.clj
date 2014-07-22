(ns dossier.api-test
  (:refer-clojure :exclude [name parents])
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [dossier.utils :refer [dbg]]
            [dossier.document :as doc]
            [dossier.api :refer :all]))

(deftest test-api
  (testing "init"
    (doc/setup))
  (testing "publish!"
    (let [doc-url (publish :resource "poems/Walt-Whitman-4.txt")]
      (dbg doc-url)
      (not (empty? doc-url)))))

(deftest test-search
  (testing "Google Like Queries"
    (is (= (doc/status) :running))
    (is (not (nil? (search :title "You May Forget"))))
    (is (not (nil? (search :author "Sappho"))))
    (is (not (nil? (search :year "600"))))
    (is (not (nil? (search :country "Greece"))))
    (is (not (nil? (search :content "someone in")))))

  (testing "Auto complete queries"
    (is (not (nil? (search :author "Sap" ))))
    (is (not (nil? (search :title "May AND Forget" :google))))))


;(run-tests)
