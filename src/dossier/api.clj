(ns dossier.api
  (:require [dossier.batch :refer :all]
            [dossier.document :refer :all]
            [dossier.converter :refer :all]
            [dossier.social :refer :all]
            [dossier.utils :refer :all]))

(defn publish [io-type fname]
  "Process a document, validate, backup in S3, index in ES, and generate a short url"
  (let [doc (read-zenup io-type fname)
        uri (gen-short-url)]
    (upload uri io-type fname)
    (index doc uri)))

(defn search
  "Generic search query. Accepts meta data or fuzzy :google like search.
   attr and value map to the document's schema. Optionally attr can be a vector
   query-type can be :google; default is :match"
 ([attr query size]
  (search attr query size :google))
 ([attr query size query-type]
  (let [queryfn (cond (= query-type :google) google-like-query
                      (= query-type :match) match-query)
        res (queryfn attr query size)
        total (:total (:hits res))]
    (if (:facets res)
      (get-in res [:facets attr :terms])
      (map #(:_source %) (:hits (:hits res)))))))
