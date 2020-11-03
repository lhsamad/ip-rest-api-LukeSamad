#!/usr/bin/env python3

import requests

headers = {'Content-Type': 'application/json'}
url = "http://localhost:8080/cidr?cidr=10.0.0.1/24"
r = requests.get(url, headers=headers)
print(r)
print(r.text)
