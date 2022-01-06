package com.ridecoder.greetingcardmaker.threewaysolutions.Models;

import java.io.Serializable;

public class UserData implements Serializable {
    String id,device_id,company_name,company_logo,address,mobile_number1,email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getMobile_number1() {
        return mobile_number1;
    }

    public void setMobile_number1(String mobile_number1) {
        this.mobile_number1 = mobile_number1;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
