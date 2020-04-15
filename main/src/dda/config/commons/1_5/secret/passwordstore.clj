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

(ns dda.config.commons.1-5.secret.passwordstore
  (:require
   [schema.core :as s]
   [clojure.string :as string]
   [clojure.java.shell :as sh]))

(def PasswordStoreRecord
  {:password s/Str
   :login s/Str})


(def PasswordStoreRecordSpec
  {:path s/Str
   :element (s/enum :password :login)})

(def PasswordStore
  [{:password-store-single s/Str}
   {:password-store-record PasswordStoreRecordSpec}
   {:password-store-multi s/Str}])

(s/defn get-secret :- s/Str
  [path :- s/Str]
  (let [result (sh/sh "pass" path)]
    (if (= 0 (:exit result))
        (:out result)
        (throw (RuntimeException. (str "error in path " path "\n" (:err result)))))))

(s/defn trim :- s/Str
  [input :- s/Str]
  (string/trim input))

(s/defn parse-line :- [s/Str s/Str]
  [input-singleline :- s/Str]
  (let [login (re-find #"(^login:)(.*)" (trim input-singleline))
        password (re-find #"(^password:)(.*)" (trim input-singleline))
        other (re-find #"(.*)(:)(.*)" (trim input-singleline))]
    (cond
      login {:login (trim (last login))}
      password {:password (trim (last password))}
      other {}
      :else {:password input-singleline})))

(s/defn ^:always-validate parse-record :- PasswordStoreRecord
  [input-multiline :- s/Str]
  (apply merge
    (map parse-line
      (string/split-lines (trim input-multiline)))))

(s/defn get-secret-wo-newline :- s/Str
  [path :- s/Str]
  (trim (get-secret path)))

(s/defn get-secret-record :- s/Str
  [record-spec :- PasswordStoreRecordSpec]
  (get
    (parse-record (get-secret (:path record-spec)))
    (:element record-spec)))
