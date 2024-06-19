package com.example.plantina;

public class News_Model {
private String heading,description,date;

    public News_Model(String heading, String description, String date) {
        this.heading = heading;
        this.description = description;
        this.date = date;
    }

    public News_Model() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
