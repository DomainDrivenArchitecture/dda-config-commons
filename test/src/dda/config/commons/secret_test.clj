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


(ns dda.config.commons.secret-test
  (:require
    [clojure.test :refer :all]
    [schema.core :as s]
    [dda.config.commons.secret :as sut]))

(defmethod sut/resolve-secret :test-resolver
  [secret
   & _]
  "success")

(def secret
  {:plain "test"})

(deftest test-schema
  (testing
    (is (s/validate sut/Secret secret))
    (is (s/validate sut/Secret  {:password-store-multi "path"}))
    (is (s/validate sut/Secret  {:password-store-single "path"}))
    (is (s/validate sut/Secret  {:password-store-record {:path "path"
                                                         :element :login}}))
    (is (thrown? Exception (s/validate sut/Secret {:not-implemented ""})))))

(deftest test-secret-resolving
  (testing
    (is (= :plain (sut/dispatch-by-secret-type secret)))
    (is (thrown? Exception (sut/resolve-secret {:not-implemented ""})))
    (is (= "success" (sut/resolve-secret {:test-resolver ""})))
    (is (= "test" (sut/resolve-secret secret)))))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Tests for create-custom-resolved-schema ;;;
;;; and for resolve-custom-secrets.         ;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def secret2
  {:plain s/Str})

(def schema1
  (s/either
    {:a secret2
     :b s/Any
     :c (s/either s/Int secret2)
     :d [secret2]
     (s/optional-key :e) secret2
     (s/optional-key :f) {:x s/Str
                          :y secret2}}
    [secret2]))

(def schema1-resolved
  (s/either
    {:a s/Str
     :b s/Any
     :c (s/either s/Int s/Str)
     :d [s/Str]
     (s/optional-key :e) s/Str
     (s/optional-key :f) {:x s/Str
                          :y s/Str}}
    [s/Str]))

(def config1
  {:a {:plain "first secret"}
   :b 42
   :c 24
   :d [{:plain "secret"} {:plain "secret2"}]
   :e {:plain "next secret"}
   :f {:x "no secret"
       :y {:plain "secret"}}})

(def config2
  [{:plain "secret"} {:plain "secret2"}])
  
(deftest test-create-custom-resolved-schema
  (testing
    (is (= s/Str (sut/create-custom-resolved-schema secret2 secret2)))
    (is (= {s/Keyword s/Str} (sut/create-custom-resolved-schema {s/Keyword secret2} secret2)))
    (is (= [s/Str] (sut/create-custom-resolved-schema [secret2] secret2)))
    (is (= schema1-resolved (sut/create-custom-resolved-schema schema1 secret2)))))

(deftest test-resolve-custom-secrets
  (testing
    (is (s/validate schema1-resolved (sut/resolve-custom-secrets config1 schema1 secret2)))
    (is (s/validate schema1-resolved (sut/resolve-custom-secrets config2 schema1 secret2)))
    (is (thrown? Exception (sut/resolve-custom-secrets schema1 (merge config1 {:a "no secret"}) secret2)))
    ))
