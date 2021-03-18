# dda-config-commons
[![Clojars Project](https://img.shields.io/clojars/v/dda/dda-config-commons.svg)](https://clojars.org/dda/dda-config-commons)
[![Build Status](https://travis-ci.org/DomainDrivenArchitecture/dda-config-commons.svg?branch=master)](https://travis-ci.org/DomainDrivenArchitecture/dda-config-commons)

[![Slack](https://img.shields.io/badge/chat-clojurians-green.svg?style=flat)](https://clojurians.slack.com/messages/#dda-pallet/) | [<img src="https://meissa-gmbh.de/img/community/Mastodon_Logotype.svg" width=20 alt="team@social.meissa-gmbh.de"> team@social.meissa-gmbh.de](https://social.meissa-gmbh.de/@team) | [Website & Blog](https://domaindrivenarchitecture.org)

## Compatibility

dda-config-commons is compatible to the following versions
 * clojure 1.9

## Purpose

dda-config-commons handles configuration for configuration management systems / provisioning systems. This covers:
* load & store configuration
* handle credentials (load from safe places, resolve em, log outputs)
* tbd. support various data-formats (edn, yml, json)
* tbd. place for common spec predicates
* tbd. place for utilities around gpg / maps / user-envs / operatings-system passwords

## Usage

Use load-and-resolve to pass an edn file to aero and pull secrets out of gopass.
Currently supported are the following operations:
* default operations in aero: #env, #join, #include, etc. (Documented [here](https://github.com/juxt/aero))
* #gopass [PATH] (load secrets directly from gopass)

Check the config in test for an example.

## License

Copyright © 2015, 2016, 2017, 2018, 2019 meissa GmbH
Licensed under the [Apache License, Version 2.0](LICENSE) (the "License")
Pls. find licenses of our subcomponents [here](doc/SUBCOMPONENT_LICENSE)

