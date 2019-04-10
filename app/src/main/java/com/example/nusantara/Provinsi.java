package com.example.nusantara;

public class Provinsi {
    String Nama;
    String Ibukota;
    String Tempatwisata;
    String Budaya;
    String Makanankhas;

    public Provinsi() {
    }

    public Provinsi(String nama, String ibukota, String tempatwisata, String budaya, String makanankhas) {
        Nama = nama;
        Ibukota = ibukota;
        Tempatwisata = tempatwisata;
        Budaya = budaya;
        Makanankhas = makanankhas;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getIbukota() {
        return Ibukota;
    }

    public void setIbukota(String ibukota) {
        Ibukota = ibukota;
    }

    public String getTempatwisata() {
        return Tempatwisata;
    }

    public void setTempatwisata(String tempatwisata) {
        Tempatwisata = tempatwisata;
    }

    public String getBudaya() {
        return Budaya;
    }

    public void setBudaya(String budaya) {
        Budaya = budaya;
    }

    public String getMakanankhas() {
        return Makanankhas;
    }

    public void setMakanankhas(String makanankhas) {
        Makanankhas = makanankhas;
    }
}
