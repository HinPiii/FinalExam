package com.vku.lmhiep.finalexam.Data;

import java.util.Comparator;

public class users {
    private String name;
    private String gmail;
    private double weight;
    private int calories;
    private String calo;
    private double height;
    private String img;

    public users() {
    }

    public users(String name, String gmail, double weight, double height, int calories) {
        this.name = name;
        this.gmail = gmail;
        this.weight = weight;
        this.height = height;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static Comparator<users> CaloAscending = new Comparator<users>() {
        @Override
        public int compare(users o1, users o2) {
            return Integer.parseInt(o1.getCalo()) - Integer.parseInt(o2.getCalo());

        }
    };

    public static Comparator<users> CaloDescending = new Comparator<users>() {
        @Override
        public int compare(users o1, users o2) {
            return Integer.parseInt(o2.getCalo()) - Integer.parseInt(o1.getCalo());

        }
    };
}
