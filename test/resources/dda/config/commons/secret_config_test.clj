(ns resources.dda.config.commons.secret-config-test
  (:require
   [clojure.test :refer :all]
   [data-test :refer :all]
   [dda.config.commons.secret-config :as sut]))

(defdatatest should-load-and-resolve [input expected]
  (is (= expected
         (sut/load-and-resolve input))))
