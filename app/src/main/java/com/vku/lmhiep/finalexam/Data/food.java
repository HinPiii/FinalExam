package com.vku.lmhiep.finalexam.Data;

public class food {
    String name;
    String calo;
    String img;
    int react;

    public food() {
    }

    public food(String name, String calo, String img, int react) {
        this.name = name;
        this.calo = calo;
        this.img = img;
        this.react = react;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getReact() {
        return react;
    }

    public void setReact(int react) {
        this.react = react;
    }
}
