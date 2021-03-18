(ns dda.config.commons.secret-config
  (:require [aero.core :as aero]
            [clojure.java.shell :refer [sh]]))

(defn read-gopass [name]
  (let [result (sh "gopass" name)]
    (if (= (:exit result) 0)
      (:out result)
      (throw (RuntimeException. (:err result))))))

(defmethod aero/reader 'gopass
  [{:keys [profile] :as opts} tag value]
  (read-gopass value))

(defn load-and-resolve [path] (aero/read-config path))

