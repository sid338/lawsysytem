package com.example.lawsystem;

public class police_ModelClass {
    String stationlocation,phonenumber;

    public police_ModelClass() {
    }

    public police_ModelClass(String stationlocation, String phonenumber) {
        this.stationlocation = stationlocation;
        this.phonenumber = phonenumber;
    }

    public String getStationlocation() {
        return stationlocation;
    }

    public void setStationlocation(String stationlocation) {
        this.stationlocation = stationlocation;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
