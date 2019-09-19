package com.sxjs.jd.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/19 19:05
 * @Description:
 */
public class UnReadMessageResponse {


    /**
     * code : 200200
     * data : {"YJ":1,"TX":2,"WD":1}
     * msg :
     */

    private String code;
    private DataBean data;
    private String   msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * YJ : 1
         * TX : 2
         * WD : 1
         */

        private int YJ;
        private int TX;
        private int WD;

        public int getYJ() {
            return YJ;
        }

        public void setYJ(int YJ) {
            this.YJ = YJ;
        }

        public int getTX() {
            return TX;
        }

        public void setTX(int TX) {
            this.TX = TX;
        }

        public int getWD() {
            return WD;
        }

        public void setWD(int WD) {
            this.WD = WD;
        }
    }
}
