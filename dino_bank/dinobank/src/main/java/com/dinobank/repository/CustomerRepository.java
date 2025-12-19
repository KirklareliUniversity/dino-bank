package com.dinobank.repository;

import com.dinobank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByTcKimlikNo(String tcKimlikNo);
    Optional<Customer> findByEmail(String email);
    Customer findByAd(String ad);
 
}
/**
 * =============================================================================
 * MÜŞTERİ REPOSITORY - Veritabanı Erişim Katmanı
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