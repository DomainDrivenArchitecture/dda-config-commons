(ns dda.config.commons.1-5.schema-test
  {:deprecated "1.5"}
  (:require
   [clojure.string :as string]
   [clojure.test :refer :all]
   [clojure.test.check :as tc]
   [clojure.test.check.generators :as gen]
   [clojure.test.check.properties :as prop]
   [dda.config.commons.1-5.schema :as sut]))


; ------------------------  test data  ------------------------

(def gen-octet (gen/choose 0 255))
(def gen-ipv4 (gen/fmap #(string/join "." %) (gen/vector gen-octet 4)))

(def gen-bad-octet (gen/choose 0 10000))
(def gen-bad-ipv4 (gen/fmap #(string/join "." %)
                        (gen/such-that #(or (not= 4 (count %))
                                            (some (partial < 255) %))
                                       (gen/vector gen-octet 1 10))))

; ------------------------  tests  ------------------------------

(def prop-good-ipv4
  (prop/for-all [ip gen-ipv4]
    (= true (sut/ipv4? ip))))

(def prop-bad-ipv4
  (prop/for-all [ip gen-bad-ipv4]
    (= false (sut/ipv4? ip))))

(deftest test-ipv4
  (testing
    "test ipv4 validation"
    (is (:result (tc/quick-check 100 prop-good-ipv4)))
    (is (:result (tc/quick-check 100 prop-bad-ipv4)))))
