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
(ns dda.config.commons.gpg-key-test
  (:require
   [clojure.test :refer :all]
   [dda.config.commons.gpg-key :as sut]))

(def public-gpg-key
"-----BEGIN PGP PUBLIC KEY BLOCK-----
Version: GnuPG v1

mI0EWbJf3wEEALq5GqjqzlYRLLXa3oz0Ow/nPnzurAPr1+yn2UZinSMk6wgB6ayf
9DySPvUW9+VLrlLvCSLEo/tX2NwSdn6ZWR454inlmJrzm+G5JPWc4ANhCE61JVME
l6pPXTQ+CgsPLQspq6leD7+WrBEIKQuXZpcQv2HnirvbNKeYn9eT1xibABEBAAG0
CHRlc3R1c2VyiLgEEwECACIFAlmyX98CGwMGCwkIBwMCBhUIAgkKCwQWAgMBAh4B
AheAAAoJEEoEofT3ZQcF/dEEALTrwkAWtpy219Y36pt6IxcvPG8ei8j3/sAP7IX4
mHR0cTObZQO2jKaTr8hBRNX9Xt/kiLflIbj9oIt56WeT31FFjISI8wg+mT9e8F3J
WHZTsvoXmHuQs4m2Vk/9Wkkcvv5uJuhzM+SAsB24cvkluLeUaxjZ8HgIN+LCyGlL
ioDTuI0EWbJf3wEEAOtyW/nlXK6DXhfwZOWemI+QheyIzgnR21rz2emUIBRIzgc4
OU/SSFTIIOo1e/S8ReCV6/cLK6HubWb79TG32SSEF0jxcbY8SA3ylvJeHnfOpwOh
RDDyNSJU422kNt6ojblQZ+bh8OQciZcOoNuNlVVBelWlt1t8kJGA2+hOkCehABEB
AAGInwQYAQIACQUCWbJf3wIbDAAKCRBKBKH092UHBf8NA/9bZdANGsp5uLxyaMIO
J/wzlkhuyRpR9Ht+VdzARdzFEQLo77TLGEA1N/Sa2afgD3VxveR4LZaWhFK6F670
CN8eNvWgBPPb5UT/wngyJZTPWkkcKUx0v5k4J6K1aCXLNPNTN94SBRsohEw2pxp4
tszJa3+UaMuSzvG+Tlv8woAv8w==
=WIFr
-----END PGP PUBLIC KEY BLOCK-----")
  

(deftest test-configs
  (is (= "4a04a1f4f7650705" (sut/hex-id public-gpg-key))))