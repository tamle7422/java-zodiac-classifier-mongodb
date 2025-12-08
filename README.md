# Zodiac Classifier Web Application

A web application that predicts a user's zodiac sign based on provided keywords using a Python-based prediction model, with history tracking stored in MongoDB. Built with **Spring Boot**, **Thymeleaf**, and **MongoDB**.

---

## Features

- Predict zodiac sign from user input keywords
- Save predictions to MongoDB for history tracking
- Display prediction history in a table with timestamps
- Responsive UI using Bootstrap 5

---

## Project Structure

backend/
├─ src/main/java/com/example/zodiac/
│ ├─ controller/
│ │ └─ ZodiacController.java
│ ├─ model/
│ │ └─ Prediction.java
│ ├─ repository/
│ │ └─ PredictionRepository.java
│ └─ service/
│ └─ PredictionService.java
├─ resources/
│ ├─ templates/
│ │ ├─ index.html
│ │ └─ history.html
│ └─ application.properties
└─ pom.xml
predict_model.py # Python script for zodiac prediction


---

## Prerequisites
- Java 17+
- Maven 3.8+
- MongoDB running locally (default: `mongodb://localhost:27017`)
- Python 3.x installed
- Required Python packages:
  ```bash
  pip install pandas scikit-learn joblib

Configuration:
application.properties:
spring.data.mongodb.uri=mongodb://localhost:27017/zodiacdb
spring.thymeleaf.cache=false
server.port=8080

Start MongoDB:
mongod --dbpath /path/to/your/db

Start Spring Boot:
mvn spring-boot:run

Access the App:
Predict page: http://localhost:8080/
History page: http://localhost:8080/history

Python Prediction Script:
The Python script predict_model.py should be located outside the backend folder, for example:
/path/zodiac-classifier-mongodb/predict_model.py

The spring boot service calls it like so.
ProcessBuilder pb = new ProcessBuilder(
    "python3",
    "/path/to/predict_model.py",
    keywords
);

Happy Coding!