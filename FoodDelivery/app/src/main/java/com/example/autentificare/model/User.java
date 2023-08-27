package com.example.autentificare.model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String IsStaff;


    public User() {
    }

    public User(String name, String password, String phone) {
        Name = name;
        Password = password;
        Phone = phone;
        IsStaff ="false";

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + Name + '\'' +
                ", password='" + Password + '\'' +
                ", phone='" + Phone + '\'' +
                ", IsStaff='" + IsStaff + '\'' +
                '}';
    }
}
