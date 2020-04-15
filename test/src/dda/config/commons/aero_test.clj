(ns dda.config.commons.aero-test
  (:require
   [clojure.test :refer :all]
   [data-test :refer :all]
   [dda.config.commons.aero :as sut]))

(defdatatest should-load-and-resolve [input expected]
  (is (= expected
         (sut/load-and-resolve input))))