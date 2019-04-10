package com.example.nusantara;

public class ItemMenu {
    String name;
    String desk;
    String img;

    public ItemMenu(){

    }
    public ItemMenu(String name,String desk,String img){
        this.name = name;
        this.desk = desk;
        this.img = img;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDesk(){
        return desk;
    }
    public void setDesk(String desk){
        this.desk = desk;
    }
    public String getImg(){
        return img;
    }
    public void setImg(String img){
        this.img = img;
    }
}
