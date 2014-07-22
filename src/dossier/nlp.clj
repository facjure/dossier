(ns dossier.nlp
  (:require
   [clojure.java.io :as io])
  (:use opennlp.nlp
        opennlp.treebank
        opennlp.tools.lazy
        opennlp.tools.filters
        dossier.utils)
  (:import
    (opennlp.tools.sentdetect DefaultEndOfSentenceScanner)))

;; Custom make-sentence-detector

;; TODO
(defmethod make-sentence-detector DefaultEndOfSentenceScanner
  [model]
  (fn sentence-detector
    [text]
    {:pre [(string? text)]}))

;; Initialize Models

(defonce get-sentences (make-sentence-detector (io/resource "models/en-sent.bin")))
(defonce tokenize (make-tokenizer (io/resource "models/en-token.bin")))
(defonce detokenize (make-detokenizer (io/resource "models/english-detokenizer.xml")))
(defonce pos-tag (make-pos-tagger (io/resource "models/en-pos-maxent.bin")))
(defonce name-find (make-name-finder (io/resource "models/namefind/en-ner-person.bin")))
(defonce chunker (make-treebank-chunker (io/resource "models/en-chunker.bin")))


