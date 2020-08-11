package com.softgates.instadoctor.demoo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ZoomResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("data")
    @Expose
    private List<ZoomData> zoomData = null;

    public List<ZoomData> getZoomData() {
        return zoomData;
    }

    public void setZoomData(List<ZoomData> zoomData) {
        this.zoomData = zoomData;
    }








    public String getmessage() {
        return message;
    }

    public void setmessage(boolean error) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

