(ns dossier.batch
  (:refer-clojure :exclude [name parents])
  (:use [clojure.string :only (join split)])
  (:require [environ.core :refer [env]]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [me.raynes.fs :refer [glob]]
            [aws.sdk.s3 :as s3]
            [dossier.utils :refer :all])
  (:import  [java.io File]))

(def ^:dynamic *aws*
  {:access-key (env :aws-access-key)
   :secret-key (env :aws-secret-key)})

(defn ls [dir]
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

(defn upload [bucket url fname]
  "Upload content to S3"
  (s3/put-object *aws* bucket url (fetch :url fname)))

(defn grant-read-access [bucket url]
  "Grant all users a read permission"
  (s3/update-object-acl *aws* bucket url (s3/grant :all-users :read)))

(defn exists? [bucket url]
  "Does content exist?"
  (s3/object-exists? *aws* bucket url))

(defn download [bucket url-suffix]
  "Download content from S3 and return a string;
   url-suffix: unique suffix added to the S3 host URL"
  (slurp (:content (s3/get-object *aws* bucket url-suffix))))

(defn delete
  [bucket url fname]
  (s3/delete-object *aws* bucket url))
