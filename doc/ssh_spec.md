
# Example
```clojure
(def PublicSshKey
  {:type s/Str
   :public-key s/Str
   :comment s/Str})

(def PrivateSshKey s/Str)

(def SshKeyPair
  {:public-key PublicSshKey
   :private-key PrivateSshKey})
```
