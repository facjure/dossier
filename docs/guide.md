# Dossier Guide

## Introduction to Elasticsearch

Elasticserch is built on **Lucene**, an industry standard in search for over a decade. It has two interfaces: **Solr** and **ElasticSearch**.

Solr is a Database with search server, and accepts multyiple formats like JSON, XML, CSV. It supports modeling documentd with a Schema, REST, and is distributed (since 4.0). The cloud offering includes Websolr and Zookeeper. As you can imagine, things are not easy as they seem.

ElasticSearch focuses on JSON and REST. Schema is optional. It's distributed from the start, and keeps [getting better](http://www.elasticsearch.org/blog/1-0-0-released/). Due to its focus on clustering, several cloud providers offer solutions on AWS and Heroku. This makes it a better choice for app developers and publishers who understand their business model.

The first thing to know about ES is that a Document != File. Imagine a row in a table from a relational database. Something like that. Each document is stored, cached, and indexed with a type and an id. A type is like a ‘table’. There's no DDL or SQL, but we have basic Schema mapping and Query DSL in JSON.

Documents -> (field, value) pairs.

What do these technologies solve?

The problem of full-text searching.

Other features:

- auto-complete
- wildcards and regex
- fieldname search
- AND, OR, NOT queries
- term search:
- basic linguistic analysis for text fields
- hit highlighting
- [aggregations](http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/aggregations.html), based on faceted_search](https://en.wikipedia.org/wiki/)
- [arser Query Syntax](http://lucene.apache.org/core/2_9_4/queryparsersyntax.html)
- Integration with commerical tooling and data visualization

## API

TODO

## Essays

- [Call me Maybe](http://aphyr.com/posts/317-call-me-maybe-elasticsearch), a deep dive into the issues with scaling Elasticsearch

## Books (free)

- [Exploring Elasticsearh](http://exploringelasticsearch.com)
