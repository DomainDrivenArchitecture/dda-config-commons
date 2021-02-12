(ns dda.config.commons.secret-config
  (:require [aero.core :as aero]
            [clojure.java.shell :refer [sh]]))

; Read password from gopass (use #gopass)
; Read password from file (use #include)
; Read password from env (use #env)

; Read config with aero/read-config

; Gopass implementation with aero
; #gopass PATH
(defn read-gopass [name]
  (let [result (sh "gopass" name)]
    (if (= (:exit result) 0)
      (:out result)
      (throw (RuntimeException. (:err result))))))

(defmethod aero/reader 'gopass
  [{:keys [profile] :as opts} tag value]
  (read-gopass value))

