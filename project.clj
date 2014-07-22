(defproject dossier "0.3.0"
  :description "A document management library, built on S3 and Elasticsearch."
  :url "https://github.com/Facjure/clover"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :jvm-opts ["-Xms768m" "-Xmx768m"]
  :warn-on-reflection false
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojurewerkz/elastisch "2.0.0"]
                 [clojure-opennlp "0.3.2"]
                 [clj-aws-s3 "0.3.9"]
                 [twitter-api "0.7.5"]
                 [hiccup "1.0.5"]
                 [markdown-clj "0.9.47"]
                 [clj-yaml "0.4.0"]
                 [clj-pdf "1.11.20"]
                 [me.raynes/fs "1.4.6"]
                 [environ "0.5.0"]]
  :plugins [[lein-environ "0.5.0"]
            [lein-marginalia "0.7.1"]
            [lein-midje "3.0.0"]]
  :uberjar-name "clover.jar"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.5.1" :exclusions [org.codehaus.plexus/plexus-utils]]]}})
