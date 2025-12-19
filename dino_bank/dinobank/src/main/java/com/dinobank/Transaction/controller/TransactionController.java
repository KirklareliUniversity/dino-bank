package com.dinobank.Transaction.controller;

import com.dinobank.Transaction.dto.TransactionRequestDto;
import com.dinobank.Transaction.dto.TransactionResponseDto;
import com.dinobank.Transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =============================================================================
 * TRANSACTION CONTROLLER - Para İşlemleri REST API Kontrolcüsü
 * =============================================================================
 * 
 * Bu kontrolcü, para transferi ve finansal işlemler için HTTP endpoint'leri
 * sağlar.
 * Frontend uygulaması bu endpoint'leri kullanarak hesaplar arası para transferi
 * ve işlem geçmişi sorgulama işlemlerini gerçekleştirir.
 * 
 * BASE URL: /api/transactions
 * 
 * API ENDPOINT HARİTASI:
 * ┌─────────────────────────────────────────────────────────────────────────────┐
 * │ METHOD │ ENDPOINT │ AÇIKLAMA │
 * ├────────┼──────────────────────────────┼────────────────────────────────────┤
 * │ POST │ /api/transactions/transfer │ Hesaplar arası para transferi │
 * │ POST │ /api/transactions/deposit │ Hesaba para yatırma │
 * │ GET │ /api/transactions/history/{id} │ Hesap işlem geçmişini getir │
 * └─────────────────────────────────────────────────────────────────────────────┘
 * 
 * FRONTEND KULLANIMI (React - api.js):
 * ```javascript
 * // Para transferi
 * export const transfer = (data) => request('/transactions/transfer', {
 * method: 'POST',
 * body: JSON.stringify(data)
 * });
 * 
 * // İşlem geçmişi
 * export const getTransactions = (accountId) =>
 * request(`/transactions/history/${accountId}`);
 * ```
 * 
 * @author DinoBank Development Team
 * @version 1.0
 * @see TransactionService İş mantığı katmanı
 */
@RestController // REST API kontrolcüsü
@RequestMapping("/api/transactions") // Tüm endpoint'ler bu path'ten başlar
public class TransactionController {

    /** İşlem iş mantığı servisi */
    private final TransactionService transactionService;

    /**
     * Constructor - TransactionService bağımlılığını enjekte eder
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * =========================================================================
     * PARA TRANSFERİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/transactions/transfer
     * 
     * AÇIKLAMA:
     * İki hesap arasında para transferi gerçekleştirir.
     * Bakiye kontrolü yapılır, yetersiz bakiye varsa hata döner.
     * 
     * REQUEST BODY (JSON):
     * {
     * "fromAccountId": 1, // Gönderen hesap ID
     * "toAccountId": 2, // Alıcı hesap ID
     * "amount": 1000.00, // Transfer tutarı
     * "description": "Kira" // Açıklama (opsiyonel)
     * }
     * 
     * RESPONSE:
     * - 200 OK: Transfer başarılı, işlem detayları döner
     * - 400 Bad Request: Yetersiz bakiye veya geçersiz hesap
     * 
     * @param request Transfer detayları
     * @return TransactionResponseDto - İşlem sonucu
     */
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransactionRequestDto request) {
        return ResponseEntity.ok(transactionService.transferMoney(request));
    }

    /**
     * =========================================================================
     * PARA YATIRMA ENDPOINT'İ
     * =========================================================================
     * 
     * URL: POST /api/transactions/deposit
     * 
     * AÇIKLAMA:
     * Belirtilen hesaba para yatırma işlemi yapar.
     * ATM veya şube yatırımlarını simüle eder.
     * 
     * REQUEST BODY (JSON):
     * {
     * "toAccountId": 1, // Hedef hesap ID
     * "amount": 5000.00, // Yatırılacak tutar
     * "description": "ATM" // Kaynak bilgisi
     * }
     * 
     * @param request Yatırma detayları
     * @return TransactionResponseDto - İşlem sonucu
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@RequestBody TransactionRequestDto request) {
        return ResponseEntity.ok(transactionService.deposit(request));
    }

    /**
     * =========================================================================
     * İŞLEM GEÇMİŞİ ENDPOINT'İ
     * =========================================================================
     * 
     * URL: GET /api/transactions/history/{accountId}
     * 
     * AÇIKLAMA:
     * Belirtilen hesabın tüm işlem geçmişini döner.
     * Dashboard'da "Son İşlemler" bölümünü doldurmak için kullanılır.
     * 
     * PATH VARIABLE:
     * - accountId: Hesabın benzersiz ID'si
     * 
     * ÖRNEK: GET /api/transactions/history/1
     * 
     * RESPONSE: TransactionResponseDto[] - İşlem listesi (tarih sıralı)
     * 
     * @param accountId URL'den alınan hesap ID'si
     * @return İşlem geçmişi listesi
     */
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getHistory(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId));
    }
}