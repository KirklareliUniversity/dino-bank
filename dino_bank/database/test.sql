-- DinoBank Başlangıç Verileri

-- Test müşterisi
INSERT INTO customers (tc_kimlik_no, ad, soyad, email, telefon, dogum_tarihi, adres, sifre, kayit_tarihi, aktif)
VALUES ('12345678901', 'Test', 'Müşteri', 'test@dinobank.com', '5551234567', '1990-01-01', 
        'Test Adresi, İstanbul', 'test123', CURRENT_DATE, true);

-- Test hesabı
INSERT INTO accounts (customer_id, account_number, account_type, balance, currency, opening_date, active)
VALUES (1, 'DB1001', 'CHECKING', 1000.00, 'TRY', CURRENT_TIMESTAMP, true);

