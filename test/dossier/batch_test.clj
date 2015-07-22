(ns dossier.batch-test
  (:require [environ.core :refer [env]]
            [clojure.test :refer :all]
            [dossier.utils :refer :all]
            [zendown.core :as zen]
            [dossier.batch :as batch]))

(def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")

(defn aws []
  {:access-key (:aws-access-key env)
   :secret-key (:aws-secret-key env)})

(deftest test-batch
  (testing "batch"
    (let [bucket "dossier-test"
          url (batch/upload (aws) bucket sample :resource)]
      (is (contains?
           (zen/readany :str (batch/download (aws) bucket url)) :title)))))
