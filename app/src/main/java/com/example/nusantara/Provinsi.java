package com.example.nusantara;

public class Provinsi {
    String Nama;
    String Ibukota;
    String Tempatwisata;
    String Budaya;
    String Makanankhas;

    public Provinsi() {
    }

    public Provinsi(String Nama, String ibukota, String tempatwisata, String budaya, String makanankhas) {
        Nama = Nama;
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
}