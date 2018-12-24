package com.t6.babyvaccin.babyvaccin.model;

public class VaccinClass {

    private String uid;
    private String name;
    private String description;
    private String injectAt;

    public VaccinClass() {
    }

    public VaccinClass(String uid, String name, String description, String injectAt) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.injectAt = injectAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInjectAt() {
        return injectAt;
    }

    public void setInjectAt(String injectAt) {
        this.injectAt = injectAt;
    }


}
