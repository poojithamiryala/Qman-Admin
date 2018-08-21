package com.example.poojithamiryala.admin_qma;

/**
 * Created by poojitha miryala on 05-04-2018.
 */

public class BookUser
{
    @com.google.gson.annotations.SerializedName("category")
    private String category;
    @com.google.gson.annotations.SerializedName("orgname")
    private String orgname;
    @com.google.gson.annotations.SerializedName("location")
    private String location;
    @com.google.gson.annotations.SerializedName("contact")
    private String contact;
    @com.google.gson.annotations.SerializedName("service")
    private String service;
    @com.google.gson.annotations.SerializedName("time")
    private String time;
    @com.google.gson.annotations.SerializedName("eta")
    private String eta;
    @com.google.gson.annotations.SerializedName("timestamp")
    private String timestamp;
    @com.google.gson.annotations.SerializedName("id")
    private String id;
    @com.google.gson.annotations.SerializedName("date")
    private String date;
    @com.google.gson.annotations.SerializedName("allotedno")
    private int allotedno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public int getAllotedno() {
        return allotedno;
    }

    public void setAllotedno(int allotedno) {
        this.allotedno = allotedno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
