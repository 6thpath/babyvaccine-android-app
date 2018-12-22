package com.t6.babyvaccin.babyvaccin.model;

public class BabyClass {
    private String uid;
    private String name;
    private String dob;

    public BabyClass() { }

    public BabyClass(String uid, String name, String dob) {
        this.uid = uid;
        this.name = name;
        this.dob = dob;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
