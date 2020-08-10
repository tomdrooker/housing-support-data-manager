package com.manager.data.housing.housingsupportmanager.model;
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
    private String dhp;
    private String list;
    private String info;

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

    public String getDhp() {
        return dhp;
    }

    public void setDhp(String dhp) {
        this.dhp = dhp;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list =list;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}