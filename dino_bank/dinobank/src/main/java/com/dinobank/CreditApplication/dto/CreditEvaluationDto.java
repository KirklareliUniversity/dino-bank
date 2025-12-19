package com.dinobank.CreditApplication.dto;

public class CreditEvaluationDto {
    private Long applicationId; 
    private String result; 
    private String reason; 

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

/**
 * Burada CreditEvaluationDto sınıfı oluşturulmuştur.
 * Bu sınıf, kredi başvurusu için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */
