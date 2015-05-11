Dossier
=======

Dossier is a simple document management library in Clojure, built on S3 and
Elasticsearch to store, search, and analyze plaintext-like documents on
[Elastisch](http://clojureelasticsearch.info). Built primarily to manage
structured content like blogs, poems, and stories.

## Quickstart

Add the following to Leiningen or Boot dependency:

Leiningen dependency information:

    [facjure/dossier "0.3.1"]

Environ](https://github.com/weavejester/environ) is used to manage environment
variables for AWS, Heroku, Cassandra and other storage engines.

Add the follwing keys in `~/.lein/profiles.clj` or your PAAS/IAAS environment:

    :aws-access-key "todo"
    :aws-secret-key "todo"
    :elasticsearch-url "todo"

## Documentation

See `doc/index.md` for more details

## Status & Roadmap

This library is still in development. Feedback and contributions are welcome.

v0.2.0

## License

Copyright © 2015 Facjure LLC.

Distributed under the Eclipse Public License either version 1.0 or (at your
option) any later version.
