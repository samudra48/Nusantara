package com.example.nusantara;

public class ItemData {
    String img;
    String nama;
    String deskripsi;

    public ItemData(){

    }

    public ItemData(String img, String nama, String deskripsi) {
        this.img = img;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
