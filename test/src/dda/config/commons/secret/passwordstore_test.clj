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


(ns dda.config.commons.secret.passwordstore-test
  (:require
    [clojure.test :refer :all]
    [schema.core :as s]
    [dda.config.commons.secret.passwordstore :as sut]))

(deftest test-trim
  (testing
    (is (= "test" (sut/trim "test")))
    (is (= "test" (sut/trim " test")))
    (is (= "test" (sut/trim " test ")))
    (is (= "test" (sut/trim " \ntest ")))
    (is (= "test" (sut/trim " \n test \n ")))
    (is (= "te st" (sut/trim "te st")))))

(deftest test-parse-line
  (testing
    (is (= {:login "l"}
           (sut/parse-line "login:l")))
    (is (= {:login "l"}
           (sut/parse-line " login:l")))
    (is (= {:login "l"}
           (sut/parse-line "login:l ")))
    (is (= {:login "l"}
           (sut/parse-line "login: l ")))))

(deftest test-parse-record
  (testing
    (is (= {:login "l"
            :password "p"}
           (sut/parse-record "p\nlogin:l")))
    (is (= {:login "l"
            :password "p"}
           (sut/parse-record "p\nlogin:l\nurl:u")))
    (is (= {:login "l"
            :password "p"}
           (sut/parse-record "\np\nlogin:l")))
    (is (= {:login "l"
            :password "p"}
           (sut/parse-record "\nlogin:l\np\n")))
    (is (= {:login "l"
            :password "p"}
           (sut/parse-record "\nlogin:l\npassword:p\n")))))
