package com.dinobank.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Kullanıcıya account bilgilerini dönerken kullanılacak veri yapısı
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String accountType;
    private String customerName; 
    private LocalDateTime openingDate;

    public AccountResponseDto(Long id, String accountNumber, BigDecimal balance, String currency, String accountType,
            String customerName, LocalDateTime openingDate) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.accountType = accountType;
        this.customerName = customerName;
        this.openingDate = openingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }
}