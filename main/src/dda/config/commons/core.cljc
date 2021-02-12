(ns dda.config.commons.core
  (:require
    #?@(:clj []
        :cljs [])
))

(defn helloworld [arg]
  #?(:clj  (println "Hallo Welt!" arg)
     :cljs (js/console.log "Hallo Welt" arg)))