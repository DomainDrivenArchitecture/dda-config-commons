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
(ns dda.config.commons.1-5.secret
  {:deprecated "1.5"}
  (:require
    [schema.core :as s]
    [schema.spec.core :as spec]
    [dda.config.commons.1-5.secret.passwordstore :as ps]))

(def SecretSchemas
  (into
    [{:plain s/Str}]
    ps/PasswordStore))

(def Secret
    (apply s/either SecretSchemas))

(s/defn dispatch-by-secret-type :- s/Keyword
  "Dispatcher for secret resolving. Also does a
   schema validation of arguments."
  [secret :- Secret
   & _]
  (first (keys secret)))

(defmulti resolve-secret
  "resolves the secret"
  dispatch-by-secret-type)

(s/defmethod ^:always-validate resolve-secret :default
  [secret :- Secret]
  (throw (UnsupportedOperationException. (str "Not impleneted yet: resolve-secret for " secret))))

(s/defmethod ^:always-validate resolve-secret :plain
  [secret :- Secret
   & _]
  (:plain secret))

(s/defmethod ^:always-validate resolve-secret :password-store-single
  [secret :- Secret
   & _]
  (ps/get-secret-wo-newline (:password-store-single secret)))

(s/defmethod ^:always-validate resolve-secret :password-store-record
  [secret :- Secret
   & _]
  (ps/get-secret-record (:password-store-record secret)))

(s/defmethod ^:always-validate resolve-secret :password-store-multi
  [secret :- Secret
   & _]
  (ps/get-secret (:password-store-multi secret)))

(defn create-custom-resolved-schema
  "Replaces all given secret-schemass within the given schema-config by Str."
  [schema-config secret-schema]
  (clojure.walk/postwalk
    (fn [x]
      (if (= x secret-schema)
        s/Str
        x))
    schema-config))

(defn create-resolved-schema
  "Replaces all Secrets within the given schema-config by Str."
  [schema-config]
  (create-custom-resolved-schema schema-config Secret))

(defn resolve-custom-secrets
  "Takes a config, a corresponding schema and a secret-schema.
   Resolves all secret-schemas within the config"
  [config schema secret-schema]
  (s/validate schema config)
  ((spec/run-checker
     (fn [s params]
       (let [walk (spec/checker (s/spec s) params)]
         (fn [x]
           (if (= s secret-schema)
             (resolve-secret x)
             (walk x)))))
     true
     schema)
   config))

(defn resolve-secrets
  "Takes a config and a corresponding schema.
   Resolves all Secrets within the config."
  [config schema]
  (resolve-custom-secrets config schema Secret))
