package com.dinobank.account.controller;

import com.dinobank.account.dto.AccountRequestDto;
import com.dinobank.account.dto.AccountResponseDto;
import com.dinobank.account.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =============================================================================
 * ACCOUNT CONTROLLER - Hesap Yönetimi REST API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, banka hesabı oluşturma ve yönetim işlemleri için
 * HTTP endpoint'leri sağlar. Müşterilerin yeni hesap açması ve
 * mevcut hesaplarını görüntülemesi için kullanılır.
 * 
 * BASE URL: /api/accounts
 * 
 * API ENDPOINT HARİTASI:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼────────────────────────────────┼──────────────────────────────────┤
 * │ POST │ /api/accounts/create │ Yeni hesap oluştur │
 * │ GET │ /api/accounts/customer/{id} │ Müşterinin hesaplarını listele │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * FRONTEND KULLANIMI (React - api.js):
 * ```javascript
 * export const createAccount = (data) => request('/accounts/create', {
 * method: 'POST',
 * body: JSON.stringify(data)
 * });
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see AccountService İş mantığı katmanı
 */
@RestController // REST API kontrolcüsü olarak işaretle
@RequestMapping("/api/accounts") // Base path: /api/accounts
public class AccountController {

    /** Hesap işlemleri servisi */
    private final AccountService accountService;

    /**
     * Constructor - AccountService bağımlılığını enjekte eder
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * =========================================================================
     * YENİ HESAP OLUŞTURMA ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/accounts/create
     * 
     * AÇIKLAMA:
     * Müşteri için yeni banka hesabı oluşturur.
     * Hesap numarası otomatik üretilir (IBAN formatı).
     * 
     * REQUEST BODY (JSON):
     * {
     * "customerId": 1,
     * "accountType": "VADESIZ", // VADESIZ, VADELI, DOVIZ
     * "currency": "TRY", // TRY, USD, EUR
     * "initialBalance": 0 // Başlangıç bakiyesi (opsiyonel)
     * }
     * 
     * RESPONSE (200 OK):
     * - Yeni oluşturulan hesap bilgileri (accountNumber, id, vb.)
     * 
     * @param request Hesap oluşturma bilgileri
     * @return Oluşturulan hesap detayları
     */
    @PostMapping("/create")
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    /**
     * =========================================================================
     * MÜŞTERİ HESAPLARI LİSTELEME ENDPOINT'İ
     * =========================================================================
     * 
     * URL: GET /api/accounts/customer/{customerId}
     * 
     * AÇIKLAMA:
     * Belirtilen müşterinin sahip olduğu tüm banka hesaplarını listeler.
     * Admin paneli veya profil sayfası için kullanılır.
     * 
     * PATH VARIABLE:
     * - customerId: Müşterinin benzersiz ID'si
     * 
     * ÖRNEK: GET /api/accounts/customer/1
     * 
     * RESPONSE: AccountResponseDto[] - Müşterinin tüm hesapları
     * 
     * @param customerId URL'den alınan müşteri ID'si
     * @return Müşterinin hesap listesi
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponseDto>> getCustomerAccounts(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }
}