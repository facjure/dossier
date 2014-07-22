(ns dossier.batch-test
  (:require [clojure.test :refer :all]
            [dossier.utils :refer :all]
            [dossier.converter :refer :all]
            [dossier.batch :refer :all]))

(deftest test-batch
  (def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")
  (testing "batch"
    (let [uri (generate-uri (read-zenup sample))
          data (upload "samples/Hemingway-The-old-man-and-the-bridge.txt" uri)]
      (is (contains? (download uri) :title)))))


;(run-tests)
