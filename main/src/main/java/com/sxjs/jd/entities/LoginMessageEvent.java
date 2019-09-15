package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/15 14:30
 * @Description:
 */
public   class LoginMessageEvent {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
