package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/18 14:13
 * @Description:
 */
public class MessageWarnResponse {
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
