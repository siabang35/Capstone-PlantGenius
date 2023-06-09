# Machine Learning Path's Overview  
As a machine learning engineers, we mainly contributed in making a model using Keras in TensorFlow to detection commodity plant diseases images that will be captured by PlantGenius. To encapsulate the machine learning path guidelines in this project, here are some points to consider:

## Machine Learning Job Desc  
* Collect the dataset.
* Pre-processing the images using ImageDataGenerator.
* Create and Training model using `EfficientNetB3` and some additional layers.
* Saved the best model.
* Predict some images with the best model.

## Collecting The Dataset  
For building Lookies prototype, we collected around 75000 images of 14 Commodity Plant Indonesian varieties, which are:
1. Banana
2. Cacao
3. Cassava
4. Coffee
5. Corn
6. Guava
7. Mango
8. Orange
9. Potato
10. Rice
11. Soybean
12. Squash
13. Tea
14. Tomato

The used dataset were split into 3 sets, that are training, validation, and testing set. The training set contains balance of each disease's class, whereas 20 images are included in validation and testing set respectively in each of the disease's class. We collected dataset manually from kaggle source and combine into one, 
[Kaggle](https://www.kaggle.com/datasets/tanukii/plantgeniusdataset)

## The General Information of The Pre-trained Model Using EfficientNetB3  
The `EfficientNetB3`is a model that extracts some important features by adapting the equilibrium between the depth, width, and resolution of a network. We choose `EfficientNetB3` because this model had the best accuracy than the other models we tested .

## The Commodity Plant Disease Model Notebook 
We utilized the `EfficientNetB3` pre-trained model to extract some feautures as it's mentioned above, then fine-tuned the model with adding the `CNN layers` on it. The built model generates 81.88% of accuracy in testing. Please refer to this [link](https://github.com/siabang35/Capstone-PlantGenius/blob/master/Machine_Learning/building_model_81_accuracy.ipynb) to view the complete steps and result.

## The Notebook of Predicting The Commodity Plant Disease Images  
After had the model, we use the model to predict some images. Please refer to this [notebook](https://github.com/siabang35/Capstone-PlantGenius/blob/master/Machine_Learning/predict_Images.ipynb) to view the complete steps to predict images and also the result.

## References
* Tiwari V, Joshi R, Dutta M. (2021). Dense convolutional neural networks based multiclass plant disease detection and classification using leaf images. *Ecological informatics, 63*, 2021.
* Sankar A. (2020). Disease Detection in Spinach Leaves using Image Processing and Machine Learning. *International Journal of Innovative Research in Electrical, 8*, 2020.
* kaggle.com. (2018). Kue Indonesia. Access on May 12th, 2023, from this [link]([(https://www.kaggle.com/datasets/vipoooool/new-plant-diseases-dataset)]).
