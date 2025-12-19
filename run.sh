#!/bin/bash
# run_dino.sh

echo "🦕 DinoBank Başlatılıyor..."

# Kill any process on port 8081
echo "🔌 8081 portu temizleniyor..."
lsof -t -i:8081 | xargs kill -9 2>/dev/null

# Change directory to backend
cd "dino_bank/dinobank"

# Run Spring Boot
echo "Backend ve Frontend (Spring Boot) başlatılıyor..."
sh mvnw spring-boot:run
