import numpy as np
import os
from flask import Flask, request, jsonify
import pickle
from flask_cors import CORS

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

model = pickle.load(open('model.pkl', 'rb'))

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        Age = float(data['age'])
        Weight = float(data['weight'])
        Height = float(data['height'])

        generate = 66.5 + (13.75 * Weight) + (5.003 * Height) - (6.75 * Age)
        Calories = generate * 0.666
        Protein = generate * 0.333
        
        final_features = np.array([[Calories, Protein]])
        
        prediction = model.predict(final_features)
        
        return jsonify({'error': False, 'message': 'success', 'predict' : prediction[0]}), 200

    except Exception as e:
        return jsonify({'error': True, 'message': 'Terjadi kesalahan'}), 500

if __name__ == "__main__":
    from gevent.pywsgi import WSGIServer
    http_server = WSGIServer(('0.0.0.0', 5000), app)
    http_server.serve_forever()
