(ns dda.config.commons.aero
  (:require [aero.core :as a]))
            
(defn load-and-resolve [path] (a/read-config path))