Dossier
=======

A simple document management library in Clojure, built on S3 and Elasticsearch.

## Overview

Dossier is a higher-level api to store, search, and analyze plaintext-like documents on [Elastisch](http://clojureelasticsearch.info). Built primarily to manage structured content like blogs, poems, and stories.

For more info, see Status & Roadmap.

## Usage

Leiningen dependency information:

    [facjure/dossier "0.3.1"]

## API

Currently, there are two apis:

1. Publish

```clojure
(require '[dossier.api :refer :all]))
(publish :resource "samples/Hemingway-The-old-man-and-the-bridge.md")
>> http://yourdomain.com/zaswqr7
```

This will upload a document to S3, index content and its metadata in ES, and generate a short url. Publish can take :file :url :str or any :resource from classpath.

2. Search

Search currently uses ‘fuzzy’ search, exact match, and others options.

```clojure
(require '[dossier.api :refer :all]))
(search :title "The-old-man-and-the-bridge" 0 :match)
(search :content "bridge with soldiers" 5 :google)
```

_Document formats supported_:

- Plaintext (UTF-8)
- Markdown + github-flavored Markdown
- [Zendown](https://github.com/facjure/zendown)

## Documentation

See `docs/guide.md` (work-in-progress) for a guide and an introduction to Elasticsearch.

## Credits

This library stands on the shoulders of [Elastisch](http://clojureelasticsearch.info).

## Status & Roadmap

**Early development**.

This library was broken apart from an internal webapp (Poetroid), and currently works (almost) for basic search, backup, and other simple things. The roadmap includes:

- [Aggregation](http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/_buckets.html) for 'Collections'
- [Looking at time](http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/_looking_at_time.html)
- 'Related documents' based on existing metadata

## License

Copyright © 2014 Facjure LLC.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
