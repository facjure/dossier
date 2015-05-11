API
===

_Document formats supported_:

- Plaintext (UTF-8)
- Markdown + github-flavored Markdown
- [Zendown](https://github.com/facjure/zendown)


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
