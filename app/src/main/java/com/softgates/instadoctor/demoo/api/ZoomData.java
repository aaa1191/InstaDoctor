package com.softgates.instadoctor.demoo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

public class ZoomData implements Serializable {

    @SerializedName("id")
    @Expose
    private BigInteger id;

    @SerializedName("password")
    @Expose
    private String  password;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
