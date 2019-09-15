package com.sxjs.jd.entities;


import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/8 19:15
 * @Description:
 */
public class LoginResponse implements Serializable {


    /**
     * code : 200200
     * data : ca0342e4-9a5b-410c-9bbb-d6e65a2b17f9
     * msg : null
     */

    private String code;
    private String data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "code='" + code + '\'' +
                ", data='" + data + '\'' +
                ", msg=" + msg +
                '}';
    }
}
