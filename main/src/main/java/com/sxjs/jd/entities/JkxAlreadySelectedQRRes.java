package com.sxjs.jd.entities;

/**
 * Created by think1 on 2019/1/17.
 */

public class JkxAlreadySelectedQRRes extends JkxResponseBase {
    private String ID;
    private String NAME;
    private String PID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }
}
