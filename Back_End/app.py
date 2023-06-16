from flask import Flask, request, jsonify
from helpers.handler import disease_prediction

app = Flask(__name__)


@app.route('/predict', methods=['POST'])
def predict():
        try:
            data = request.get_json()
            image_url = data['imagePath']
            result = disease_prediction(image_url)
            return jsonify({
            'prediction': result
            })
        except Exception as e:
             return str(e)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
