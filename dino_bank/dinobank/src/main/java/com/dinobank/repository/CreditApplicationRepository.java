package com.dinobank.repository;

import com.dinobank.model.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * =============================================================================
 * KREDİ BAŞVURUSU REPOSITORY - Veritabanı Erişim Katmanı
 * =============================================================================
 * 
 * Bu interface, Spring Data JPA'nın güçlü "Repository Pattern" yapısını
 * kullanır.
 * JpaRepository'yi extend ederek, sıfır kod yazarak temel CRUD işlemlerini elde
 * ederiz.
 * 
 * KALITIMLA GELEN HAZIR METODLAR:
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ save(entity) → Yeni kayıt ekler veya günceller │
 * │ findById(id) → ID ile kayıt bulur (Optional döner) │
 * │ findAll() → Tüm kayıtları listeler │
 * │ deleteById(id) → ID ile kayıt siler │
 * │ count() → Toplam kayıt sayısını döner │
 * │ existsById(id) → Kaydın var olup olmadığını kontrol eder │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * SPRING DATA JPA'NIN "QUERY DERIVATION" MEKANİZMASI:
 * Spring, metod isimlerini okuyarak otomatik SQL sorguları oluşturur!
 * findBy + FieldName → WHERE field_name = ?
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */
@Repository // Spring'e bu interface'in bir Repository bean'i olduğunu bildirir
public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

    /**
     * Belirli Bir Müşterinin Tüm Kredi Başvurularını Getirir
     * 
     * Spring Data JPA, bu metod ismini parse ederek şu SQL'i üretir:
     * → SELECT * FROM credit_application WHERE customer_id = ?
     * 
     * KULLANIM ALANLARI:
     * - Müşteri profilinde geçmiş başvuruları göstermek
     * - Müşteri bazlı kredi raporu çıkarmak
     * 
     * @param customerId Müşterinin benzersiz kimlik numarası
     * @return Müşteriye ait tüm kredi başvurularının listesi
     */
    List<CreditApplication> findByCustomerId(Long customerId);

    /**
     * Belirli Bir Duruma Sahip Tüm Başvuruları Getirir
     * 
     * Spring Data JPA, bu metod ismini parse ederek şu SQL'i üretir:
     * → SELECT * FROM credit_application WHERE status = ?
     * 
     * OLASI STATUS DEĞERLERİ:
     * ┌───────────────────────────────────────────────────────┐
     * │ "PENDING" → Değerlendirme bekleyen başvurular │
     * │ "APPROVED" → Onaylanmış başvurular │
     * │ "REJECTED" → Reddedilmiş başvurular │
     * └───────────────────────────────────────────────────────┘
     * 
     * KULLANIM ALANLARI:
     * - Admin panelinde bekleyen başvuruları listelemek
     * - Raporlama: Onay/Red oranları analizi
     * 
     * @param status Filtrelenmek istenen başvuru durumu
     * @return Belirtilen duruma sahip başvuruların listesi
     */
    List<CreditApplication> findByStatus(String status);
}
