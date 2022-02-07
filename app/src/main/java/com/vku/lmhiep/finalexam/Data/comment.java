package com.vku.lmhiep.finalexam.Data;

public class comment {
    String name, food, img, gmail, cmt, content;

    public comment() {
    }

    public comment(String name, String food, String cmt, String img, String gmail) {
        this.name = name;
        this.food = food;
        this.cmt = cmt;
        this.img = img;
        this.gmail = gmail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
