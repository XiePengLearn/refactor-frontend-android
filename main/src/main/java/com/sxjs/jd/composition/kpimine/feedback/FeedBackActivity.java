package com.sxjs.jd.composition.kpimine.feedback;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PermissionHelper;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.myJsonParser;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.FeedBackResponse;
import com.sxjs.jd.entities.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/feedBackActivity/feedBackActivity")
public class FeedBackActivity extends BaseActivity implements FeedBackContract.View {
    @Inject
    FeedBackPresenter mPresenter;


    private static final String TAG = "NationExamActivity";
    @BindView(R2.id.fake_status_bar)
    View           fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView       jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button         jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView       jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView       jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView       newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView       jkxTitleRight;
    @BindView(R2.id.ed_content)
    EditText       edContent;
    @BindView(R2.id.iv_record)
    ImageView      ivRecord;
    @BindView(R2.id.tv_commit)
    TextView       tvCommit;
    private Button           mLoginEntry;
    private FeedBackResponse feedBackResponse;

    // 语音听写对象
    private SpeechRecognizer        mIat;
    // 语音听写UI
    private RecognizerDialog        mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults;
    private SharedPreferences       mSharedPreferences;
    // 引擎类型
    private String                  mEngineType      = SpeechConstant.TYPE_CLOUD;
    private boolean                 mTranslateEnable = false;

    private             StringBuffer buffer;
    public static final int          START_RECORD = 24;    //录音

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        //讯飞sdk初始化
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        //        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5d44e8f9");
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        buffer = new StringBuffer();
        mIatResults = new LinkedHashMap<>();
        //        initSDK();
        initTitle();
        initView();

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //返回按钮
        jkxTitleLeft.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("我的反馈");


    }

    private void initSDK() {

        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(this.getApplicationContext(), mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(mContext, mInitListener);

        mSharedPreferences = mContext.getSharedPreferences("com.iflytek.setting",
                Activity.MODE_PRIVATE);

    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            LogUtil.e(TAG, "初始化失败，错误码：" + code);
            if (code != ErrorCode.SUCCESS) {
                ToastUtil.showToast(mContext, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT);
            }
        }
    };

    private void initView() {
        DaggerFeedBackActivityComponent.builder()
                .appComponent(getAppComponent())
                .feedBackPresenterModule(new FeedBackPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData(String content) {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("CONTETNT", content);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S012");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void setResponseData(FeedBackResponse feedBackResponse) {
        this.feedBackResponse = feedBackResponse;
        try {
            String code = feedBackResponse.getCode();
            String msg = feedBackResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                ToastUtil.showToast(this.getApplicationContext(), "提交成功");
                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }


    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    @OnClick({R2.id.jkx_title_left, R2.id.iv_record, R2.id.tv_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();
        } else if (i == R.id.iv_record) {
            if (PermissionUtils.isGranted(PermissionConstants.MICROPHONE)) {
                startRecord();
            } else {
                requestPermission(START_RECORD, null, PermissionConstants.MICROPHONE);
            }
        } else if (i == R.id.tv_commit) {

            if (StringUtils.isEmpty(edContent.getText())) {
                ToastUtils.showLong("请填写反馈内容后体检");
                return;
            }
            if (StringUtils.length(edContent.getText()) > 1000) {
                ToastUtils.showLong("字数超过限制");
                return;
            }

            initData(edContent.getText().toString());
        }
    }

    public void startRecord() {
        startDialog();
    }

    public void startDialog() {
        buffer.setLength(0);
        edContent.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        //        setParam();

        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
    }

    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            if (mTranslateEnable) {
                printTransResult(results);
            } else {
                printResult(results);
            }

        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            if (mTranslateEnable && error.getErrorCode() == 14002) {
                ToastUtil.showToast(mContext, error.getPlainDescription(true) + "\n请确认是否已开通翻译功能", Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(mContext, error.getPlainDescription(true), Toast.LENGTH_SHORT);
            }
        }

    };

    private void printTransResult(RecognizerResult results) {
        String trans = myJsonParser.parseTransResult(results.getResultString(), "dst");
        String oris = myJsonParser.parseTransResult(results.getResultString(), "src");

        if (TextUtils.isEmpty(trans) || TextUtils.isEmpty(oris)) {
            ToastUtil.showToast(mContext, "解析结果失败，请确认是否已开通翻译功能。", Toast.LENGTH_SHORT);
        } else {
        }

    }

    private void printResult(RecognizerResult results) {
        String text = myJsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        edContent.setText(resultBuffer.toString());
        edContent.setSelection(edContent.length());
    }

    /**
     * 获取一组权限方法
     *
     * @param type        功能
     * @param permissions 申请的权限
     */
    public void requestPermission(final int type, final Bundle bundle, final @PermissionConstants.Permission String... permissions) {

        PermissionHelper.request(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                switch (type) {
                    case START_RECORD:
                        startRecord();
                        break;
                }
            }
        }, permissions);

    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        this.mTranslateEnable = mSharedPreferences.getBoolean("translate", false);
        if (mTranslateEnable) {
            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
        }

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if (mTranslateEnable) {
                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
            }
        }
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

}
