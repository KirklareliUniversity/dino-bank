package com.dinobank.CreditApplication.service;

import com.dinobank.CreditApplication.dto.CreditEvaluationDto;
import com.dinobank.CreditApplication.dto.CreditRequestDto;
import java.math.BigDecimal;
import com.dinobank.model.Account;
import com.dinobank.model.CreditApplication;
import com.dinobank.model.Customer;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CreditApplicationRepository;
import com.dinobank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * =============================================================================
 * CREDIT SERVICE - Kredi İşlemleri Servis Katmanı
 * =============================================================================
 * 
 * Bu sınıf, DinoBank'ın kredi başvuru süreçlerini yöneten ana iş mantığı
 * (business logic) katmanıdır. Controller'dan gelen istekleri işler ve
 * veritabanı işlemlerini Repository katmanı aracılığıyla gerçekleştirir.
 * 
 * MİMARİ KATMAN: Service Layer (İş Mantığı)
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Controller → SERVICE → Repository → Database │
 * │ (HTTP) (Logic) (Data Access) (PostgreSQL) │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * KREDİ DEĞERLENDİRME KURALI:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Toplam Bakiye × 4 >= Talep Edilen Tutar → ONAYLA │
 * │ Toplam Bakiye × 4 < Talep Edilen Tutar → REDDET │
 * │ │
 * │ Örnek: 50.000 TL bakiye → Max 200.000 TL kredi alabilir │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see CreditApplication
 * @see CreditApplicationRepository
 */
@Service // Spring'e bu sınıfın bir Service bean'i olduğunu bildirir
public class CreditService {

    // =========================================================================
    // BAĞIMLILIKLAR (Dependencies) - Constructor Injection ile enjekte edilir
    // =========================================================================

    /** Kredi başvurularına erişim için repository */
    private final CreditApplicationRepository creditRepository;

    /** Müşteri bilgilerine erişim için repository */
    private final CustomerRepository customerRepository;

    /** Hesap bilgilerine erişim için repository */
    private final AccountRepository accountRepository;

    /**
     * CONSTRUCTOR - Bağımlılık Enjeksiyonu (Dependency Injection)
     * 
     * Spring, bu constructor'ı otomatik olarak çağırır ve gerekli
     * repository bean'lerini enjekte eder.
     * 
     * NOT: @Autowired anotasyonu tek constructor'da opsiyoneldir.
     * Spring 4.3+ sürümünden itibaren otomatik olarak algılanır.
     * 
     * @param creditRepository   Kredi başvuru veritabanı işlemleri
     * @param customerRepository Müşteri veritabanı işlemleri
     * @param accountRepository  Hesap veritabanı işlemleri
     */
    public CreditService(CreditApplicationRepository creditRepository,
            CustomerRepository customerRepository,
            AccountRepository accountRepository) {
        this.creditRepository = creditRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * =========================================================================
     * KREDİ BAŞVURUSU YAP - Ana Başvuru İşleme Metodu
     * =========================================================================
     * 
     * Bu metod, müşterinin kredi başvurusunu alır ve anında değerlendirir.
     * Bakiye kontrolü yaparak otomatik onay/red kararı verir.
     * 
     * İŞLEM AKIŞI:
     * ┌─────────────────────────────────────────────────────────────────┐
     * │ 1. Müşteri bilgilerini veritabanından çek │
     * │ 2. Müşterinin tüm hesaplarının toplam bakiyesini hesapla │
     * │ 3. Kredi limitini hesapla (Toplam Bakiye × 4) │
     * │ 4. Limit yeterliyse: ONAYLA ve parayı ilk hesaba yatır │
     * │ 5. Limit yetersizse: REDDET ve sebebini kaydet │
     * │ 6. Başvuruyu veritabanına kaydet ve sonucu döndür │
     * └─────────────────────────────────────────────────────────────────┘
     * 
     * @Transactional: Tüm işlemler tek bir veritabanı transaction'ında
     *                 yapılır. Hata olursa tüm değişiklikler geri alınır
     *                 (rollback).
     * 
     * @param request Müşterinin kredi başvuru bilgileri (tutar, vade, amaç)
     * @return Kaydedilmiş kredi başvurusu (onay/red durumu ile birlikte)
     * @throws RuntimeException Müşteri bulunamazsa
     */
    @Transactional // Atomik işlem: Ya hepsi başarılı olur, ya hiçbiri
    public CreditApplication applyForCredit(CreditRequestDto request) {

        // ADIM 1: Müşteri bilgilerini veritabanından çek
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

        // ADIM 2: Müşterinin toplam bakiyesini hesapla
        // Tüm hesapları getir ve bakiyelerini topla
        List<Account> accounts = accountRepository.findByCustomer(customer);
        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance) // Her hesabın bakiyesini al
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Topla

        // ADIM 3: Yeni başvuru kaydı oluştur
        CreditApplication application = new CreditApplication();
        application.setCustomer(customer);
        application.setRequestedAmount(request.getRequestedAmount());
        application.setInstallmentCount(request.getInstallmentCount());
        application.setPurpose(request.getPurpose());
        application.setApplicationDate(LocalDate.now());

        // ADIM 4: Kredi limitini hesapla ve karar ver
        // KURAL: Toplam Bakiye × 4 = Maksimum Kredi Limiti
        BigDecimal limit = totalBalance.multiply(new BigDecimal("4"));

        // Limit yeterli mi? (limit >= talep edilen tutar)
        if (limit.compareTo(request.getRequestedAmount()) >= 0) {
            // ✅ ONAYLANDI - Kredi verilebilir
            application.setStatus("APPROVED");
            application.setEvaluationDate(LocalDateTime.now());

            // Kredi tutarını müşterinin ilk hesabına yatır
            if (!accounts.isEmpty()) {
                Account targetAccount = accounts.get(0); // İlk hesabı kullan
                targetAccount.setBalance(
                        targetAccount.getBalance().add(request.getRequestedAmount()));
                accountRepository.save(targetAccount); // Güncel bakiyeyi kaydet
            }
            // NOT: Hesap yoksa para yatırılamaz, ama bakiye 0 ise zaten limit de 0'dır
        } else {
            // ❌ REDDEDİLDİ - Yetersiz bakiye
            application.setStatus("REJECTED");
            application.setRejectionReason(
                    "Yetersiz Bakiye (Maksimum Kredi Limiti: " + limit + " TL)");
            application.setEvaluationDate(LocalDateTime.now());
        }

        // ADIM 5: Başvuruyu veritabanına kaydet ve döndür
        return creditRepository.save(application);
    }

