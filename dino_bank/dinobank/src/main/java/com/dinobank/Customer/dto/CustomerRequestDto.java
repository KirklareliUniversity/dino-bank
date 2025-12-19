package com.dinobank.Customer.dto;

import java.time.LocalDate;

public class CustomerRequestDto {
    private String tcKimlikNo;
    private String ad;
    private String soyad;
    private String email;
    private String telefon;
    private LocalDate dogumTarihi;
    private String adres;
    private String sifre;

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(LocalDate dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}

/**
 * Burada CustomerRequestDto sınıfı oluşturulmuştur.
 * Bu sınıf, müşteri kayıtları için temel CRUD operasyonlarını gerçekleştirir.
 * 
 * @author DinoBank Development Team
 * @version 1.0
 */