package com.vku.lmhiep.finalexam.Data;

public class react {
    String gmail;
    String name;

    public react() {
    }

    public react(String gmail, String name) {
        this.gmail = gmail;
        this.name = name;
    }



    public react(String name){
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
