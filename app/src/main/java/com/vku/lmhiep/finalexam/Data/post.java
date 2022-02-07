package com.vku.lmhiep.finalexam.Data;

public class post {
    String name, content, img, gmail, cmt;

    public post() {
    }

    public post(String name, String content, String img, String gmail, String cmt) {
        this.name = name;
        this.content = content;
        this.img = img;
        this.gmail = gmail;
        this.cmt = cmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
