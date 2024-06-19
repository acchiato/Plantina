package com.example.plantina;

public class Customer {
        private String name,username,email,age,password;

// blank constructor to read values back
        public Customer()
        {

        }

        //initialize the values
    public Customer(String name, String username, String email, String age, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String image) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAddress(String address) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
