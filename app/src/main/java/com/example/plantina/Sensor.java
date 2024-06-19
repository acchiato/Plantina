package com.example.plantina;

import com.google.firebase.auth.FirebaseUser;

public class Sensor {
    private String ph,moisture,temprature,sensorid,email;

    // blank constructor to read values back
    public Sensor()
    {

    }

    public Sensor(String ph, String moisture, String temprature, String sensorid,String email) {
        this.ph = ph;
        this.moisture = moisture;
        this.temprature = temprature;
        this.sensorid = sensorid;
        this.email=email;
    }

    public String getSensorid() {
        return sensorid;
    }

    public void setSensorid(String sensorid) {
                this.sensorid = sensorid;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
