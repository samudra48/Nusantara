package com.example.nusantara.model;

public class ItemModel {

    public String image;
    public String caption;
    public String captionlain;


    public ItemModel() {
    }

    public ItemModel(String image, String caption, String captionlain) {
        this.image = image;
        this.caption = caption;
        this.captionlain = captionlain;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getcaption() {
        return caption;
    }

    public void setcaption(String caption) {
        this.caption = caption;
    }

    public String getcaptionlain() {
        return captionlain;
    }

    public void setcaptionlain(String captionlain) {
        this.captionlain = captionlain;
    }
}