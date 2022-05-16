package com.example.fraction2;

public class Menu {
    private String Name;

    private String Image;

    private String price;
    public Menu(){}

    public Menu(String name, String image, String price) {
        Name = name;
        Image = image;
        this.price = price;
    }


    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
