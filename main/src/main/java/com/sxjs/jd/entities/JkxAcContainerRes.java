package com.sxjs.jd.entities;

/**
 * Created by think1 on 2019/1/15.
 */

public class JkxAcContainerRes extends JkxResponseBase {
    private String ID;
    private String NAME;
    private String AC_CLASSIFIED=null;

    public String getAC_CLASSIFIED() {
        return AC_CLASSIFIED;
    }

    public void setAC_CLASSIFIED(String AC_CLASSIFIED) {
        this.AC_CLASSIFIED = AC_CLASSIFIED;
    }

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
}
