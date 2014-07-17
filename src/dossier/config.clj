(ns dossier.config
  (:require [clojure.pprint :as pp]
            [environ.core :refer [env]])
  (:use [twitter.oauth]))

(def ^:dynamic *aws*
  {:access-key (env :aws-access-key)
   :secret-key (env :aws-secret-key)})

(def ^:dynamic *bucket* (env :s3-bucket))

(def ^:dynamic *twitter* (make-oauth-creds (:twtr-api-key env)
                               (:twtr-api-secret env)
                               (:twtr-useraccess-token env)
                               (:twtr-useraccess-secret env)))

(def ^:dynamic *es* (:bonsai-url env))
(def ^:dynamic *es-index* (:bonsai-url env))

(def ^:dynamic *app-domain* (:app-domain env))


(def ^:dynamic *twitter-user* (str "priyatam"))
(defn alter-twitter-user! [username]
  (alter-var-root (var *twitter-user*)
                  (constantly (str username))))
