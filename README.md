Dossier
======

A document management library in Clojure, built on S3 and Elasticsearch.

## Overview

Dossier is a higher-level api to store, search, and analyze plaintext-like documents on [Elastisch](http://clojureelasticsearch.info) with [S3](https://github.com/weavejester/clj-aws-s3). Built primarily to analyze structured content like tech blogs, poems, and stories.

For more info, see Status & Roadmap.

## API

Currently, there are two apis:

1. Publish

```clojure
(require '[clover.api :refer :all]))
(publish :resource "samples/Chris-Granger-IDE-as-value.md")
>> http://yourdomain.com/zaswqr7
```

This will upload a document to S3, index content and its metadata in ES, and generate a short url.

2. Search

```clojure
(require '[clover.api :refer :all]))
(search :title "IDE as VALUE" 0 :match)
(search :content "After week physical" 5 :google)
```

_Document formats supported_:

- Plaintext (UTF-8)
- Markdown (+ github-flavored markdown)
- [Zenup](https://github.com/facjure/zenup)

## Usage

**Setup**

Environment variables are managed by [Environ](https://github.com/weavejester/environ). Add the following vars in `~/.lein/profiles.clj`.

_AWS/S3 credentials_:

    :aws-access-key
    :aws-secret-key
    :s3-bucket

Your application domain (http://<mynew>.com), for generating unique document urls

    :app-domain

**Dev**

Start Elasticsearch in the background.

    elasticsearch

Run ring in the background to auto-reload .clj classes.

    lein deps (first time)
    lein repl

**Test**

    lein test

**Package**

    lein uberjar

## Documentation

See `docs/guide.md` (work-in-progress) for a guide and an introduction to Elasticsearch.

## Credits

This library stands on the giant shoulders of [Elastisch](http://clojureelasticsearch.info), thanks to the team at Clojurewerkz.

## Status & Roadmap

**early development**.

This library was broken apart from an internal webapp (Poetroid), and currently works (almost) for basic search, backup, and other simple things. The roadmap includes:

- [Aggregation](http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/_buckets.html) for 'Collections'
- ‘Related documents’ based on existing metadata
- Converters for Pdf and Html5

## License

Copyright © 2014 Facjure LLC.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
