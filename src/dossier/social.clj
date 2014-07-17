(ns dossier.social
  (:require [clojure.pprint :as pp]
            [dossier.config :refer :all])
  (:use [twitter.oauth]
        [twitter.request]
        [twitter.callbacks]
        [twitter.callbacks.handlers]
        [twitter.api.restful])
  (:import
   (twitter.callbacks.protocols AsyncStreamingCallback)
   (twitter.callbacks.protocols SyncSingleCallback)))


(defn tweet
  ([status]
   "Tweet status"
   (statuses-update :oauth-creds *twitter*
                    :params {:status status}))
   ([status pic]
    "Tweet status with a pic"
    (tweet status pic nil))
   ([status pic headers]
    "Tweet status with pic and custom headers"
    (statuses-update-with-media :oauth-creds *twitter*
                                :body [(file-body-part pic)
                                       (status-body-part status)]
                                :headers headers)))

(defn find-retweets [query]
  "Search your published tweets"
  (search-tweets :params {:q query}))
