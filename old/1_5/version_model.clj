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

(ns dda.config.commons.1-5.version-model
  {:deprecated "1.5"}
   (:require [schema.core :as s :include-macros true]))

(def Version
  "A schema for a nested data type"
  [(s/one s/Int "major") (s/one s/Int "minor") (s/one s/Int "patch") (s/optional s/Int "pre-release")])


(s/defn ver_str2int :- s/Int
  "Converts string to integer, ignores all characters but 0-9.
   Returns 0 if string is empty."
  [str :- s/Str]
  (if (= 0 (count (re-find  #"\d+" str)))
    0
    (Integer. (re-find  #"\d+" str))))

(s/defn ver_fromstr :- Version
  "Converts formated version string (e.g. 1.2.3.4) to version vector [1 2 3 4].
   Shorter versions will be filled with zeros (e.g. 1.2 to [1 2 0]).
   Longer versions will be trimmed (e.g. 1.2.3.4.5 to [1 2 3 4]).
   nil is Version [0 0 0]."
  [str]
  (if (nil? str) [0 0 0]
   (let [ver (into [] (map ver_str2int (clojure.string/split str #"\.")))
         len (count ver)]
     (cond
       (< len 3) (concat ver (repeat (- 3 len) 0))
       (> len 4) (take 4 ver)
       :else ver))))

(s/defn ver_str :- s/Str
 "Converts version vector to point seperated string.
   Example (= (ver_str [2 1 4]) \"2.1.4\") "
 [version :- Version]
 (clojure.string/join "." version))

;TODO: Decide if this method is required.
(defn ver_fill [ver length]
  "Fills up a version vector with trailing zeros.
   Example (= (verfill [2 1] 4) [2 1 4 4]) "
  (if (> length (count ver))
    (concat ver (repeat (- length (count ver)) 0))
    ver))


(defn ver_comp [v1 v2]
  "Compares two version vectors and return the difference of the first postion they differ.
   Returns nil if they are the same version."
  (let [len (max (count v1) (count v2))]
   (first (drop-while #(= % 0) (mapv - (ver_fill v1 len) (ver_fill v2 len))))))


(defn ver_less [v1 v2]
  "Returns v1 < v2"
  (let [comp (ver_comp v1 v2)]
    (if (nil? comp) false (< comp 0))))

(defn ver_lesseq [v1 v2]
  "Returns v1 <= v2"
  (let [comp (ver_comp v1 v2)]
    (if (nil? comp) true (< comp 0))))

(defn ver_eq [v1 v2]
  "Returns v1 == v2"
  (let [comp (ver_comp v1 v2)]
    (nil? comp)))
