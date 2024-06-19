from flask import Flask, request, render_template
import pickle
import numpy as np
from flask_restful import Api,Resource
from flask_cors import CORS
from flask import jsonify
import seaborn as se
import pandas as pd

app = Flask(__name__)   
CORS(app)
API_NAME = Api(app)
app.config['SECRET_KEY'] = 'disable the web security'
app.config['CORS_HEADERS'] = 'Content-Type'

model = pickle.load(open("soil_model.pkl","rb"))

@app.route("/")
def home():
    return "Welcome to Plantina mobile API!"
class SoilPrediction(Resource):
    def post(self):
        info = request.get_json()
        ph_value = info['pH']
        temperature_value = info['Temperature']
    
        data = ([[ph_value, temperature_value]])
        prediction = model.predict(data)[0]
       
        return {'prediction':prediction}




API_NAME.add_resource(SoilPrediction, '/predict', methods=['POST', 'GET'])


if __name__ == '__main__':
        app.run()
