package com.example.plantina;

    public class Soil_Model {
        private String name,ph,temperature,note;

        public Soil_Model(String name, String ph, String temperature, String note) {
            this.name = name;
            this.ph = ph;
            this.temperature = temperature;
            this.note = note;
        }
        public Soil_Model(){}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPh() {
            return ph;
        }

        public void setPh(String ph) {
            this.ph = ph;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

