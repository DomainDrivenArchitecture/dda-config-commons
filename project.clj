(defproject org.domaindrivenarchitecture/dda-config-commons "0.1.0-SNAPSHOT"
  :description "common utils for dda config"
  :url "https://www.domaindrivenarchitecture.org"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [prismatic/schema "1.0.5"]
                 [metosin/schema-tools "0.7.0"]
                 ]
  :repositories [["snapshots" :clojars]
                 ["releases" :clojars]]
  :deploy-repositories [["snapshots" :clojars]
                        ["releases" :clojars]]
  :profiles {:dev
             {:dependencies
              [[org.clojure/test.check "0.9.0"]
               ]}
             :leiningen/reply
             {:dependencies []
              :exclusions []}}
  :local-repo-classpath true
  :classifiers {:tests {:source-paths ^:replace ["test"]
                        :resource-paths ^:replace []}})
