package com.dinobank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * =============================================================================
 * CREDIT APPLICATION (KREDİ BAŞVURUSU) MODEL SINIFI
 * =============================================================================
 * 
 * Bu sınıf, DinoBank sistemindeki kredi başvurularını temsil eder.
 * Müşterilerin yaptığı kredi talepleri bu tabloda saklanır ve
 * onay/red süreçleri burada yönetilir.
 * 
 * VERİTABANI TABLOSU: public.credit_applications
 * 
 * KREDİ BAŞVURU AKIŞI:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 1. Müşteri başvuru yapar → status = "PENDING" │
 * │ 2. Sistem bakiyeyi kontrol eder (Bakiye x 4 >= Talep?) │
 * │ 3a. BAŞARILI → status = "APPROVED", para hesaba eklenir │
 * │ 3b. BAŞARISIZ → status = "REJECTED", sebep kaydedilir │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * İLİŞKİLER:
 * - Customer (1) -----> CreditApplication (N) : Bir müşteri N başvuru yapabilir
 * 
 * KREDİ ONAY KURALI (CreditService tarafından uygulanır):
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Müşterinin Toplam Bakiyesi x 4 >= Talep Edilen Tutar │
 * │ │
 * │ Örnek: 25.000 TL bakiye → Maksimum 100.000 TL kredi alabilir │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see Customer
 * @see CreditService
 */
@Entity // JPA varlık sınıfı
@Table(name = "credit_applications", schema = "public")
public class CreditApplication {

    /**
     * BAŞVURU ID - Birincil Anahtar
     * 
     * Her kredi başvurusunun benzersiz tanımlayıcısı.
     * Takip ve referans için kullanılır.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_application_id")
    private Long id;

    /**
     * BAŞVURU SAHİBİ - Krediyi Talep Eden Müşteri
     * 
     * @ManyToOne: Birçok başvuru aynı müşteriye ait olabilir
     *             Müşteri bilgilerine erişim için kullanılır (isim, bakiye vb.)
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * TALEP EDİLEN KREDİ TUTARI
     * 
     * Müşterinin almak istediği kredi miktarı (TL cinsinden).
     * BigDecimal kullanımı: Para hesaplamalarında hassasiyet için şart!
     * 
     * Örnek: 50000.00 (Elli bin TL)
     */
    @Column(name = "requested_amount", nullable = false)
    private BigDecimal requestedAmount;

    /**
     * TAKSİT SAYISI (Vade)
     * 
     * Kredinin kaç ayda geri ödeneceği.
     * Desteklenen değerler: 12, 24, 36, 48 ay
     * 
     * Bu değere göre aylık taksit hesaplanır:
     * Aylık Taksit = (Talep Tutarı + Faiz) / Taksit Sayısı
     */
    @Column(name = "installment_count", nullable = false)
    private Integer installmentCount;

    /**
     * KREDİ KULLANIM AMACI
     * 
     * Müşterinin krediyi ne için kullanacağı.
     * İsteğe bağlı alan - Raporlama ve analiz için faydalı.
     * 
     * Örnek değerler: "Tatil", "Eğitim", "Ev Tadilat", "Araç"
     */
    @Column(name = "purpose")
    private String purpose;

    /**
     * BAŞVURU TARİHİ
     * 
     * Başvurunun sisteme girildiği tarih.
     * Otomatik olarak başvuru anının tarihi atanır.
     */
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate = LocalDate.now();

    /**
     * BAŞVURU DURUMU (Status)
     * 
     * Başvurunun mevcut durumunu gösterir:
     * ┌─────────────────────────────────────────────────────────────────┐
     * │ "PENDING" → Beklemede, henüz değerlendirilmedi │
     * │ "APPROVED" → Onaylandı, kredi tutarı hesaba yatırıldı │
     * │ "REJECTED" → Reddedildi, rejectionReason alanına bakınız │
     * └─────────────────────────────────────────────────────────────────┘
     * 
     * Varsayılan: "PENDING" (Yeni başvurular beklemede başlar)
     */
    @Column(name = "status", length = 20, nullable = false)
    private String status = "PENDING";

    /**
     * RED SEBEBİ
     * 
     * Başvuru reddedildiyse (status = "REJECTED") nedeni burada saklanır.
     * Onaylanan başvurularda bu alan null kalır.
     * 
     * Örnek: "Yetersiz Bakiye (Maksimum Kredi Limiti: 80000 TL)"
     */
    @Column(name = "rejection_reason")
    private String rejectionReason;

    /**
     * DEĞERLENDİRME TARİHİ VE SAATİ
     * 
     * Başvurunun onaylandığı veya reddedildiği an.
     * PENDING durumundaki başvurularda null olur.
     * 
     * Bu alan sayesinde işlem süresi hesaplanabilir:
     * İşlem Süresi = evaluationDate - applicationDate
     */
    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

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

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public void setInstallmentCount(Integer installmentCount) {
        this.installmentCount = installmentCount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
