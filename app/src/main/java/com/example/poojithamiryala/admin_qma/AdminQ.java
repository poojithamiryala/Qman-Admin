package com.example.poojithamiryala.admin_qma;

/**
 * Created by poojitha miryala on 30-03-2018.
 */

public class AdminQ
{
    @com.google.gson.annotations.SerializedName("username")

    String username;
    @com.google.gson.annotations.SerializedName("orgname")
    String orgname;
    @com.google.gson.annotations.SerializedName("service")
    String servicename;
    @com.google.gson.annotations.SerializedName("adminQ")
    int adminQ;
    @com.google.gson.annotations.SerializedName("time")
    long time;
    @com.google.gson.annotations.SerializedName("id")
    private String id;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdminQ() {
        return adminQ;
    }

    public void setAdminQ(int adminQ) {
        this.adminQ = adminQ;
    }

    public String getServicename() {

        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
