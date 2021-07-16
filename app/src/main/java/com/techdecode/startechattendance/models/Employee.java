package com.techdecode.startechattendance.models;

public class Employee {
    String Name;
    String ID;
    String Code;

    public Employee(String name, String ID, String code) {
        Name = name;
        this.ID = ID;
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
    public String toString() {
        return Name + " "+ID;
    }
}
