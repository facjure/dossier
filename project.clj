(defproject facjure/dossier "0.3.3"
  :description "A document management library, built on S3 and Elasticsearch."
  :url "https://github.com/facjure/dossier"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/facjure/dossier"}
  :min-lein-version "2.5.1"
  :jvm-opts ["-Xms512m" "-server"]
  :warn-on-reflection false
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [clojurewerkz/elastisch "2.2.0-beta4"]
                 [clj-time "0.9.0"]
                 [clj-aws-s3 "0.3.10" :exclusions [joda-time]]
                 [environ "1.0.0"]
                 [facjure/zendown "0.3.0"]]
  :plugins [[lein-environ "1.0.0"]
            [lein-cljfmt "0.1.10"]
            [com.jakemccrary/lein-test-refresh "0.9.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
                                  [environ "1.0.0"]
                                  [pjstadig/humane-test-output "0.7.0"]]}})
