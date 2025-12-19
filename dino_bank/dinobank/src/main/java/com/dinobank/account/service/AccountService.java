package com.dinobank.account.service;

import com.dinobank.account.dto.AccountRequestDto;
import com.dinobank.account.dto.AccountResponseDto;
import com.dinobank.model.Account;
import com.dinobank.model.Customer;
import com.dinobank.repository.AccountRepository;
import com.dinobank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
//Bu sayfada AccountService sınıfı oluşturulmuştur.
//Bu sınıf Account tablosu için CRUD operasyonlarını gerçekleştirmek için kullanılır.
@Service // Bu sınıf bir servis sınıfıdır.
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public AccountResponseDto createAccount(AccountRequestDto request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

        Account account = new Account();
        account.setCustomer(customer);
        account.setCurrency(request.getCurrency());
        account.setBalance(BigDecimal.ZERO);
        account.setOpeningDate(LocalDateTime.now());
        account.setActive(true);
        account.setAccountType("VADESIZ");

        long randomNum = (long) (Math.random() * 10000000000L);
        account.setAccountNumber(String.valueOf(randomNum));

        Account savedAccount = accountRepository.save(account);

        return new AccountResponseDto(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance(),
                savedAccount.getCurrency(),
                savedAccount.getAccountType(),
                customer.getAd() + " " + customer.getSoyad(),
                savedAccount.getOpeningDate());
    }

    public List<AccountResponseDto> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findById(customerId).stream() 
                .map(acc -> new AccountResponseDto(
                        acc.getId(),
                        acc.getAccountNumber(),
                        acc.getBalance(),
                        acc.getCurrency(),
                        acc.getAccountType(),
                        acc.getCustomer().getAd() + " " + acc.getCustomer().getSoyad(),
                        acc.getOpeningDate()))
                .collect(Collectors.toList());
    }
}