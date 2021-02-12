(ns dda.config.commons.core
  (:require
    #?(:clj [aero.core :as aero]
       :cljs [aero.core :as aero])
))

(defn helloworld [arg]
  #?(:clj  (println "Hallo Welt!" arg)
     :cljs (js/console.log "Hallo Welt" arg)))