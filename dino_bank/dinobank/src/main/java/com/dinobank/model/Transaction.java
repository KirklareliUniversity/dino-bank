package com.dinobank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * =============================================================================
 * TRANSACTION (FİNANSAL İŞLEM) MODEL SINIFI
 * =============================================================================
 * 
 * Bu sınıf, DinoBank sistemindeki tüm finansal işlemleri temsil eder.
 * Para transferleri, ödemeler, yatırımlar ve ATM işlemleri bu tabloda kayıt
 * altına alınır.
 * 
 * VERİTABANI TABLOSU: public.transactions
 * 
 * İŞLEM AKIŞI:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 1. İşlem oluşturulur → status = "PENDING" │
 * │ 2. Bakiye kontrolü yapılır (yeterli bakiye var mı?) │
 * │ 3a. BAŞARILI → fromAccount'tan düş, toAccount'a ekle │
 * │ status = "COMPLETED" │
 * │ 3b. BAŞARISIZ → status = "FAILED", işlem iptal │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * İLİŞKİLER:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Account (fromAccount) ---> Transaction : Parayı gönderen hesap │
 * │ Account (toAccount) ---> Transaction : Parayı alan hesap │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * İŞLEM TİPLERİ (transactionType):
 * - "TRANSFER" : Hesaplar arası para transferi
 * - "DEPOSIT" : Hesaba para yatırma (ATM, şube)
 * - "WITHDRAW" : Hesaptan para çekme (ATM)
 * - "PAYMENT" : Fatura/hizmet ödemesi
 * - "CREDIT" : Kredi ödemesi veya kredi yatırımı
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see Account
 */
@Entity // JPA varlık sınıfı
@Table(name = "transactions", schema = "public")
public class Transaction {

    /**
     * İŞLEM ID - Birincil Anahtar
     * 
     * Her finansal işlemin benzersiz referans numarası.
     * Dekont ve takip işlemleri için kullanılır.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    /**
     * GÖNDERİCİ HESAP - Paranın Çıktığı Hesap
     * 
     * @ManyToOne: Bir hesaptan birden fazla işlem yapılabilir
     *             nullable = false: Her işlemin bir kaynağı olmalı!
     * 
     *             DEPOSIT işlemlerinde bu alan banka kasasını temsil edebilir.
     */
    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    /**
     * ALICI HESAP - Paranın Gittiği Hesap
     * 
     * WITHDRAW işlemlerinde null olabilir (para sistemi terk ediyor)
     * TRANSFER işlemlerinde zorunlu (paranın gideceği yer)
     * 
     * Aynı müşterinin kendi hesapları arasında da transfer yapılabilir.
     */
    @ManyToOne
    @JoinColumn(name = "to_account_id") // nullable = true (varsayılan)
    private Account toAccount;

    /**
     * İŞLEM TİPİ
     * 
     * Yapılan işlemin kategorisini belirler:
     * ┌─────────────────────────────────────────────────────────────────┐
     * │ "TRANSFER" → Hesaplar arası para gönderimi │
     * │ "DEPOSIT" → Hesaba para yatırma │
     * │ "WITHDRAW" → Hesaptan para çekme │
     * │ "PAYMENT" → Fatura veya hizmet ödemesi │
     * │ "CREDIT" → Kredi ile ilgili işlem │
     * └─────────────────────────────────────────────────────────────────┘
     */
    @Column(name = "transaction_type", length = 20, nullable = false)
    private String transactionType;

    /**
     * İŞLEM TUTARI
     * 
     * Transfer edilen/çekilen/yatırılan para miktarı.
     * 
     * ⚠️ ÖNEMLİ: Her zaman pozitif değer olmalı!
     * İşlemin yönü (giriş/çıkış) transactionType'a göre belirlenir.
     * 
     * BigDecimal kullanımı: Finansal hassasiyet için zorunlu!
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /**
     * PARA BİRİMİ (ISO 4217 Kodu)
     * 
     * İşlemin hangi para biriminde yapıldığı:
     * - TRY: Türk Lirası (varsayılan)
     * - USD: Amerikan Doları
     * - EUR: Euro
     * 
     * Farklı para birimleri arası transferlerde kur uygulanır.
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "TRY";

    /**
     * İŞLEM AÇIKLAMASI
     * 
     * Kullanıcı tarafından girilen veya sistem tarafından oluşturulan not.
     * 
     * Örnekler:
     * - "Ev kirasi Aralik 2024"
     * - "Kredi ödemesi - Taksit 3/12"
     * - "ATM - Kadıköy Şubesi"
     */
    @Column(name = "description")
    private String description;

    /**
     * İŞLEM TARİHİ VE SAATİ
     * 
     * İşlemin gerçekleştirildiği an.
     * Hesap hareketleri ve hesap özeti için kritik önem taşır.
     * 
     * Otomatik olarak işlem anının zamanı atanır.
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    /**
     * İŞLEM DURUMU (Status)
     * 
     * İşlemin mevcut durumunu gösterir:
     * ┌─────────────────────────────────────────────────────────────────┐
     * │ "PENDING" → İşleniyor, henüz tamamlanmadı │
     * │ "COMPLETED" → Başarıyla tamamlandı │
     * │ "FAILED" → Başarısız (yetersiz bakiye, hata vb.) │
     * │ "CANCELLED" → Kullanıcı veya sistem tarafından iptal edildi │
     * └─────────────────────────────────────────────────────────────────┘
     * 
     * Varsayılan: "PENDING"
     */
    @Column(name = "status", length = 20, nullable = false)
    private String status = "PENDING";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
