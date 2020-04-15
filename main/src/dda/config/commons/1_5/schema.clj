(ns dda.config.commons.1-5.schema
  {:deprecated "1.5"}
  (:require [clojure.edn :as edn]
            [clojure.string :as string]))

(defn ipv4? [s]
  (let [parts (string/split s #"\.")]
    (and
     (= (count parts) 4)
     (every?
      (fn [part]
        (try
          (let [n (edn/read-string part)]
            (and (integer? n) (>= 256 n 0)))
          (catch Exception _ false)))
      parts))))
