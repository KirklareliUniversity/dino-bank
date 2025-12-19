/**
 * =============================================================================
 * API.JS - Frontend HTTP İstek Yönetim Modülü
 * =============================================================================
 * 
 * Bu dosya, React frontend'in Spring Boot backend ile iletişim kurmasını sağlar.
 * Fetch API'yi kullanarak HTTP istekleri yapar ve yanıtları işler.
 * 
 * BACKEND BAĞLANTISI:
 * - Geliştirme: Vite proxy üzerinden /api → http://localhost:8081
 * - Üretim: Aynı origin'de /api yolu
 * 
 * KULLANIM ÖRNEKLERİ:
 * ```javascript
 * import { login, getAccountSummary, transfer } from '@/lib/api';
 * 
 * // Login işlemi
 * const userData = await login('email@example.com', 'password123');
 * 
 * // Hesap özeti çekme
 * const account = await getAccountSummary(userData.id);
 * 
 * // Para transferi
 * await transfer({ fromAccountId: 1, toAccountId: 2, amount: 100 });
 * ```
 * 
 * @module api
 * @author DinoBank Frontend Team
 * @version 1.0
 */

// =============================================================================
// BASE URL - API İsteklerinin Temel Adresi
// =============================================================================
// Vite'ın proxy ayarı sayesinde /api istekleri otomatik olarak
// http://localhost:8081'e yönlendirilir (vite.config.js'de tanımlı)
const BASE_URL = '/api';

/**
 * =============================================================================
 * REQUEST - Genel HTTP İstek Fonksiyonu
 * =============================================================================
 * 
 * Tüm API istekleri için temel fonksiyon. Hata yönetimi ve JSON
 * dönüşümü otomatik olarak yapılır.
 * 
 * AKIŞ:
 * 1. Varsayılan header'ları ayarla (Content-Type: application/json)
 * 2. Fetch isteğini gerçekleştir
 * 3. HTTP hata durumlarını kontrol et (4xx, 5xx)
 * 4. Yanıtı JSON olarak parse et ve döndür
 * 
 * @param {string} endpoint - API endpoint'i (örn: '/auth/login')
 * @param {object} options - Fetch ayarları (method, body, headers)
 * @returns {Promise<any>} - API yanıtı (JSON olarak)
 * @throws {Error} - Ağ hatası veya HTTP hata durumunda
 */
export async function request(endpoint, options = {}) {
    // Varsayılan ayarlar
    const defaults = {
        headers: {
            'Content-Type': 'application/json', // JSON formatında veri gönderiyoruz
        },
    };

    // Kullanıcı ayarlarını varsayılanlarla birleştir
    const config = {
        ...defaults,
        ...options,
        headers: {
            ...defaults.headers,
            ...options.headers,
        },
    };

    let response;
    try {
        // Fetch API ile HTTP isteği yap
        response = await fetch(`${BASE_URL}${endpoint}`, config);
    } catch (error) {
        // Ağ hatası (internet yok, sunucu kapalı vb.)
        throw new Error('Network error');
    }

    // HTTP hata kontrolü (4xx - client hatası, 5xx - server hatası)
    if (!response.ok) {
        let errorMessage = 'An error occurred';
        try {
            // Backend'den gelen hata mesajını al
            const data = await response.json();
            errorMessage = data.message || errorMessage;
        } catch (e) {
            // JSON parse edilemezse düz metin olarak al
            errorMessage = await response.text();
        }
        throw new Error(errorMessage);
    }

    // 204 No Content - Boş yanıt (silme işlemleri gibi)
    if (response.status === 204) {
        return null;
    }

    // Başarılı yanıtı JSON olarak döndür
    try {
        return await response.json();
    } catch (e) {
        return null;
    }
}

// =============================================================================
// KİMLİK DOĞRULAMA (AUTHENTICATION) API'LERİ
// =============================================================================

