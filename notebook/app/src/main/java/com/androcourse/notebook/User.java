package com.androcourse.notebook;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String data;
    private String userName;
    private String userLastName;
    private UUID uuid;
    private String phone;

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String lastName) {
        this.userLastName = lastName;
    }

    public User() {
        this.uuid = UUID.randomUUID();
    }

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
