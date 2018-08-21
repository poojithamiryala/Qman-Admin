package com.example.poojithamiryala.admin_qma;

/**
 * Created by poojitha miryala on 16-03-2018.
 */

public class Organization_details
{
    @com.google.gson.annotations.SerializedName("category")
    private String category;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("branch")
    private String branches;
    @com.google.gson.annotations.SerializedName("city")
    private String city;
    @com.google.gson.annotations.SerializedName("services")
    private String services;

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @com.google.gson.annotations.SerializedName("Username")
    private String username;
    @com.google.gson.annotations.SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
