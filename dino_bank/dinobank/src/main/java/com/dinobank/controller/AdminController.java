package com.dinobank.controller;

import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CustomerRepository;
import com.dinobank.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================================
 * ADMIN CONTROLLER - Yönetim Paneli REST API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, admin/yönetici paneli için veritabanı verilerine
 * erişim sağlar. Tüm müşteri, hesap ve işlem kayıtlarını görüntüler.
 * 
 * ⚠️ GÜVENLİK UYARISI: Bu endpoint hassas verilere erişim sağlar!
 * Üretim ortamında mutlaka yetkilendirme kontrolü eklenmelidir.
 * 
 * BASE URL: /api/admin
 * 
 * API ENDPOINT:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼─────────────────┼─────────────────────────────────────────────────┤
 * │ GET │ /api/admin/db │ Tüm veritabanı verilerini döner │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * FRONTEND KULLANIMI (React - AdminPage.jsx):
 * ```javascript
 * const response = await fetch('/api/admin/db');
 * const data = await response.json();
 * // data.customers - Tüm müşteriler
 * // data.accounts - Tüm hesaplar
 * // data.transactions - Tüm işlemler
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */
@RestController // REST API kontrolcüsü
@RequestMapping("/api/admin") // Admin endpoint'leri için base path
public class AdminController {

    /** Müşteri veritabanı erişimi */
    private final CustomerRepository customerRepository;

    /** Hesap veritabanı erişimi */
    private final AccountRepository accountRepository;

    /** İşlem veritabanı erişimi */
    private final TransactionRepository transactionRepository;

    /**
     * Constructor - Tüm repository bağımlılıklarını enjekte eder
     */
    public AdminController(CustomerRepository customerRepository, AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * =========================================================================
     * VERİTABANI VERİLERİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: GET /api/admin/db
     * 
     * AÇIKLAMA:
     * Sistemdeki tüm verileri tek bir yanıtta döner.
     * Admin panelinde tablo görüntülemek için kullanılır.
     * 
     * RESPONSE (200 OK):
     * {
     * "customers": [...], // Tüm müşteri kayıtları
     * "accounts": [...], // Tüm hesap kayıtları
     * "transactions": [...] // Tüm işlem kayıtları
     * }
     * 
     * ⚠️ DİKKAT: Bu endpoint tüm verileri döner!
     * Büyük veritabanlarında performans sorunu yaratabilir.
     * Üretim ortamında sayfalama (pagination) eklenmelidir.
     * 
     * @return Tüm veritabanı verilerini içeren Map objesi
     */
    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> getDatabaseData() {
        Map<String, Object> data = new HashMap<>();

        // Tüm müşterileri getir
        data.put("customers", customerRepository.findAll());

        // Tüm hesapları getir
        data.put("accounts", accountRepository.findAll());

        // Tüm işlemleri getir
        data.put("transactions", transactionRepository.findAll());

        return ResponseEntity.ok(data);
    }
}
