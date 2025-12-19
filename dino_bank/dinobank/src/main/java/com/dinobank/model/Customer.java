package com.dinobank.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

/**
 * =============================================================================
 * CUSTOMER (MÜŞTERİ) MODEL SINIFI - Banka Müşterisi Varlık Tanımı
 * =============================================================================
 * 
 * Bu sınıf, DinoBank sistemindeki kayıtlı müşterileri temsil eder.
 * Türk bankacılık sistemine uygun olarak T.C. Kimlik numarası zorunludur.
 * 
 * VERİTABANI TABLOSU: public.customers
 * 
 * İLİŞKİLER:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Customer (1) <----> Account (N) : Müşterinin hesapları │
 * │ Customer (1) <----> CreditApplication (N) : Kredi başvuruları │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * GÜVENLİK NOTLARI:
 * - Şifre alanı BCrypt ile hashlenerek saklanmalıdır
 * - T.C. Kimlik numarası benzersiz olmalıdır
 * - JsonIgnore ile hassas veriler API'den gizlenir
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see Account
 * @see CreditApplication
 */
@Entity // JPA varlık sınıfı olarak işaretler
@Table(name = "customers", schema = "public") // Veritabanı tablo eşlemesi
public class Customer {

    /**
     * MÜŞTERİ ID - Birincil Anahtar (Primary Key)
     * 
     * Sistemdeki benzersiz müşteri tanımlayıcısı.
     * Veritabanı tarafından otomatik olarak üretilir.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    /**
     * T.C. KİMLİK NUMARASI
     * 
     * Türkiye Cumhuriyeti vatandaşlık numarası - 11 haneli
     * Bankacılık işlemleri için yasal zorunluluktur.
     * 
     * DOĞRULAMA KURALLARI:
     * - Tam olarak 11 rakam olmalı
     * - İlk rakam 0 olamaz
     * - Algoritma doğrulaması yapılmalı (Luhn benzeri)
     * 
     * unique = true: Aynı T.C. ile iki hesap açılamaz!
     */
    @Column(name = "tc_kimlik_no", length = 11, nullable = false, unique = true)
    private String tcKimlikNo;

    /**
     * MÜŞTERİ ADI
     * 
     * Müşterinin adı (ilk isim). Türkçe karakterler desteklenir.
     */
    @Column(name = "ad", nullable = false, length = 50)
    private String ad;

    /**
     * MÜŞTERİ SOYADI
     * 
     * Müşterinin soyadı. Türkçe karakterler desteklenir.
     */
    @Column(name = "soyad", nullable = false, length = 50)
    private String soyad;

    /**
     * E-POSTA ADRESİ
     * 
     * Giriş yapmak ve bildirim almak için kullanılır.
     * Benzersiz olmalıdır - Her e-posta sadece bir hesaba bağlanabilir.
     * 
     * Format: kullanici@domain.com
     */
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    /**
     * TELEFON NUMARASI
     * 
     * İsteğe bağlı alan - SMS doğrulama ve iletişim için.
     * Önerilen format: +905XXXXXXXXX
     */
    @Column(name = "telefon", length = 15)
    private String telefon;

    /**
     * DOĞUM TARİHİ
     * 
     * Yaş doğrulaması için kullanılır.
     * Bankacılık işlemleri için 18 yaş ve üzeri olmalıdır.
     */
    @Column(name = "dogum_tarihi", nullable = false)
    private LocalDate dogumTarihi;

    /**
     * ADRES BİLGİSİ
     * 
     * Müşterinin ikamet adresi. Fatura ve resmi yazışmalar için.
     * İsteğe bağlı alan.
     */
    @Column(name = "adres")
    private String adres;

    /**
     * ŞİFRE (HASHLENMIŞ)
     * 
     * ⚠️ GÜVENLİK UYARISI: Düz metin (plain text) olarak ASLA saklanmamalı!
     * BCrypt algoritması ile hashlenmelidir.
     * 
     * Örnek hash: $2a$10$N9qo8uLOickgx2ZMRZoMy...
     */
    @Column(name = "sifre", nullable = false, length = 255)
    private String sifre;

    /**
     * KAYIT TARİHİ
     * 
     * Müşterinin sisteme ne zaman kaydolduğu.
     * Otomatik olarak kaydolma anının tarihi atanır.
     */
    @Column(name = "kayit_tarihi", nullable = false)
    private LocalDate kayitTarihi = LocalDate.now();

    /**
     * HESAP AKTİFLİK DURUMU
     * 
     * true: Müşteri aktif, giriş yapabilir ve işlem yapabilir
     * false: Hesap dondurulmuş, askıya alınmış veya kapatılmış
     * 
     * Soft-delete: Müşteriler silinmez, sadece pasife alınır.
     */
    @Column(name = "aktif", nullable = false)
    private Boolean aktifMi = true;

    /**
     * MÜŞTERİNİN BANKA HESAPLARI
     * 
     * @OneToMany: Bir müşterinin birden fazla hesabı olabilir (1:N ilişki)
     * 
     *             mappedBy = "customer": Account sınıfındaki 'customer' alanı ile
     *             bağlantı
     *             cascade = ALL: Müşteri silinirse hesapları da silinir
     *             fetch = LAZY: Hesaplar sadece istendiğinde yüklenir (performans)
     * 
     * @JsonIgnore: REST API yanıtlarında sonsuz döngüyü önler
     *              (Customer -> Accounts -> Customer -> Accounts -> ...)
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // API'de bu alan gösterilmez (sonsuz döngü önlemi)
    private List<Account> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public LocalDate getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(LocalDate kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }

    public Boolean getAktif() {
        return aktifMi;
    }

    public void setAktif(Boolean aktif) {
        this.aktifMi = aktif;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
