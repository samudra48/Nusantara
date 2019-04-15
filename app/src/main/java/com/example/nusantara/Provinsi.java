package com.example.nusantara;

public class Provinsi {
    String Nama;
    String Ibukota;
    String Tempatwisata;
    String Budaya;
    String Makanankhas;

    public Provinsi() {
    }

    public Provinsi(String Nama, String Ibukota, String Tempatwisata, String Budaya, String Makanankhas) {
        this.Nama = Nama;
        this.Ibukota = Ibukota;
        this.Tempatwisata = Tempatwisata;
        this.Budaya = Budaya;
        this.Makanankhas = Makanankhas;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getIbukota() {
        return Ibukota;
    }

    public void setIbukota(String Ibukota) {
        this.Ibukota = Ibukota;
    }

    public String getTempatwisata() {
        return Tempatwisata;
    }

    public void setTempatwisata(String Tempatwisata) {
        this.Tempatwisata = Tempatwisata;
    }

    public String getBudaya() {
        return Budaya;
    }

    public void setBudaya(String Budaya) {
        this.Budaya = Budaya;
    }

    public String getMakanankhas() {
        return Makanankhas;
    }

    public void setMakanankhas(String Makanankhas) {
        this.Makanankhas = Makanankhas;
    }
}