API
===

_Document formats supported_:

- Plaintext (UTF-8)
- Markdown + github-flavored Markdown
- [Zendown](https://github.com/facjure/zendown)


## Indexing

```clojure
(require '[dossier.api :refer :all]))

(def conn (doc/connect "http://127.0.0.1:9200"))
(def sample "samples/Hemingway-The-old-man-and-the-bridge.txt")

(index-document conn index :documents sample :resource)
```

## Search

Search currently uses ‘fuzzy’ search, exact match, and others future options.

```clojure
(require '[dossier.api :refer :all]))

(search :title "The-old-man-and-the-bridge" 0 :match)
(search :content "bridge with soldiers" 5 :google)
```
