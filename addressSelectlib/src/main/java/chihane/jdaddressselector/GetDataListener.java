package chihane.jdaddressselector;

/**
 * Created by think1 on 2019/1/16.
 */


public interface GetDataListener {
    /**
     * 回调接口，根据选择深度，按顺序返回选择的对象。
     */
    void onGetDataListen(String id);
}
