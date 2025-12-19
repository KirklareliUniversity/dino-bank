package com.dinobank.Customer.controller;

import java.time.LocalDate;

public class CustomerResponseDto {
    private Long id;
    private String ad;
    private String soyad;
    private String email;
    private LocalDate kayitTarihi;

    public CustomerResponseDto(Long id, String ad, String soyad, String email, LocalDate kayitTarihi) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.kayitTarihi = kayitTarihi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getKayitTarihi() {
        return kayitTarihi;
    }

    public void setKayitTarihi(LocalDate kayitTarihi) {
        this.kayitTarihi = kayitTarihi;
    }
}

/**
 * Burada CustomerResponseDto sınıfı oluşturulmuştur.
 * Bu sınıf, müşteri kayıtları için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */