package com.sxjs.jd;

import com.google.gson.Gson;
import com.sxjs.common.model.BaseDataManager;
import com.sxjs.common.model.DataManager;
import com.sxjs.common.model.http.BaseApiService;
import com.sxjs.common.util.FillUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author：xiepeng on 2018/4/20 18:26.
 */

public class MainDataManager extends BaseDataManager {

    //绩效app接口开始
    //public static String KPI_ROOT_URL = "http://192.168.1.75:8086/api";
    public static String KPI_ROOT_URL = "http://114.247.234.146:8086/api";


    //通用模块路径
    public static String GENERAL_DIR = "/cm/v1";

    //消息模块路径
    public static String MESSAGE_DIR = "/i/v1";

    //系统模块路径
    public static String SYSTEM_DIR_BASE = "/s/v1";

    //咨询模块路径
    public static String ASK_DIR  = "/q/v1";
    //首页模块路径
    public static String HOME_DIR = "/h/v1";

    //考中模块路径
    public static String MIDDLE_DIR = "/x/v1";

    //考前模块路径
    public static String BEFORE_DIR = "/y/v1";

    //审核模块路径
    public static String SHENHE_DIR = "/t/v1";

    //绩效app接口结束
    public MainDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static MainDataManager getInstance(DataManager dataManager) {
        return new MainDataManager(dataManager);
    }

    /*
     *验证短信验证码注册/登陆 （只做示例，无数据返回）
     */
    public Disposable login(DisposableObserver<ResponseBody> consumer, String mobile, String verifyCode) {

        return changeIOToMainThread(getService(MainApiService.class).login(mobile, verifyCode), consumer);
    }


    public Disposable getMainData(int start, int count, DisposableObserver<ResponseBody> consumer) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("start", start);
        map.put("count", count);
        return changeIOToMainThread(getService(BaseApiService.class).executeGet("http://www.baidu.com", map), consumer);

    }


    /**
     * 获取登录数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + SYSTEM_DIR_BASE, mapParameters, mapHeaders), consumer);
    }

    /**
     * 获取注册数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisretData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + SYSTEM_DIR_BASE, mapParameters, mapHeaders), consumer);

    }

    /**
     * 获取忘记密码数据
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getForgetPasswordData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + SYSTEM_DIR_BASE, mapParameters, mapHeaders), consumer);

    }

    /**
     * 获取验证码
     *
     * @param mapHeaders    请求头
     * @param mapParameters 请求参数
     * @param consumer      consumer
     * @return Disposable
     */
    public Disposable getRegisretCodeData(Map<String, String> mapHeaders, Map<String, Object> mapParameters, DisposableObserver<ResponseBody> consumer) {
        return changeIOToMainThread(getService(BaseApiService.class).executePostHeader
                (KPI_ROOT_URL + GENERAL_DIR, mapParameters, mapHeaders), consumer);

    }


    public List<String> getTypeOfNameData() {
        ArrayList<String> list = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            list.add("家用电器");
        }
        return list;
    }

    public <S> Disposable getData(DisposableObserver<S> consumer, final Class<S> clazz, final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = getContext().getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }
}
