package com.uber.executer.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by goodson on 7/8/15.
 */
public class Calendar {
    @SerializedName("start")
    public String start;

    @SerializedName("end")
    public String end;

    @SerializedName("location")
    public String location;

    @SerializedName("status")
    public String status;

    @SerializedName("summary")
    public String summary;

    public String getStart () {

        return start;
    }

    public String getEnd () {
        return end;
    }

    public String getLocation () {
        return location;
    }

    public String getStatus () {
        return status;
    }

    public String getSummary () {
        return summary;
    }

}
