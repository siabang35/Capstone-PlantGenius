import numpy as np
import tensorflow as tf

disease_class = [
        'Banana cordana', 'Banana healthy', 'Banana pestalotiopsis', 'Banana sigatoka', 'Cacao black pod rot',
        'Cacao healthy', 'Cacao pod borer', 'Cassava brown leaf spot', 'Cassava brown streak disease',
        'Cassava green mottle', 'Cassava healthy', 'Cassava mosaic disease', 'Cassava resistance marker',
        'Coffee cercospora', 'Coffee healthy', 'Coffee leaf rust', 'Coffee miner', 'Coffee phoma',
        'Corn cercospora leaf spot', 'Corn common rust', 'Corn northern leaf blight', 'Corn healthy',
        'Corn gray leaf spot', 'Guava canker', 'Guava dot', 'Guava healthy', 'Guava mummification',
        'Guava Rust', 'Mango anthracnose', 'Mango bacterial canker', 'Mango cutting weevil', 'Mango die back',
        'Mango gall midge', 'Mango healthy', 'Mango powdery mildew', 'Mango sooty mould',
        'Orange haunglongbing', 'Potato early blight', 'Potato late blight', 'Potato healthy', 'Rice sogatella',
        'Rice Tungro', 'Rice bacterial leaf blight', 'Rice brown spot', 'Rice healthy', 'Rice hispa',
        'Rice leaf blast', 'Soybean healthy', 'Squash powdery mildew', 'Tea anthracnose', 'Tea algal leaf',
        'Tea brown blight', 'Tea gray light', 'Tea healthy', 'Tea helopeltis', 'Tea red leaf spot', 'Tea white spot',
        'Tomato bacterial spot', 'Tomato early blight', 'Tomato late blight', 'Tomato leaf mold',
        'Tomato septoria leaf spot', 'Tomato spider mites', 'Tomato target spot', 'Tomato mosaic virus',
        'Tomato yellow leaf curl virus', 'Tomato healthy', 'Potato hollow heart'
    ]

def load_model():
    model_path = './saved_model'
    model = tf.saved_model.load(model_path,)
    return model

def disease_prediction(file_path):
    img = tf.keras.preprocessing.image.load_img(file_path)
    x = tf.keras.preprocessing.image.img_to_array(img)
    x = x / 255.0
    x = tf.image.resize(x, (150, 150))
    x = np.expand_dims(x, axis=0)


    model = load_model()
    pred = model(x)
    index = np.argmax(pred)
    pred_disease = disease_class[index]

    return pred_disease
