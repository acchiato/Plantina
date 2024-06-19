package com.example.plantina;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultData {

    @SerializedName("prediction")
    @Expose
    private String prediction;

    public String getPrediction() {
        return prediction;
    }



}