    /**
     * =========================================================================
     * BAŞVURU DEĞERLENDİR - Admin Manuel Onay/Red Metodu
     * =========================================================================
     * 
     * Bu metod, beklemedeki (PENDING) kredi başvurularını manuel olarak
     * değerlendirmek için kullanılır. Admin panelden çağrılır.
     * 
     * KULLANIM SENARYOSU:
     * - Otomatik karar verilemeyen özel durumlar
     * - Büyük tutarlı kredilerde insan onayı gerekliliği
     * - Sistem tarafından bekletilen başvuruların kontrolü
     * 
     * KONTROLLER:
     * - Başvuru var mı? (yoksa hata fırlat)
     * - Başvuru hala PENDING durumunda mı? (değilse hata fırlat)
     * 
     * @param evaluation Admin'in verdiği karar (APPROVED/REJECTED ve sebep)
     * @return Güncellenmiş kredi başvurusu
     * @throws RuntimeException Başvuru bulunamazsa veya zaten sonuçlanmışsa
     */
    @Transactional
    public CreditApplication evaluateApplication(CreditEvaluationDto evaluation) {
        // Başvuruyu ID'ye göre bul
        CreditApplication application = creditRepository.findById(evaluation.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Başvuru bulunamadı!"));

        // Güvenlik kontrolü: Sadece bekleyen başvurular değerlendirilebilir
        if (!"PENDING".equals(application.getStatus())) {
            throw new RuntimeException("Bu başvuru zaten sonuçlanmış!");
        }

        // Admin kararını uygula
        application.setStatus(evaluation.getResult()); // APPROVED veya REJECTED
        application.setRejectionReason(evaluation.getReason()); // Red sebebi (opsiyonel)
        application.setEvaluationDate(LocalDateTime.now()); // Değerlendirme zamanı

        // Eğer onaylandıysa, parayı müşterinin hesabına aktar
        if ("APPROVED".equals(evaluation.getResult())) {
            List<Account> accounts = accountRepository.findByCustomer(application.getCustomer());
            if (accounts.isEmpty()) {
                throw new RuntimeException("Müşterinin parayı yatıracak bir hesabı yok!");
            }

            // İlk hesaba kredi tutarını ekle
            Account targetAccount = accounts.get(0);
            targetAccount.setBalance(targetAccount.getBalance().add(application.getRequestedAmount()));
            accountRepository.save(targetAccount);
        }

        // Güncellenmiş başvuruyu kaydet ve döndür
        return creditRepository.save(application);
    }

    /**
     * =========================================================================
     * BEKLEYENLERİ GETİR - Admin Panel İçin Bekleyen Başvurular
     * =========================================================================
     * 
     * Admin panelinde görüntülenmek üzere "PENDING" durumundaki
     * tüm başvuruları listeler.
     * 
     * KULLANIM: Admin dashboard, bekleyen işler kutusu
     * 
     * @return Değerlendirme bekleyen kredi başvurularının listesi
     */
    public List<CreditApplication> getPendingApplications() {
        return creditRepository.findByStatus("PENDING");
    }

    /**
     * =========================================================================
     * GEÇMİŞ BAŞVURULARI GETİR - Müşteri Bazlı Kredi Geçmişi
     * =========================================================================
     * 
     * Belirli bir müşterinin tüm kredi başvurularını listeler.
     * Müşteri dashboard'unda "Geçmiş Başvurularım" bölümü için kullanılır.
     * 
     * İÇERİK: Onaylanan, reddedilen ve bekleyen tüm başvurular döner.
     * 
     * @param customerId Geçmişi görüntülenecek müşterinin ID'si
     * @return Müşterinin tüm kredi başvurularının listesi
     */
    public List<CreditApplication> getHistory(Long customerId) {
        return creditRepository.findByCustomerId(customerId);
    }
}