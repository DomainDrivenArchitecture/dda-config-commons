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
(ns dda.config.commons.secret
  (:require
    [schema.core :as s]
    [dda.config.commons.passwordstore-adapter :as adapter]))

(def Secret {(s/optional-key :plain) s/Str
             (s/optional-key :password-store-single) s/Str
             (s/optional-key :password-store-record) {:path s/Str
                                                      :element (s/enum :password :login)}
             (s/optional-key :password-store-multi) s/Str})


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
  (adapter/get-secret-wo-newline (:password-store-single secret)))
(s/defmethod ^:always-validate resolve-secret :password-store-multi
  [secret :- Secret
   & _]
  (adapter/get-secret (:password-store-multi secret)))
