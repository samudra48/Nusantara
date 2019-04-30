package com.example.nusantara;

public class ItemData {
    String uid;
    String img;
    String nama;
    String deskripsi;

    public ItemData(){

    }

    public ItemData(String img, String nama, String deskripsi, String uid) {
        this.img = img;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
