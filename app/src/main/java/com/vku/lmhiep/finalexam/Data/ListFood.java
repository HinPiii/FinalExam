package com.vku.lmhiep.finalexam.Data;

public class ListFood {
    String name,calo, react, img, user;
    boolean fav = false;

    public ListFood() {
    }

    public ListFood(String name, String calo, String react, String img, String user) {
        this.name = name;
        this.calo = calo;
        this.react = react;
        this.img = img;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }

    public String getReact() {
        return react;
    }

    public void setReact(String react) {
        this.react = react;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
