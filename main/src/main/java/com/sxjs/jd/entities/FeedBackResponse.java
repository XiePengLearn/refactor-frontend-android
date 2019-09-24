package com.sxjs.jd.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:34
 * @Description:
 */
public class FeedBackResponse implements Serializable {
    /**
     * code : 200200
     * msg : 说明
     * data :
     */

    private String code;
    private String msg;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
