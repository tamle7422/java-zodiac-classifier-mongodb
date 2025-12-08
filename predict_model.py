import sys
import joblib
import os
from pathlib import Path

# ------------------------------------------------------------------
# Find the directory where THIS file (predict_model.py) lives
# ------------------------------------------------------------------
CURRENT_DIR = Path(__file__).parent.resolve()
MODEL_PATH = CURRENT_DIR / "zodiac_model.pkl"

# Optional: helpful error if someone moves the file
if not MODEL_PATH.exists():
    raise FileNotFoundError(f"Model file not found! Expected at: {MODEL_PATH}")

# Load the model
model = joblib.load(MODEL_PATH)

# ---------------------------------------------------------
# Read input text from command line argument
# ---------------------------------------------------------
if len(sys.argv) < 2:
    print("No input")
    sys.exit(0)

text = sys.argv[1].strip()

# ---------------------------------------------------------
# Keyword check: require at least 3 keywords
# ---------------------------------------------------------
keywords = [word for word in text.split() if word.strip()]
if len(keywords) < 3:
    print("Not enough keywords")
    sys.exit(0)

# ---------------------------------------------------------
# Predict
# ---------------------------------------------------------
prediction = model.predict([text])[0]
print(prediction)
