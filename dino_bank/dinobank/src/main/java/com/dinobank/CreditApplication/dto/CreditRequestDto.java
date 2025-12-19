package com.dinobank.CreditApplication.dto;

import java.math.BigDecimal;

public class CreditRequestDto {
    private Long customerId;
    private BigDecimal requestedAmount;
    private Integer installmentCount; 
    private String purpose; 

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public void setInstallmentCount(Integer installmentCount) {
        this.installmentCount = installmentCount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}

/**
 * Burada CreditRequestDto sınıfı oluşturulmuştur.
 * Bu sınıf, kredi başvurusu için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */