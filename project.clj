(defproject org.domaindrivenarchitecture/dda-config-commons "0.1.7"
  :description "common utils for dda config"
  :url "https://www.domaindrivenarchitecture.org"
  :license {:name "Apache License, Version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [prismatic/schema "1.1.3"]
                 [metosin/schema-tools "0.9.0"]
                 [ch.qos.logback/logback-classic "1.1.7"]]
  :repositories [["snapshots" :clojars]
                 ["releases" :clojars]]
  :deploy-repositories [["snapshots" :clojars]
                        ["releases" :clojars]]
  :plugins [[lein-sub "0.3.0"]]
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
