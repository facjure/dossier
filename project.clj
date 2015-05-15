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
                 [clj-time "0.9.0"]
                 [clj-aws-s3 "0.3.10" :exclusions [joda-time]]
                 [environ "1.0.0"]
                 [facjure/zendown "0.3.0"]]
  :plugins [[lein-environ "1.0.0"]
            [lein-cljfmt "0.1.10"]
            [com.jakemccrary/lein-test-refresh "0.9.0"]]
  :profiles {:dev {:dependencies [[pjstadig/humane-test-output "0.7.0"]]
                   :test-refresh {:notify-command ["terminal-notifier" "-title" "Tests" "-message"]}
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]}})
