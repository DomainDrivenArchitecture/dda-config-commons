(ns dda.config.commons.user-env
  (:require
    [dda.config.commons.user-env.ssh-key :as ssh-key]))

(defn read-ssh-pub-key-to-config
  [& args]
  (ssh-key/read-ssh-pub-key-to-config args))

(defn read-ssh-priv-key-to-config
  [& args]
  (ssh-key/read-ssh-priv-key-to-config args))

(defn read-ssh-keys-to-pair-config
  [& args]
  (ssh-key/read-ssh-keys-to-pair-config args))
