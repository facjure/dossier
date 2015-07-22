(ns dossier.batch
  (:refer-clojure :exclude [name parents])
  (:use [clojure.string :only (join split)])
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [aws.sdk.s3 :as s3]
            [dossier.utils :refer :all]
            [zendown.core :as zen])
  (:import  [java.io File]))

(defn ls [{:keys [access-key secret-key]} dir]
  "List files in dir"
  (doseq [file (file-seq (File. dir))] (println file)))

(defn readline [fname]
  "Read one line at a time"
  (with-open [r (io/reader fname)]
    (doseq [line (line-seq r)]
      line)))

(defn append [fname contents]
  "Append contents to fname"
  (spit fname contents
        :encoding "UTF-8"
        :append true))

(defn upload [aws-config bucket doc io-type]
  "Upload content to S3"
  (let [data (zen/readany :resource doc)
        url (generate-uri data)
        contents (fetch io-type doc)]
    (s3/put-object aws-config bucket url contents)
    url))

(defn grant-read-access [aws-config bucket url]
  "Grant all users a read permission"
  (s3/update-object-acl aws-config bucket url (s3/grant :all-users :read)))

(defn exists? [aws-config bucket url]
  "Does content exist?"
  (s3/object-exists? aws-config bucket url))

(defn download [aws-config bucket url]
  "Download content from S3 and return a string"
  (slurp (:content (s3/get-object aws-config bucket url))))

(defn delete
  [aws-config bucket url]
  (s3/delete-object aws-config bucket url))
