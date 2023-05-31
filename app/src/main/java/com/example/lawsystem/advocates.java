package com.example.lawsystem;

public class advocates {

    private String Name,ContactNo,Email_Id;

    public advocates() {
    }

    public advocates(String name, String contactNo, String email_Id) {
        Name = name;
        ContactNo = contactNo;
        Email_Id = email_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }
}
