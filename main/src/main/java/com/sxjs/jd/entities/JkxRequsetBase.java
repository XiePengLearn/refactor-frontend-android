package com.sxjs.jd.entities;

public class JkxRequsetBase {

    private String PAGE_NO; // 当前页码

    private String PAGE_SIZE; // 当前条数

    private boolean mRefreshOrLoadMore;// 判断是否是刷新或加载更多
    private boolean isLoading; // 是否显示loading false 显示 true 不显示

    public String getPAGE_NO() {
        return PAGE_NO;
    }

    public void setPAGE_NO(String pAGE_NO) {
        PAGE_NO = pAGE_NO;
    }

    public String getPAGE_SIZE() {
        return PAGE_SIZE;
    }

    public void setPAGE_SIZE(String pAGE_SIZE) {
        PAGE_SIZE = pAGE_SIZE;
    }

    public boolean ismRefreshOrLoadMore() {
        return mRefreshOrLoadMore;
    }

    public void setmRefreshOrLoadMore(boolean mRefreshOrLoadMore) {
        this.mRefreshOrLoadMore = mRefreshOrLoadMore;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