/**
 * Kullanıcı Girişi
 * 
 * ENDPOINT: POST /api/auth/login
 * BACKEND: AuthController.login()
 * 
 * @param {string} email - Kullanıcı e-posta adresi
 * @param {string} password - Kullanıcı şifresi
 * @returns {Promise<{id: number, ad: string, message: string}>}
 */
export const login = (email, password) => request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email: email, sifre: password }),
});

/**
 * Yeni Kullanıcı Kaydı
 * 
 * ENDPOINT: POST /api/auth/register
 * BACKEND: AuthController.register()
 * 
 * @param {object} data - Kayıt bilgileri (tcKimlikNo, ad, soyad, email, sifre, vb.)
 * @returns {Promise<{id: number, message: string}>}
 */
export const register = (data) => request('/auth/register', {
    method: 'POST',
    body: JSON.stringify(data)
});

// =============================================================================
// MÜŞTERİ VE HESAP API'LERİ
// =============================================================================

/**
 * Müşteri Bilgilerini Getir
 * 
 * ENDPOINT: GET /api/customers/{id}
 * 
 * @param {number} id - Müşteri ID'si
 * @returns {Promise<Customer>}
 */
export const getCustomer = (id) => request(`/customers/${id}`);

/**
 * Hesap Özeti Getir (Dashboard için)
 * 
 * ENDPOINT: GET /api/accounts/summary/{customerId}
 * BACKEND: AccountSummaryController.getAccountByCustomerId()
 * 
 * Müşterinin ana hesap bilgisini (bakiye, hesap no) döner.
 * 
 * @param {number} customerId - Müşteri ID'si
 * @returns {Promise<Account>} - Hesap bilgileri (balance, accountNumber, vb.)
 */
export const getAccountSummary = (customerId) => request(`/accounts/summary/${customerId}`);

// =============================================================================
// İŞLEM (TRANSACTION) API'LERİ
// =============================================================================

/**
 * Hesap İşlem Geçmişini Getir
 * 
 * ENDPOINT: GET /api/transactions/history/{accountId}
 * BACKEND: TransactionController.getHistory()
 * 
 * @param {number} accountId - Hesap ID'si
 * @returns {Promise<Transaction[]>} - İşlem listesi
 */
export const getTransactions = (accountId) => request(`/transactions/history/${accountId}`);

/**
 * Para Transferi Yap
 * 
 * ENDPOINT: POST /api/transactions/transfer
 * BACKEND: TransactionController.transfer()
 * 
 * @param {object} data - Transfer bilgileri
 * @param {number} data.fromAccountId - Gönderen hesap ID
 * @param {number} data.toAccountId - Alıcı hesap ID
 * @param {number} data.amount - Transfer tutarı
 * @param {string} [data.description] - Açıklama (opsiyonel)
 * @returns {Promise<TransactionResponse>}
 */
export const transfer = (data) => request('/transactions/transfer', {
    method: 'POST',
    body: JSON.stringify(data)
});

/**
 * Yeni Hesap Oluştur
 * 
 * ENDPOINT: POST /api/accounts/create
 * BACKEND: AccountController.createAccount()
 * 
 * @param {object} data - Hesap bilgileri
 * @param {number} data.customerId - Müşteri ID
 * @param {string} data.accountType - Hesap tipi (VADESIZ, VADELI, DOVIZ)
 * @param {string} data.currency - Para birimi (TRY, USD, EUR)
 * @returns {Promise<Account>}
 */
export const createAccount = (data) => request('/accounts/create', {
    method: 'POST',
    body: JSON.stringify(data)
});

// =============================================================================
// VARSAYILAN EXPORT - api.login, api.register şeklinde kullanım için
// =============================================================================
export const api = {
    login,      // Kullanıcı girişi
    register,   // Yeni kayıt
    getCustomer,        // Müşteri bilgileri
    getAccountSummary,  // Hesap özeti
    getTransactions,    // İşlem geçmişi
    transfer,           // Para transferi
    createAccount       // Yeni hesap
};
