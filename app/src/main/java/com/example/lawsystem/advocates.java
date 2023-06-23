package com.example.lawsystem;

public class advocates {

    private String Name,contactno,Email_Id;

    public advocates() {
    }

    public advocates(String name, String contactNo, String email_Id) {
        Name = name;
        contactno = contactNo;
        Email_Id = email_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContactNo() {
        return contactno;
    }

    public void setContactNo(String contactno) {
        contactno = contactno;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }
}
