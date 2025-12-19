package com.dinobank.dto;

public class LoginRequestDto {
    private String email;
    private String sifre;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
/**
 * Burada LoginRequestDto sınıfı oluşturulmuştur.
 * Bu sınıf, kullanıcı giriş yaparken gerekli bilgileri tutar.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */