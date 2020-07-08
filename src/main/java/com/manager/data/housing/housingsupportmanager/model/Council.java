package com.manager.data.housing.housingsupportmanager.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "council")
public class Council {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Enter a council name")
    private String name;
    private String address;
    private String email;
    private String phone;
    private String advice;
    private String dhp;
    private String lwa;
    private String hours;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] photo;

    public Council() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getDhp() {
        return dhp;
    }

    public void setDhp(String dhp) {
        this.dhp = dhp;
    }

    public String getLwa() {
        return lwa;
    }

    public void setLwa(String lwa) {
        this.lwa = lwa;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}