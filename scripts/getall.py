#!/usr/bin/env python3

import requests

headers = {'Content-Type': 'application/json'}
url = "http://localhost:8080/cidrs"
r = requests.get(url, headers=headers)
print(r)
print(r.text)
