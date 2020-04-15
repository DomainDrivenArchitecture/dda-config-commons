; Licensed to the Apache Software Foundation (ASF) under one
; or more contributor license agreements. See the NOTICE file
; distributed with this work for additional information
; regarding copyright ownership. The ASF licenses this file
; to you under the Apache License, Version 2.0 (the
; "License"); you may not use this file except in compliance
; with the License. You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.

(ns dda.config.commons.1-5.map-utils-test
  {:deprecated "1.5"}
  (:require
    [clojure.test :refer :all]
    [schema.core :as s]
    [schema.experimental.complete :as c]
    [dda.config.commons.1-5.map-utils :as sut]))


(deftest deep-merge-test
 (testing
   (is (= {:a {:a_a "a2.a"
               :a_b "a1.b"}
           :b {:b_b "b1.b"}}
          (sut/deep-merge
            {:a {:a_a "a1.a"
                 :a_b "a1.b"}
             :b {:b_b "b1.b"}}
            {:a {:a_a "a2.a"}})))
  (is (= {:a #{:a1 :b1}}
         (sut/deep-merge
           {:a #{:a1}}
           {:a #{:b1}})))))


(deftest schema-keys-test
 (testing
   (is (= :a
          (first (sut/schema-keys
                   {:a s/Bool}))))
   (is (= :a
          (first (sut/schema-keys
                   {(s/optional-key :a) s/Bool}))))))


(deftest filter-test
 (testing
   "test wheter deep merge works"
     (is (not (contains?
               (sut/filter-for-target-schema
                 {:a s/Bool}
                 {:a :true
                  :b false})
               :b)))))
