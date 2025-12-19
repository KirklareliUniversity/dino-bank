# 📦 Bağımlılıklar ve Kurulum Notu

Bu dosya, GitHub'a **yüklenmeyen** bağımlılıklar ve kurulum gereksinimleri hakkında bilgi verir.

---

## 🚫 Git'e Yüklenmeyen Klasörler

| Klasör | Açıklama | Neden Yüklenmedi |
|--------|----------|------------------|
| `node_modules/` | Frontend bağımlılıkları | Çok büyük (~200MB+), `npm install` ile indirilir |
| `target/` | Java derlenmiş dosyalar | Build çıktısı, `mvn package` ile oluşur |
| `.idea/` | IntelliJ ayarları | IDE'ye özel |

---

## ⚙️ Projeyi Çalıştırmak İçin

### 1. Backend (Spring Boot)
```bash
cd dino_bank/dinobank
./mvnw spring-boot:run
```
> **Gerekli:** Java 17+, Maven (otomatik indirilir)

### 2. Frontend (React + Vite)
```bash
cd dinoframe
npm install    # Bağımlılıkları indir (ilk seferde)
npm run dev    # Geliştirme sunucusunu başlat
```
> **Gerekli:** Node.js 18+, npm

### 3. Veritabanı
- PostgreSQL gerekli
- `dino_bank/database/dinobank.sql` dosyasını çalıştır

---

## 📋 Özet

Projeyi klonladıktan sonra **sadece bir kez** şunları çalıştır:
```bash
cd dinoframe && npm install
```

Artık `run.sh` ile projeyi başlatabilirsin.
