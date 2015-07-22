(ns dossier.utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import  [java.io File]))

(defmacro dbg [body]
  "Cheap inline debugging"
  `(let [x# ~body]
     (println x#)
     x#))

(defn- sanitize [s]
  (-> (str/triml s)
      (str/lower-case)
      (str/replace #"[\!\"\#\$\%\&\'\(\)\*\+\,\-\.\/\:\;\<\=\>\?\@\ \\\^\_\`\{\|\}\~]+" " ")
      (str/replace " " "-")
      (str/replace #"-+" "-")
      (str/replace #"-$" "")))

(defn generate-uri [{author :author title :title doc :content}]
  "Generates a document's unique uri, a suffix for the host url.
   Expects a doc in clj data."
  (let [first-line (first (str/split-lines doc))
        uri (cond
             (= title nil) first-line
             :else title)]
    (sanitize (str author "-" uri))))

(defn fetch [io-type file]
  "Fetch file from any io-type
   Usage:
       fetch :resource 'data/poem.txt'"
  (cond
   (= io-type :str) (str file)
   (= io-type :resource) (slurp (io/resource file))
   (= io-type :url) (slurp (io/as-url file))
   (= io-type :file) (slurp (io/as-file file))
   :else nil))

(defn int-to-base62
  "Convert an integer to base62"
   ([n] (int-to-base62 (rem n 62) (quot n 62) ""))
   ([remainder number accum]
    (let [alphabet "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"]
      (cond
        (zero? number) (str (nth alphabet remainder) accum)
        :else (recur
               (rem number 62)
               (quot number 62)
               (str (nth alphabet remainder) accum))))))

(defn gen-short-url []
  "Generate a unique, short URL with 8 chars"
   (subs
    (int-to-base62
     (java.math.BigInteger. (str/replace (str (java.util.UUID/randomUUID)) "-" "") 16))
    0 8))

