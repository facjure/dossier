(ns dossier.batch-test
  (:require [clojure.test :refer :all]
            [dossier.utils :refer :all]
            [zendown.core :as zen]
            [dossier.batch :refer :all]))

(deftest test-batch
  (def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")
  (testing "batch"
    (let [uri (generate-uri (zen/readany sample))
          data (upload "samples/Hemingway-The-old-man-and-the-bridge.txt" uri)]
      (is (contains? (download uri) :title)))))
