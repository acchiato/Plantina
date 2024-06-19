import requests
import json

url = 'http://127.0.0.1:5000/predict'

# data=json.dumps({'pH':8.55, 'Temperature':33})

r = requests.get(url,json={'pH':8.55, 'Temperature':33})
print(r.json())