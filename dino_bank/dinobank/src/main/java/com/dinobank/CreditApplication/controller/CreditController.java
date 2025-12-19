package com.dinobank.CreditApplication.controller;

import com.dinobank.CreditApplication.dto.CreditEvaluationDto;
import com.dinobank.CreditApplication.dto.CreditRequestDto;
import com.dinobank.CreditApplication.service.CreditService;
import com.dinobank.model.CreditApplication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =============================================================================
 * CREDIT CONTROLLER - Kredi İşlemleri REST API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, kredi başvuru işlemleri için HTTP endpoint'leri sağlar.
 * Frontend (React) uygulaması bu endpoint'lere fetch/axios ile bağlanır.
 * 
 * BASE URL: /api/credits
 * 
 * API ENDPOINT HARİTASI:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼─────────────────────────┼─────────────────────────────────────────┤
 * │ POST │ /api/credits/apply │ Yeni kredi başvurusu yap │
 * │ POST │ /api/credits/evaluate │ Bekleyen başvuruyu değerlendir (admin) │
 * │ GET │ /api/credits/pending │ Bekleyen başvuruları listele (admin) │
 * │ GET │ /api/credits/history/{id} │ Müşteri kredi geçmişini getir │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * FRONTEND KULLANIMI (React - CreditPage.jsx):
 * ```javascript
 * // Kredi başvurusu
 * fetch('/api/credits/apply', {
 * method: 'POST',
 * body: JSON.stringify({ customerId, requestedAmount, installmentCount, purpose
 * })
 * });
 * 
 * // Geçmiş başvuruları getir
 * fetch(`/api/credits/history/${userId}`);
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see CreditService İş mantığı katmanı
 * @see CreditApplication Veri modeli
 */
@RestController // Bu sınıfın bir REST API kontrolcüsü olduğunu belirtir
@RequestMapping("/api/credits") // Tüm endpoint'ler bu base path'ten başlar
public class CreditController {

    /** Kredi iş mantığı servisi - Dependency Injection ile enjekte edilir */
    private final CreditService creditService;

    /**
     * Constructor - CreditService bağımlılığını enjekte eder
     * Spring otomatik olarak CreditService bean'ini bulur ve buraya yerleştirir
     */
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    /**
     * =========================================================================
     * KREDİ BAŞVURUSU ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/credits/apply
     * 
     * AÇIKLAMA:
     * Müşterinin yeni kredi başvurusu yapmasını sağlar.
     * Bakiye kontrolü otomatik yapılır ve anında onay/red kararı verilir.
     * 
     * REQUEST BODY (JSON):
     * {
     * "customerId": 1,
     * "requestedAmount": 50000.00,
     * "installmentCount": 12,
     * "purpose": "Tatil"
     * }
     * 
     * RESPONSE (200 OK):
     * - Onaylanırsa: status = "APPROVED", para hesaba yatırılır
     * - Reddedilirse: status = "REJECTED", rejectionReason dolu döner
     * 
     * @param request Kredi başvuru bilgileri (DTO)
     * @return CreditApplication - Oluşturulan/güncellenen başvuru kaydı
     */
    @PostMapping("/apply")
    public ResponseEntity<CreditApplication> apply(@RequestBody CreditRequestDto request) {
        return ResponseEntity.ok(creditService.applyForCredit(request));
    }

    /**
     * =========================================================================
     * BAŞVURU DEĞERLENDİRME ENDPOINT'İ (ADMIN)
     * =========================================================================
     * 
     * URL: POST /api/credits/evaluate
     * 
     * AÇIKLAMA:
     * Admin panelinden beklemedeki başvuruları manuel olarak değerlendirmek için.
     * Sadece PENDING durumundaki başvurular değerlendirilebilir.
     * 
     * REQUEST BODY (JSON):
     * {
     * "applicationId": 5,
     * "result": "APPROVED", // veya "REJECTED"
     * "reason": "Manuel onay" // Opsiyonel
     * }
     * 
     * @param evaluation Admin'in verdiği karar
     * @return CreditApplication - Güncellenmiş başvuru kaydı
     */
    @PostMapping("/evaluate")
    public ResponseEntity<CreditApplication> evaluate(@RequestBody CreditEvaluationDto evaluation) {
        return ResponseEntity.ok(creditService.evaluateApplication(evaluation));
    }

    /**
     * =========================================================================
     * BEKLEYEN BAŞVURULAR ENDPOINT'İ (ADMIN)
     * =========================================================================
     * 
     * URL: GET /api/credits/pending
     * 
     * AÇIKLAMA:
     * Admin panelinde görüntülenmek üzere PENDING durumundaki
     * tüm başvuruları listeler. Sayfalama yoktur.
     * 
     * RESPONSE (200 OK): CreditApplication[] dizisi
     * 
     * @return Bekleyen başvuruların listesi
     */
    @GetMapping("/pending")
    public ResponseEntity<List<CreditApplication>> getPending() {
        return ResponseEntity.ok(creditService.getPendingApplications());
    }

    /**
     * =========================================================================
     * MÜŞTERİ KREDİ GEÇMİŞİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: GET /api/credits/history/{customerId}
     * 
     * AÇIKLAMA:
     * Belirli bir müşterinin tüm kredi başvurularını (onaylı, reddedilmiş,
     * beklemede) getirir. CreditPage.jsx'te "Geçmiş Başvurularım" tablosunu
     * doldurmak için kullanılır.
     * 
     * PATH VARIABLE:
     * - customerId: Müşterinin benzersiz ID'si
     * 
     * ÖRNEK: GET /api/credits/history/1
     * 
     * RESPONSE (200 OK): CreditApplication[] dizisi
     * 
     * @param customerId URL'den alınan müşteri ID'si
     * @return Müşterinin tüm kredi başvurularının listesi
     */
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<CreditApplication>> getHistory(@PathVariable Long customerId) {
        return ResponseEntity.ok(creditService.getHistory(customerId));
    }
}