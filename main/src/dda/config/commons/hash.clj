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
(ns dda.config.commons.hash
  (:require
   [schema.core :as s])
  (:import
    (org.apache.commons.codec.digest Crypt)))

(defn bash-escaped
  "returns bash escaped string"
  [#^String input]
  (apply str
    (map
     (fn [a] (if (= a \$) "\\$" a))
     (seq input))))

(defn crypt
  "Computes the linux crypt hashed & encoded representation of a string"
  [#^String input]
  (Crypt/crypt input))

(defn crypt-bash-escaped
  "returns bash escaped password"
  [#^String input]
  (bash-escaped (crypt input)))
