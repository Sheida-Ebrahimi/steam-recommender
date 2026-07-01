# Steam Vibe Engine

A full-stack, microservices-based recommendation engine that helps users find new Steam games based on specific "vibes" (e.g., cozy, cyberpunk, stressful). It actively scans Steam reviews using Natural Language Processing (NLP) to categorize games and cross-references them with the user's personal Steam library.
Still in development.

## Key Features
* **Smart Filtering:** Authenticates with the Steam Web API to check your current library and shows what you own and don't.
* **NLP Vibe Extraction:** Uses Python and NLTK to scrape and parse the 100 most recent user reviews for a game, extracting the true "vibe" based on community sentiment rather than developer tags.
* **Autonomous Ingestion:** A background Java service passively pulls trending and top-selling games from the Steam Store and queues them for NLP processing without any manual intervention.
* **Cache-Aside Architecture:** Utilizes Redis to cache user Steam libraries with a 24-hour TTL, drastically reducing API latency and preventing rate-limiting from Steam.

## System Architecture

This project is built using a decoupled microservices architecture:

1. **Frontend (Next.js / React / Tailwind CSS):** The user interface. Sends queries and renders dynamic Steam Store capsule art directly from Steam's CDN.
2. **Orchestrator Backend (Spring Boot / Java):** The central hub. Handles HTTP requests, manages CORS, orchestrates data flow, and schedules the background ingestion tasks.
3. **NLP Microservice (FastAPI / Python):** A dedicated data-science worker. Receives App IDs from Java, scrapes reviews, tokenizes text, removes stop-words, and returns keyword frequencies.
4. **Primary Database (PostgreSQL):** Persistent storage for the game catalog, pricing data, and extracted vibe metadata.
5. **Memory Cache (Redis):** Lightning-fast temporary storage to intercept and serve repeat API requests.

## Getting Started

### Prerequisites
* Java 17+
* Node.js & npm
* Python 3.9+
* Docker Desktop (for Postgres & Redis)
* A Steam Web API Key

### 1. Infrastructure Setup (Docker)
Ensure Docker is running, then spin up the database and cache containers:
```docker compose up -d ```
### 2. Python NLP Service Setup
Navigate to the Python directory, create a virtual environment, install dependencies, and start the FastAPI server:
```
cd steam-python-service
python -m venv venv
# On Windows: .\venv\Scripts\activate
# On Mac/Linux: source venv/bin/activate
pip install fastapi uvicorn nltk
uvicorn main:app --reload --port 8000
```
### 3. Spring Boot Backend Setup

Navigate to the Java directory. First, add your Steam API key to ```src/main/resources/application.properties```:
```
steam.api.key=YOUR_STEAM_API_KEY_HERE
spring.datasource.url=jdbc:postgresql://localhost:5432/steamdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.data.redis.host=localhost
spring.data.redis.port=6379
```
Then, run the Spring Boot application via your IDE or Maven:
```
./mvnw spring-boot:run
```


### 4. Next.js Frontend Setup
Navigate to the frontend directory, install packages, and start the development server:
```
cd steam-frontend
npm install
npm run dev
```

Open ```http://localhost:3000``` in your browser. The backend will immediately begin ingesting and analyzing games in the background.

