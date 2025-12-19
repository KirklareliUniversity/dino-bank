package com.dinobank.controller;

import com.dinobank.model.Account;
import com.dinobank.model.Customer;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CustomerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * ACCOUNT SUMMARY CONTROLLER - Dashboard Hesap Özeti API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, müşteri dashboard'u için hesap özet bilgilerini sağlar.
 * React frontend'deki DashboardPage.jsx bu endpoint'i kullanarak
 * bakiye ve hesap bilgilerini gösterir.
 * 
 * BASE URL: /api/accounts
 * 
 * API ENDPOINT:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼───────────────────────────────┼───────────────────────────────────┤
 * │ GET │ /api/accounts/summary/{id} │ Müşterinin ana hesap özetini getir│
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * FRONTEND KULLANIMI (React - api.js):
 * ```javascript
 * export const getAccountSummary = (customerId) =>
 * request(`/accounts/summary/${customerId}`);
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see Account Hesap modeli
 */
@RestController // REST API kontrolcüsü
@RequestMapping("/api/accounts") // Base path
@CrossOrigin(origins = "*") // Tüm origin'lerden erişime izin (geliştirme için)
public class AccountSummaryController {

    /** Hesap veritabanı erişimi */
    private final AccountRepository accountRepository;

    /** Müşteri veritabanı erişimi */
    private final CustomerRepository customerRepository;

    /**
     * Constructor - Repository bağımlılıklarını enjekte eder
     */
    public AccountSummaryController(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * =========================================================================
     * HESAP ÖZETİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: GET /api/accounts/summary/{customerId}
     * 
     * AÇIKLAMA:
     * Müşterinin ana hesap bilgisini (ilk hesabını) döner.
     * Dashboard sayfasında bakiye göstermek için kullanılır.
     * 
     * PATH VARIABLE:
     * - customerId: Müşterinin benzersiz ID'si
     * 
     * ÖRNEK: GET /api/accounts/summary/1
     * 
     * RESPONSE:
     * - 200 OK: Account objesi (balance, accountNumber, currency, vb.)
     * - 404 Not Found: Müşteri veya hesap bulunamadı
     * 
     * NOT: Müşterinin birden fazla hesabı olabilir, bu endpoint
     * sadece ilk hesabı döner. Tüm hesaplar için
     * AccountController.getCustomerAccounts() kullanılmalı.
     * 
     * @param customerId URL'den alınan müşteri ID'si
     * @return Müşterinin ana hesap bilgileri
     */
    @GetMapping("/summary/{customerId}")
    public ResponseEntity<Account> getAccountByCustomerId(@PathVariable Long customerId) {

        // ADIM 1: Müşteriyi veritabanında ara
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 - Müşteri yok
        }

        // ADIM 2: Müşterinin hesaplarını getir
        List<Account> accounts = accountRepository.findByCustomer(customerOpt.get());

        // ADIM 3: Hesap var mı kontrol et
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 - Hesap yok
        }

        // ADIM 4: İlk hesabı döndür (ana hesap olarak kabul ediyoruz)
        return ResponseEntity.ok(accounts.get(0));
    }

}