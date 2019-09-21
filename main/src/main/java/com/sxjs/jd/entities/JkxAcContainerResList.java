package com.sxjs.jd.entities;

import java.util.ArrayList;

/**
 * Created by think1 on 2019/1/15.
 */

public class JkxAcContainerResList extends JkxResponseBase {
    private ArrayList<JkxAcContainerRes> PAGE;

    public ArrayList<JkxAcContainerRes> getPAGE() {
        return PAGE;
    }

    public void setPAGE(ArrayList<JkxAcContainerRes> PAGE) {
        this.PAGE = PAGE;
    }
}
