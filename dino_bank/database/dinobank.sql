-- DinoBank Setup Script

-- Scripti çalıştırmak için: PostgreSql içinde dinobank adlı veritabanı oluştur. 
-- dinobank üstüne sağ tıkla ve query tool'u seç. Daha sonrasında bu dosyayı aç ve çalıştır 

-- 1️⃣ Veritabanı oluştur
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'dinobank') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE dinobank');
   END IF;
END
$$;

-- 2 -- Veritabanına bağlan
\c dinobank;
 
-- 3️⃣ Customers tablosu ve sequence
CREATE SEQUENCE IF NOT EXISTS public.customers_customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.customers (
    customer_id BIGINT NOT NULL DEFAULT nextval('public.customers_customer_id_seq'::regclass) PRIMARY KEY,
    tc_kimlik_no VARCHAR(11) UNIQUE NOT NULL,
    ad VARCHAR(50) NOT NULL,
    soyad VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefon VARCHAR(15),
    dogum_tarihi DATE NOT NULL,
    adres TEXT,
    sifre VARCHAR(255) NOT NULL,
    kayit_tarihi DATE NOT NULL DEFAULT CURRENT_DATE,
    aktif BOOLEAN NOT NULL DEFAULT true
);

-- 4️⃣ Accounts tablosu ve sequence
CREATE SEQUENCE IF NOT EXISTS public.accounts_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.accounts (
    account_id BIGINT NOT NULL DEFAULT nextval('public.accounts_account_id_seq'::regclass) PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES public.customers(customer_id),
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance NUMERIC(15,2) NOT NULL DEFAULT 0.00,
    currency VARCHAR(3) NOT NULL DEFAULT 'TRY',
    opening_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT true
);

-- 5️⃣ Transactions tablosu ve sequence
CREATE SEQUENCE IF NOT EXISTS public.transactions_transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.transactions (
    transaction_id BIGINT NOT NULL DEFAULT nextval('public.transactions_transaction_id_seq'::regclass) PRIMARY KEY,
    from_account_id BIGINT NOT NULL REFERENCES public.accounts(account_id),
    to_account_id BIGINT REFERENCES public.accounts(account_id),
    transaction_type VARCHAR(20) NOT NULL,
    amount NUMERIC(15,2) NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'TRY',
    description TEXT,
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
);

-- 6️⃣ Credit Applications tablosu ve sequence
CREATE SEQUENCE IF NOT EXISTS public.credit_applications_credit_application_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.credit_applications (
    credit_application_id BIGINT NOT NULL DEFAULT nextval('public.credit_applications_credit_application_id_seq'::regclass) PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES public.customers(customer_id),
    requested_amount NUMERIC(15,2) NOT NULL,
    installment_count INTEGER NOT NULL,
    purpose TEXT,
    application_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    rejection_reason TEXT,
    evaluation_date TIMESTAMP
);

-- 7️⃣ Indexler (varsa tekrar oluşturulmaz)
CREATE INDEX IF NOT EXISTS idx_customers_tc_kimlik_no ON public.customers(tc_kimlik_no);
CREATE INDEX IF NOT EXISTS idx_customers_email ON public.customers(email);
CREATE INDEX IF NOT EXISTS idx_accounts_account_number ON public.accounts(account_number);
CREATE INDEX IF NOT EXISTS idx_accounts_customer_id ON public.accounts(customer_id);
CREATE INDEX IF NOT EXISTS idx_transactions_from_account ON public.transactions(from_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_to_account ON public.transactions(to_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_date ON public.transactions(transaction_date);
CREATE INDEX IF NOT EXISTS idx_credit_applications_customer ON public.credit_applications(customer_id);
CREATE INDEX IF NOT EXISTS idx_credit_applications_status ON public.credit_applications(status);
