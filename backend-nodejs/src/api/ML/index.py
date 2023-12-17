from flask import Flask, request, jsonify
import joblib

app = Flask(__name__)

# Load model saat aplikasi dijalankan
model = joblib.load('model.joblib')

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    age = data['age']
    height = data['height']
    weight = data['weight']
    input_data = [[age, height, weight]]
    prediction = model.predict(input_data)
    return jsonify({'prediction': list(prediction)})


if __name__ == '__main__':
    app.run(debug=True)