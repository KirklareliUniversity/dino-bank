package com.dinobank.config;

import com.dinobank.model.Account;
import com.dinobank.model.Customer;
import com.dinobank.model.Transaction;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CustomerRepository;
import com.dinobank.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataSeeder(CustomerRepository customerRepository, AccountRepository accountRepository,
            TransactionRepository transactionRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createTestUser("Osman", "Yetkin", "osman@dino.com", "11111111110", new BigDecimal("1000000.00"));
        createTestUser("Kürşat", "Uğantaş", "kursat@dino.com", "22222222220", new BigDecimal("100000.00"));
        createTestUser("İlkmert", "Kullanıcı", "ilkmert@dino.com", "33333333330", new BigDecimal("101000.00"));
        createTestUser("Atilla", "Güneş", "atilla@dino.com", "44444444440", new BigDecimal("75000.00"));
    }

    private void createTestUser(String ad, String soyad, String email, String tc, BigDecimal balance) {
        if (customerRepository.findByEmail(email).isEmpty()) {
            Customer customer = new Customer();
            customer.setAd(ad);
            customer.setSoyad(soyad);
            customer.setEmail(email);
            customer.setTcKimlikNo(tc);
            customer.setTelefon("5550000000"); // Default phone
            customer.setAdres("Test Mah. Dino Cad. No:1");
            customer.setDogumTarihi(LocalDate.of(1990, 1, 1));
            customer.setSifre(passwordEncoder.encode("123")); // Default password
            customer.setAktif(true);
            customer.setKayitTarihi(LocalDate.now());

            customer = customerRepository.save(customer);

            Account account = new Account();
            account.setCustomer(customer);
            // Generate deterministic account number based on TC for simplicity in tests, or
            // random
            account.setAccountNumber("TR" + tc);
            account.setAccountType("VADESIZ");
            account.setBalance(balance);
            account.setCurrency("TRY");
            account.setActive(true);
            account.setOpeningDate(java.time.LocalDateTime.now());

            accountRepository.save(account);

            // Seed Transactions
            createTransaction(account, new BigDecimal("50000.00"), "DEPOSIT", "Maaş Ödemesi",
                    java.time.LocalDateTime.now().minusDays(5));
            createTransaction(account, new BigDecimal("15000.00"), "WITHDRAWAL", "Kira Ödemesi",
                    java.time.LocalDateTime.now().minusDays(4));
            createTransaction(account, new BigDecimal("200.00"), "WITHDRAWAL", "Netflix",
                    java.time.LocalDateTime.now().minusDays(2));
            createTransaction(account, new BigDecimal("1500.00"), "WITHDRAWAL", "Market Alışverişi",
                    java.time.LocalDateTime.now().minusDays(1));

            System.out.println("✅ Test User Created: " + ad + " (" + balance + " TL)");
        }
    }

    private void createTransaction(Account account, BigDecimal amount, String type, String desc,
            java.time.LocalDateTime date) {
        Transaction tx = new Transaction();
        if (type.equals("DEPOSIT")) {
            tx.setToAccount(account);
            tx.setFromAccount(null); // ATM or External
        } else {
            tx.setFromAccount(account);
            tx.setToAccount(null); // External payment
        }
        tx.setAmount(amount);
        tx.setTransactionType(type);
        tx.setDescription(desc);
        tx.setTransactionDate(date);
        tx.setStatus("COMPLETED");
        tx.setCurrency("TRY");
        transactionRepository.save(tx);
    }
}
