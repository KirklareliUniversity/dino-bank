package com.dinobank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * =============================================================================
 * ACCOUNT (HESAP) MODEL SINIFI - Banka Hesabı Varlık Tanımı
 * =============================================================================
 * 
 * Bu sınıf, DinoBank sistemindeki müşteri banka hesaplarını temsil eder.
 * Her müşterinin birden fazla hesabı olabilir (vadesiz, vadeli, döviz vb.)
 * 
 * VERİTABANI TABLOSU: public.accounts
 * 
 * İLİŞKİLER:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Customer (1) -----> Account (N) : Bir müşterinin N hesabı │
 * │ Account (1) -----> Transaction (N) : Bir hesabın N işlemi │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * HESAP TÜRLERİ (accountType):
 * - "VADESIZ" : Günlük işlemler için kullanılan hesap
 * - "VADELI" : Belirli vadeye bağlı tasarruf hesabı
 * - "DOVIZ" : Döviz cinsinden tutulan hesap
 * - "YATIRIM" : Yatırım amaçlı özel hesap
 * 
 * @author DinoBank Development Team (Osman , İlkmert , Gemini)
 * @version 1.0
 * @see Customer
 * @see Transaction
 */
@Entity // Bu sınıfın bir JPA varlığı olduğunu belirtir
@Table(name = "accounts", schema = "public") // Veritabanı tablo adı ve şeması
public class Account {

    /**
     * HESAP ID - Birincil Anahtar (Primary Key)
     * 
     * PostgreSQL'de otomatik artan (SERIAL) bir değerdir.
     * JPA tarafından otomatik olarak yönetilir, manuel atama yapılmamalı.
     */
    @Id // Bu alanın birincil anahtar olduğunu belirtir
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Veritabanı tarafından otomatik üretilir
    @Column(name = "account_id") // Veritabanındaki kolon adı
    private Long id;

    /**
     * MÜŞTERİ İLİŞKİSİ - Hesabın Sahibi
     * 
     * @ManyToOne: Birçok hesap, bir müşteriye ait olabilir
     * @JoinColumn: customers tablosundaki customer_id ile bağlantı kurar
     * 
     *              nullable = false: Her hesabın mutlaka bir sahibi olmalı!
     */
    @ManyToOne // N:1 ilişki - Birden fazla hesap aynı müşteriye ait olabilir
    @JoinColumn(name = "customer_id", nullable = false) // Yabancı anahtar (Foreign Key)
    private Customer customer;

    /**
     * HESAP NUMARASI (IBAN benzeri)
     * 
     * Benzersiz (unique) olmalıdır - Aynı numarada iki hesap olamaz!
     * Örnek format: "TR1234567890123456"
     * 
     * length = 20: Maksimum 20 karakter
     */
    @Column(name = "account_number", length = 20, nullable = false, unique = true)
    private String accountNumber;

    /**
     * HESAP TİPİ
     * 
     * Hesabın türünü belirler: VADESIZ, VADELI, DOVIZ, YATIRIM
     * Bu değere göre faiz oranları ve işlem kuralları değişebilir.
     */
    @Column(name = "account_type", length = 20, nullable = false)
    private String accountType;

    /**
     * BAKİYE - Hesaptaki Mevcut Para Tutarı
     * 
     * BigDecimal kullanımı ÖNEMLİ: Para hesaplamalarında double/float
     * kullanmak hassasiyet kaybına yol açar! (0.1 + 0.2 != 0.3 sorunu)
     * 
     * Varsayılan değer: 0.00 TL
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; // Para için HER ZAMAN BigDecimal!

    /**
     * PARA BİRİMİ (ISO 4217 Kodu)
     * 
     * 3 harfli uluslararası para birimi kodu:
     * - TRY: Türk Lirası
     * - USD: Amerikan Doları
     * - EUR: Euro
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "TRY"; // Varsayılan: Türk Lirası

    /**
     * HESAP AÇILIŞ TARİHİ
     * 
     * Hesabın ne zaman oluşturulduğunu kaydeder.
     * LocalDateTime.now() ile otomatik olarak şimdiki zaman atanır.
     */
    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate = LocalDateTime.now();

    /**
     * HESAP AKTİFLİK DURUMU
     * 
     * true: Hesap aktif ve işlem yapılabilir
     * false: Hesap dondurulmuş veya kapatılmış
     * 
     * Soft-delete mantığı: Hesaplar silinmez, sadece pasife alınır.
     */
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
