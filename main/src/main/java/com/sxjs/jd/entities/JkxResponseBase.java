package com.sxjs.jd.entities;

public class JkxResponseBase {

    private String mCount; // 总个数

    private String mPageNo;// 当前页码

    private String mPageSize;// 当前条数

    private String mTotalPage; // 总页数

    private String code;
    private String msg;

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

    public String getmCount() {
        return mCount;
    }

    public void setmCount(String mCount) {
        this.mCount = mCount;
    }

    public String getmPageNo() {
        return mPageNo;
    }

    public void setmPageNo(String mPageNo) {
        this.mPageNo = mPageNo;
    }

    public String getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(String mPageSize) {
        this.mPageSize = mPageSize;
    }

    public String getmTotalPage() {
        return mTotalPage;
    }

    public void setmTotalPage(String mTotalPage) {
        this.mTotalPage = mTotalPage;
    }

}
