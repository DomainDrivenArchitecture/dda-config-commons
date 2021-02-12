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

(ns dda.config.commons.1-5.ssh-key
  {:deprecated "1.5"}
  (:require
   [schema.core :as s]
   [dda.config.commons.1-5.user-home :as user-home]))

(def PublicSshKey
  {:type s/Str
   :public-key s/Str
   :comment s/Str})

(def PrivateSshKey s/Str)

(def SshKeyPair
  {:public-key PublicSshKey
   :private-key PrivateSshKey})

(s/defn ^:always-validate
  string-to-pub-key-config :- PublicSshKey
  [pub-key :- s/Str]
  "function takes a public-key as a string and returns it as a ssh-public-key-config"
  (let [col (clojure.string/split pub-key #" ")]
    {:type (first col)
     :public-key (second col)
     :comment (nth col 2)}))

(s/defn ^:always-validate
  format-public-key :- s/Str
  "returns a formatted public-key from an ssh-config"
  [ssh-public-key-config :- PublicSshKey]
  (str
   (:type ssh-public-key-config) " "
   (:public-key ssh-public-key-config) " "
   (:comment ssh-public-key-config)))

(s/defn
  ssh-priv-key-from-env-to-config :- PrivateSshKey
 "function reads ssh private key from environment variable and returns it as a String"
 []
 (let [env-variable "SSH_PRIV_KEY"]
   (System/getenv env-variable)))

(defn user-home-dir
  "deprecated - pls use user-home/user-home-dir instead."
  {:deprecated "1.2"}
  [user-name]
  (user-home/user-home-dir user-name))

(defn user-ssh-dir
 "provides the user .ssh path."
 [user-name]
 (str (user-home-dir user-name) "/.ssh/"))

(s/defn ssh-priv-key-from-env-to-config :- PrivateSshKey
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
