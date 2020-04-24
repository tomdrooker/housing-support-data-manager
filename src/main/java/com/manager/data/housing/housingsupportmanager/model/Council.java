package com.manager.data.housing.housingsupportmanager.model;

public class Council {

    private String councilName;
    private String councilPhone;
    private String councilEmail;
    private int councilId;

    public Council() {};

    public Council(String councilName, String councilPhone, String councilEmail, int councilId) {
        this.councilName = councilName;
        this.councilPhone = councilPhone;
        this.councilEmail = councilEmail;
        this.councilId = councilId;
    }

    public String getCouncilName() {
        return councilName;
    }

    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public String getCouncilPhone() {
        return councilPhone;
    }

    public void setCouncilPhone(String councilPhone) {
        this.councilPhone = councilPhone;
    }

    public String getCouncilEmail() {
        return councilEmail;
    }

    public void setCouncilEmail(String councilEmail) {
        this.councilEmail = councilEmail;
    }

    public int getCouncilId() {
        return councilId;
    }

    public void setCouncilId(int councilId) {
        this.councilId = councilId;
    }
}
