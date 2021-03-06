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

(ns dda.config.commons.1-5.map-utils
  {:deprecated "1.5"}
  (:require
     [schema.core :as s :include-macros true]))

(defn deep-merge
  "Recursively merge maps."
  [& ms]
  (letfn [(f [l r]
            (cond (and (map? l) (map? r))
                  (deep-merge l r)
                  (or (sequential? l) (set? l))
                  (into l r)
                  :else r))]
    (apply merge-with f ms)))

(defn schema-keys
  "returns all keys from schema."
  [schema]
  (map
    #(if (instance? schema.core.OptionalKey %) (:k %) %)
    (keys schema)))

(s/defn filter-for-target-schema
  "filter a (partial-) config in order to match the given target schema."
  [schema partial-config]
  (select-keys partial-config (schema-keys schema)))
