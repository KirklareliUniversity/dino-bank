package com.dinobank.controller;

import com.dinobank.dto.LoginRequestDto;
import com.dinobank.model.Customer;
import com.dinobank.repository.CustomerRepository;
import com.dinobank.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * =============================================================================
 * AUTH CONTROLLER - Kimlik Doğrulama REST API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, kullanıcı kimlik doğrulama (authentication) işlemlerini
 * yönetir.
 * Giriş yapma ve kayıt olma endpoint'lerini sağlar.
 * 
 * BASE URL: /api/auth
 * 
 * API ENDPOINT HARİTASI:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼─────────────────────┼─────────────────────────────────────────────┤
 * │ POST │ /api/auth/login │ Kullanıcı girişi (e-posta + şifre) │
 * │ POST │ /api/auth/register │ Yeni kullanıcı kaydı │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * GÜVENLİK ÖZELLİKLERİ:
 * - BCrypt ile şifre hashleme (tek yönlü şifreleme)
 * - Spring Security entegrasyonu
 * - CORS yapılandırması (frontend erişimi için)
 * 
 * FRONTEND KULLANIMI (React - api.js):
 * ```javascript
 * export const login = (email, password) => request('/auth/login', {
 * method: 'POST',
 * body: JSON.stringify({ email: email, sifre: password }),
 * });
 * 
 * export const register = (data) => request('/auth/register', {
 * method: 'POST',
 * body: JSON.stringify(data)
 * });
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see Customer Kullanıcı modeli
 * @see BCryptPasswordEncoder Şifre hashleme servisi
 */
@RestController // REST API kontrolcüsü
@RequestMapping("/api/auth") // Base path: /api/auth
@CrossOrigin(origins = "http://localhost:5173") // React dev server'a CORS izni
public class AuthController {

    /** Müşteri veritabanı erişimi */
    private final CustomerRepository customerRepository;

    /**
     * Hesap veritabanı erişimi (kayıt sırasında varsayılan hesap oluşturmak için)
     */
    private final AccountRepository accountRepository;

    /** Şifre hashleme/doğrulama servisi - BCrypt algoritması kullanır */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor - Bağımlılıkları enjekte eder
     */
    public AuthController(CustomerRepository customerRepository, AccountRepository accountRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * =========================================================================
     * KULLANICI GİRİŞİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/auth/login
     * 
     * AÇIKLAMA:
     * Kullanıcıyı e-posta ve şifre ile doğrular.
     * Başarılı girişte kullanıcı bilgilerini döner.
     * 
     * REQUEST BODY (JSON):
     * {
     * "email": "osman@dino.com",
     * "sifre": "123"
     * }
     * 
     * RESPONSE:
     * - 200 OK: { message, id, ad } - Giriş başarılı
     * - 401 Unauthorized: { message } - Hatalı kullanıcı/şifre
     * 
     * AKIŞ:
     * 1. E-posta ile müşteriyi bul
     * 2. BCrypt ile şifreyi doğrula
     * 3. Spring Security context'ine oturumu kaydet
     * 4. Kullanıcı bilgilerini döndür
     * 
     * @param request Login bilgileri (email, şifre)
     * @return Kullanıcı bilgileri veya hata mesajı
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto request) {

        // ADIM 1: E-posta ile müşteriyi ara
        Optional<Customer> customerOptional = customerRepository.findByEmail(request.getEmail());

        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Kullanıcı bulunamadı!"));
        }

        // ADIM 2: BCrypt ile şifre doğrulama
        // passwordEncoder.matches(düz_metin, hashlenmiş_şifre)
        if (!passwordEncoder.matches(request.getSifre(), customerOptional.get().getSifre())) {
            return ResponseEntity.status(401).body(Map.of("message", "Şifre hatalı!"));
        }

        // ADIM 3: Spring Security oturumunu başlat
        Customer customer = customerOptional.get();
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                customer.getEmail(),
                null,
                java.util.Collections.emptyList());
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authToken);

        // ADIM 4: Başarılı yanıt döndür
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Giriş Başarılı");
        response.put("id", customerOptional.get().getId());
        response.put("ad", customerOptional.get().getAd());

        return ResponseEntity.ok(response);
    }

    /**
     * =========================================================================
     * YENİ KULLANICI KAYDI ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/auth/register
     * 
     * AÇIKLAMA:
     * Yeni müşteri kaydı oluşturur ve otomatik olarak varsayılan
     * bir vadesiz hesap açar.
     * 
     * REQUEST BODY (JSON):
     * {
     * "tcKimlikNo": "12345678901",
     * "ad": "Ali",
     * "soyad": "Yılmaz",
     * "email": "ali@example.com",
     * "sifre": "güçlü_şifre",
     * "telefon": "05551234567",
     * "dogumTarihi": "1990-01-15",
     * "adres": "İstanbul"
     * }
     * 
     * RESPONSE:
     * - 200 OK: { message, id } - Kayıt başarılı
     * - 400 Bad Request: { message } - E-posta zaten kayıtlı
     * 
     * AKIŞ:
     * 1. E-posta tekrarı kontrolü
     * 2. Şifreyi BCrypt ile hashle
     * 3. Müşteriyi veritabanına kaydet
     * 4. Varsayılan vadesiz hesap oluştur
     * 5. Başarı mesajı döndür
     * 
     * @param customer Yeni müşteri bilgileri
     * @return Başarı mesajı veya hata
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Customer customer) {
        // ADIM 1: E-posta tekrarı kontrolü
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(Map.of("message", "Bu e-posta zaten kayıtlı!"));
        }

        // ADIM 2: Şifreyi BCrypt ile hashle (güvenlik için zorunlu!)
        customer.setSifre(passwordEncoder.encode(customer.getSifre()));
        customer.setAktif(true);
        customer.setKayitTarihi(LocalDate.now());

        // ADIM 3: Müşteriyi veritabanına kaydet
        Customer savedCustomer = customerRepository.save(customer);

        // ADIM 4: Varsayılan vadesiz hesap oluştur
        // Her yeni müşteriye otomatik olarak bir hesap açıyoruz
        com.dinobank.model.Account defaultAccount = new com.dinobank.model.Account();
        defaultAccount.setAccountNumber("TR" + System.currentTimeMillis()); // Benzersiz hesap no
        defaultAccount.setAccountType("VADESİZ");
        defaultAccount.setBalance(java.math.BigDecimal.ZERO); // Başlangıç bakiyesi: 0 TL
        defaultAccount.setCurrency("TRY");
        defaultAccount.setOpeningDate(LocalDateTime.now());
        defaultAccount.setActive(true);
        defaultAccount.setCustomer(savedCustomer);

        accountRepository.save(defaultAccount);

        // ADIM 5: Başarı mesajı döndür
        return ResponseEntity.ok(Map.of("message", "Kayıt Başarılı!", "id", savedCustomer.getId()));
    }
}