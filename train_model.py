import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neural_network import MLPClassifier
from sklearn.pipeline import Pipeline
from sklearn.metrics import classification_report
import joblib

# -----------------------------------------
# Load your dataset (CSV with keywords,zodiac)
# -----------------------------------------
data = pd.read_csv("train_data.csv")  # update your path if needed

X = data["keywords"].astype(str)
y = data["zodiac"]

# -----------------------------------------
# Train-test split
# -----------------------------------------
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)

# -----------------------------------------
# Pipeline: TF-IDF → Neural Network
# -----------------------------------------
pipeline = Pipeline([
    ("tfidf", TfidfVectorizer(
        lowercase=True,
        stop_words="english",
        ngram_range=(1, 2)  # unigrams + bigrams
    )),
    ("mlp", MLPClassifier(
        hidden_layer_sizes=(128, 64),  # 2-layer neural net
        activation="relu",
        solver="adam",
        max_iter=500,
        random_state=42,
    ))
])

# -----------------------------------------
# Train model
# -----------------------------------------
pipeline.fit(X_train, y_train)

# -----------------------------------------
# Evaluate
# -----------------------------------------
y_pred = pipeline.predict(X_test)
print("\nClassification Report:\n")
print(classification_report(y_test, y_pred))

# -----------------------------------------
# Save model
# -----------------------------------------
joblib.dump(pipeline, "zodiac_model.pkl")
print("Neural network model saved as zodiac_model.pkl")
