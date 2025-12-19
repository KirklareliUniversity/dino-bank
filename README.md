# 🦕 DinoBank - Dijital Bankacılık Platformu

Modern bir dijital bankacılık uygulaması. Spring Boot backend ve React (Vite) frontend ile geliştirilmiştir.

---

## 📋 Gereksinimler

Projeyi çalıştırmak için aşağıdaki yazılımların sisteminizde kurulu olması gerekmektedir:

| Yazılım | Versiyon | Açıklama |
|---------|----------|----------|
| **Java JDK** | 21+ | Backend için zorunlu |
| **Node.js** | 18+ | Frontend için zorunlu |
| **PostgreSQL** | 14+ | Veritabanı |
| **Maven** | 3.8+ | Backend bağımlılıkları (wrapper dahil) |

---

## 🗄️ Veritabanı Kurulumu

### 1. PostgreSQL'de Veritabanı Oluşturma

```bash
# PostgreSQL'e bağlan
psql -U postgres

# Veritabanını oluştur
CREATE DATABASE dinobank;

# Çıkış
\q
```

### 2. Tabloları Oluşturma

`dino_bank/database/dinobank.sql` dosyasını PostgreSQL'de çalıştırın:

```bash
# dinobank veritabanına bağlan ve SQL dosyasını çalıştır
psql -U postgres -d dinobank -f dino_bank/database/dinobank.sql
```

**Veya** pgAdmin/DBeaver gibi bir araç kullanarak:
1. `dinobank` veritabanına bağlanın
2. Query Tool açın
3. `dinobank.sql` içeriğini yapıştırıp çalıştırın

### 3. Veritabanı Bağlantı Ayarları

`dino_bank/dinobank/src/main/resources/application.properties` dosyasını kendi sisteminize göre düzenleyin:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dinobank
spring.datasource.username=KULLANICI_ADINIZ
spring.datasource.password=SIFRENIZ
```

---

## 🚀 Projeyi Çalıştırma

### Yöntem 1: Script ile (Önerilen - macOS/Linux)

Proje kök dizininde:

```bash
chmod +x run.sh
./run.sh
```

Bu script:
- 8081 portunu temizler
- Backend'i başlatır

### Yöntem 2: Manuel Başlatma

#### Backend (Spring Boot)

```bash
# Backend dizinine git
cd dino_bank/dinobank

# Maven wrapper ile çalıştır
./mvnw spring-boot:run

# Windows için:
mvnw.cmd spring-boot:run
```

Backend **http://localhost:8081** adresinde çalışır.

#### Frontend (React + Vite)

```bash
# Frontend dizinine git
cd dinoframe

# Bağımlılıkları yükle (ilk kez)
npm install

# Geliştirme sunucusunu başlat
npm run dev
```

Frontend **http://localhost:5173** adresinde çalışır.

---

## 🌐 Erişim Adresleri

| Servis | URL | Açıklama |
|--------|-----|----------|
| **Frontend** | http://localhost:5173 | React uygulaması |
| **Backend API** | http://localhost:8081 | Spring Boot API |
| **API Health** | http://localhost:8081/actuator/health | Sağlık kontrolü |

---

## 📁 Proje Yapısı

```
dino_bank v1.1/
├── dino_bank/
│   ├── database/           # SQL dosyaları
│   │   └── dinobank.sql    # Veritabanı şeması
│   └── dinobank/           # Spring Boot Backend
│       ├── src/
│       │   └── main/
│       │       ├── java/   # Java kaynak kodları
│       │       └── resources/
│       │           └── application.properties
│       ├── mvnw            # Maven wrapper (Unix)
│       ├── mvnw.cmd        # Maven wrapper (Windows)
│       └── pom.xml         # Maven bağımlılıkları
├── dinoframe/              # React Frontend (Vite)
│   ├── src/                # React kaynak kodları
│   ├── package.json        # NPM bağımlılıkları
│   └── vite.config.js      # Vite yapılandırması
├── run.sh                  # Başlatma scripti
└── README.md               # Bu dosya
```

---

## 🛠️ Sorun Giderme

### Port Kullanımda Hatası

```bash
# 8081 portunu kullanan işlemi bul ve kapat
lsof -i :8081
kill -9 <PID>

# 5173 portunu kullanan işlemi bul ve kapat
lsof -i :5173
kill -9 <PID>
```

### Veritabanı Bağlantı Hatası

1. PostgreSQL servisinin çalıştığından emin olun:
   ```bash
   # macOS
   brew services start postgresql
   
   # Linux
   sudo systemctl start postgresql
   ```

2. `application.properties` dosyasındaki kullanıcı adı ve şifrenizi kontrol edin.

### Maven Build Hatası

```bash
# Temiz build
cd dino_bank/dinobank
./mvnw clean install -DskipTests
```

### Node Modülleri Hatası

```bash
# node_modules'u sil ve yeniden yükle
cd dinoframe
rm -rf node_modules
npm install
```

---

## 👤 Test Kullanıcıları

| Kullanıcı | Bakiye |
|-----------|--------|
| Osman | 1.000.000 TL |
| Kürşat | 100.000 TL |
| İlkmert | 100.000 TL |

---

## 📚 Teknoloji Yığını

**Backend:**
- Java 21
- Spring Boot 3.5.8
- Spring Security
- Spring Data JPA
- PostgreSQL

**Frontend:**
- React 19
- Vite 7
- TailwindCSS
- Radix UI
- React Router DOM

---

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.
