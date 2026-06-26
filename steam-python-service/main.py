import requests
from fastapi import FastAPI
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from collections import Counter
import string

nltk.download('punkt', quiet=True)
nltk.download('punkt_tab', quiet=True)
nltk.download('stopwords', quiet=True)

app = FastAPI(title="Steam Vibe Engine")

VIBE_KEYWORDS = {
    "cozy", "relaxing", "stressful", "atmospheric", "difficult",
    "casual", "competitive", "dark", "cute", "scary", "beautiful",
    "sandbox", "pixel", "farming", "puzzle", "chill", "funny", "grindy"
}

def fetch_steam_reviews(app_id: str):
    url = f"https://store.steampowered.com/appreviews/{app_id}?json=1&language=english&num_per_page=100"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        return [review['review'] for review in data.get('reviews', [])]
    return []

def extract_vibes(reviews: list):
    text = " ".join(reviews).lower()
    
    tokens = word_tokenize(text)
    
    stop_words = set(stopwords.words('english'))
    punctuation = set(string.punctuation)
    
    filtered_words = [
        word for word in tokens 
        if word not in stop_words and word not in punctuation and word in VIBE_KEYWORDS
    ]
    
    vibe_counts = Counter(filtered_words)
    
    return [vibe for vibe, count in vibe_counts.most_common(3)]

@app.get("/")
def health_check():
    return {"status": "Python Vibe Engine is running"}

@app.get("/api/vibe/{app_id}")
def analyze_vibe(app_id: str):
    reviews = fetch_steam_reviews(app_id)
    
    if not reviews:
        return {"app_id": app_id, "vibes": [], "message": "No reviews found."}

    top_vibes = extract_vibes(reviews)
    
    return {
        "app_id": app_id,
        "vibes": top_vibes,
        "message": f"Analyzed {len(reviews)} reviews."
    }