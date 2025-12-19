package com.dinobank.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.dinobank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * =============================================================================
 * İŞLEM REPOSITORY - Veritabanı Erişim Katmanı
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
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccount_IdOrToAccount_IdOrderByTransactionDateDesc(Long fromAccountId,
            Long toAccountId);

    List<Transaction> findByFromAccount_Id(Long accountId);

    List<Transaction> findByToAccount_Id(Long accountId);

    List<Transaction> findByStatus(String status);

    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
