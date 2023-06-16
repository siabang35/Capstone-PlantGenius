# Cloud Computing Job Desk

- Deploy Machine Learning model on Google Cloud Platform using Compute Engine
- Create SQL instance to store data and connect to compute engine via private IP Address
- Create connector using Serverless VPC Access
- Create endpoints for login, register, search cake, send the result of machine learning process and to give informations about the cake to frontend
- Create cloud storage to store cake images

# plant-genius-server
server for plant genius application
# RESTAPIDocs

## Open Endpoints

Header Authorization = "token" 

Open endpoints require no Authentication.

* [Register](examples/users/register.md) : `POST /register/`
* [Login](examples/users/login.md) : `POST /login/`

## Endpoints that require Authentication

Closed endpoints require a valid Token to be included in the header of the
request. A Token can be acquired from the Login view above.

* [Logout](examples/users/logout.md) : `POST /logout/`
* [Fungsi](examples/fungsi/predict.md) : `POST /fungsi/`

### Current User related

Each endpoint can edit and displays information related to the User whose
Token is provided with the request:

* [History](examples/history/getHistory.md) :`GET /getHistory/`
* [DeleteHistory](examples/history/deleteHistory.md) : `POST /deleteHistory/`

