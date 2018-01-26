### The Schema is:
```clojure
(def SecretSchemas
    [{:plain Str}
     {:password-store-single Str}
     {:password-store-record
       {:path Str,
        :element (enum :password :login)}}
     {:password-store-multi Str}]
```

it's located in:
```
dda.config.commons.secret
```
and it's used in:
```

```
