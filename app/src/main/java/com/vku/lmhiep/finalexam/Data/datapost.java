package com.vku.lmhiep.finalexam.Data;

public class datapost {
    String img, name, content, pic, mail;

    public datapost() {
    }

    public datapost(String img, String name, String content, String pic, String mail) {
        this.img = img;
        this.name = name;
        this.content = content;
        this.pic = pic;
        this.mail = mail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
