(ns dda.config.commons.user-env
  (:require
   [schema.core :as s]
   [dda.config.commons.ssh-key :as schema]))

(defn user-home-dir
  "provides the user home path."
  [user-name]
  (if (= user-name "root")
    "/root"
    (str "/home/" user-name)))

(defn format-public-key
  "returns a formatted public-key from an ssh-config"
  [ssh-public-key-config]
  (str
   (:type ssh-public-key-config) " "
   (:public-key ssh-public-key-config) " "
   (:comment ssh-public-key-config)))

(s/defn string-to-pub-key-config [pub-key :- s/Str] :- schema/PublicSshKey
  "function takes a public-key as a string and returns it as a ssh-public-key-config"
  (let [col (clojure.string/split pub-key #" ")]
    {:type (first col)
     :public-key (second col)
     :comment (nth col 2)}))

(defn user-ssh-dir
 "provides the user .ssh path."
 [user-name]
 (str (user-home-dir user-name) "/.ssh/"))

(s/defn ssh-priv-key-from-env-to-config :- schema/PrivateSshKey
 "function reads ssh private key from environment variable and returns it as a String"
 []
 (let [env-variable "SSH_PRIV_KEY"]
   (System/getenv env-variable)))

(defn read-ssh-pub-key-to-config
 "read the ssh-public-key to a config"
 [& {:keys [ssh-dir-path]}]
 (let [ssh-dir (or ssh-dir-path (str (System/getenv "HOME") "/.ssh"))]
   (string-to-pub-key-config (slurp (str ssh-dir "/id_rsa.pub")))))

(defn read-ssh-priv-key-to-config
 "read the ssh-private-key to a config"
 [& {:keys [ssh-dir-path read-from-env?]}]
 (let [ssh-dir (or ssh-dir-path (str (System/getenv "HOME") "/.ssh"))]
   (if read-from-env? (ssh-priv-key-from-env-to-config) (slurp (str ssh-dir "/id_rsa")))))

(defn read-ssh-keys-to-pair-config
 [& {:keys [ssh-dir-path read-from-env?]}]
 "read ssh-keys from current node to ssh-key-pair-config. If read-from-env? flag is specified,
  ssh-private-key will be read from enviroment variable SSH_PRIV_KEY"
 {:public-key (read-ssh-pub-key-to-config :ssh-dir-path ssh-dir-path)
  :private-key (read-ssh-priv-key-to-config :ssh-dir-path ssh-dir-path
                                            :read-from-env? read-from-env?)})
