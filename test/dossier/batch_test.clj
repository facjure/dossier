(ns dossier.batch-test
  (:require [clojure.test :refer :all]
            [dossier.utils :refer :all]
            [zendown.core :as zen]
            [dossier.batch :refer :all]))

(deftest test-batch
  (def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")
  (testing "batch"
    (let [bucket "dossier-test"
          uri (generate-uri (zen/readany :resource sample))
          data (upload bucket "samples/Hemingway-The-old-man-and-the-bridge.txt" uri)]
      (is (contains? (download bucket uri) :title)))))
