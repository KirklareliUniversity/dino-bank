package com.dinobank.Customer.service;

import com.dinobank.Customer.controller.CustomerResponseDto;
import com.dinobank.Customer.dto.CustomerRequestDto;
import com.dinobank.model.Account;
import com.dinobank.model.Customer;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service // Bu sınıf bir Spring Bean olarak kaydedilir
// @Service annotation'ı, bu sınıfın bir servis katmanı olduğunu belirtir.
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository,
            AccountRepository accountRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto request) {
        // Şuanlık tc kimliği rastgele atıyoruz, ileride doğrulama servisi ile entegre edilecek
        String finalTc = request.getTcKimlikNo();
        if (finalTc == null || finalTc.isEmpty()) {
            long randomNum = 10000000000L + (long) (Math.random() * 9000000000L);
            finalTc = String.valueOf(randomNum);
        }

        validateTcKimlik(finalTc);

        if (customerRepository.existsByTcKimlikNo(finalTc)) {
            throw new RuntimeException("Bu TC Kimlik Numarası ile zaten bir kayıt mevcut!");
        }

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Bu E-posta adresi zaten kullanılıyor!");
        }

        Customer customer = new Customer();
        customer.setTcKimlikNo(finalTc);
        customer.setAd(request.getAd() != null ? request.getAd() : "Misafir");
        customer.setSoyad(request.getSoyad() != null ? request.getSoyad() : "Kullanıcı");
        customer.setDogumTarihi(request.getDogumTarihi() != null 
            ? request.getDogumTarihi() : LocalDate.of(2000, 1, 1));

        customer.setEmail(request.getEmail());
        customer.setTelefon(request.getTelefon());
        customer.setAdres(request.getAdres());
        customer.setSifre(passwordEncoder.encode(request.getSifre()));
        customer.setKayitTarihi(LocalDate.now());
        customer.setAktif(true);

        Customer savedCustomer = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(savedCustomer);
        long randomAccNum = (long) (Math.random() * 100000000000L);
        account.setAccountNumber(String.valueOf(randomAccNum));
        account.setBalance(new BigDecimal("5000.00"));
        account.setCurrency("TRY");
        account.setAccountType("VADESIZ");
        account.setActive(true);
        account.setOpeningDate(LocalDateTime.now());

        accountRepository.save(account);

        return new CustomerResponseDto(
                savedCustomer.getId(),
                savedCustomer.getAd(),
                savedCustomer.getSoyad(),
                savedCustomer.getEmail(),
                savedCustomer.getKayitTarihi());
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponseDto(
                        customer.getId(),
                        customer.getAd(),
                        customer.getSoyad(),
                        customer.getEmail(),
                        customer.getKayitTarihi()))
                .collect(Collectors.toList());
    }

    private void validateTcKimlik(String tc) {
        if (tc == null)
            return; 

        if (tc.length() != 11) {
            throw new RuntimeException("Geçersiz TC: TC Kimlik Numarası 11 haneli olmalıdır!");
        }

        if (!tc.matches("[0-9]+")) {
            throw new RuntimeException("Geçersiz TC: Sadece rakamlardan oluşmalıdır!");
        }

        if (tc.startsWith("0")) {
            throw new RuntimeException("Geçersiz TC: TC Kimlik Numarası 0 ile başlayamaz!");
        }
    }
}

/**
 * Burada CustomerService sınıfı oluşturulmuştur.
 * Bu sınıf, müşteri işlemleri için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */
