package com.example.plantina;

public class Plant_Model {
    private String Image;
    private String Name;

    public Plant_Model(String Image, String Name) {
        this.Image = Image;
        this.Name = Name;
    }
public Plant_Model(){

}

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;

    }

}
