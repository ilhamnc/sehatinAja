import numpy as np
from flask import Flask, request, jsonify
import pickle

app = Flask(__name__) # Inisialisasi aplikasi Flask
model = pickle.load(open('model.pkl', 'rb')) # Memuat model yang telah dilatih

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Menerima input dari JSON
        data = request.get_json()
        Age = float(data['age'])
        Weight = float(data['weight'])
        Height = float(data['height'])

        generate = 66.5 + (13.75 * Weight) + (5.003 * Height) - (6.75 * Age)
        Calories = generate * 0.666
        Protein = generate * 0.333
        
        # Membuat array input untuk prediksi
        final_features = np.array([[Calories, Protein]])
        
        # Melakukan prediksi
        prediction = model.predict(final_features)
        
        # Output prediksi dalam bentuk JSON
        output = {'prediction': prediction[0], 'status_code': 200}

        return jsonify(output), 200 # Mengembalikan prediksi dalam bentuk JSON dengan status 200

    except Exception as e:
        return jsonify({'error': 'Terjadi kesalahan', 'status_code': 500}), 500 # Mengembalikan pesan error dengan status 500 jika terjadi kesalahan

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=int(os.environ.get('PORT', 8080))) # Menjalankan aplikasi dengan port yang sesuai
