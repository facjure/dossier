(ns dossier.batch-test
  (:require [clojure.test :refer :all]
            [dossier.utils :refer :all]
            [zendown.core :as zen]
            [dossier.batch :as batch]))

(def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")

(deftest test-batch
  (testing "batch"
    (let [bucket "dossier-test"
          url (batch/upload bucket sample :resource)]
      (is (contains?
           (zen/readany :str (batch/download bucket url)) :title)))))
