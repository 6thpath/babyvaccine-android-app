package com.t6.babyvaccin.babyvaccin.model;

public class IAClass {
    private String uid;
    private String name;
    private String address;

    public IAClass() {
    }

    public IAClass(String uid, String name, String description) {
        this.uid = uid;
        this.name = name;
        this.address = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
