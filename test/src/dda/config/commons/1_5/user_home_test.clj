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
(ns dda.config.commons.1-5.user-home-test
  {:deprecated "1.5"}
  (:require
   [clojure.test :refer :all]
   [schema.core :as s]
   [dda.config.commons.user-home :as sut]))

(deftest test-user-home
  (s/set-fn-validation! true)
  (is (= "/root"
         (sut/user-home-dir "root")))
  (is (= "/home/another"
         (sut/user-home-dir "another"))))

(deftest test-flatten-path
  (s/set-fn-validation! true)
  (is (= "some_path_xx"
         (sut/flatten-user-home-path "/root/some/path_xx")))
  (is (= "some_path"
         (sut/flatten-user-home-path "/home/another/some/path")))
  (is (= "_"
         (sut/flatten-user-home-path "/home/another/_")))
  (is (= "realy_long_path_"
         (sut/flatten-user-home-path "/home/another/realy/long/path/"))))
