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
(ns dda.config.commons.1-5.user-home
  {:deprecated "1.5"}
  (:require
   [clojure.string :as string]
   [schema.core :as s]))


(defn user-home-dir
  "provides the user home path."
  [user-name]
  (if (= user-name "root")
    "/root"
    (str "/home/" user-name)))

(s/defn
  flatten-user-home-path
  [path]
  (string/replace
    (last (re-find #"(/root|/home/[^/]*)/(.*)" path))
    #"/" "_"))
