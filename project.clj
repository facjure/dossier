(defproject facjure/dossier "0.3.2"
  :description "A document management library, built on S3 and Elasticsearch."
  :url "https://github.com/facjure/dossier"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/facjure/dossier"}
  :min-lein-version "2.5.1"
  :jvm-opts ["-Xms768m" "-Xmx768m"]
  :warn-on-reflection false
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojurewerkz/elastisch "2.2.0-beta3"]
                 [clj-aws-s3 "0.3.10"]
                 [me.raynes/fs "1.4.6"]
                 [environ "1.0.0"]
                 [facjure/zendown "0.3.0"]]
  :plugins [[lein-environ "1.0.0"]
            [lein-cljfmt "0.1.10"]
            [lein-marginalia "0.8.0"]]
  :profiles {:dev {:dependencies [[expectations "2.0.9"]]}})
