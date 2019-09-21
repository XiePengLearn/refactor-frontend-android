package com.sxjs.jd.composition.kpimine.authentication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.constant.Constant;
import com.sxjs.common.util.PermissionHelper;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.view.FlowLayout;
import com.sxjs.common.view.SelectPicPopupWindow;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.UserAuthenticationResponse;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 * @Description: 用户认证模块
 */
@Route(path = "/authenticationActivity/authenticationActivity")
public class AuthenticationActivity extends BaseActivity implements AuthenticationContract.View {
    @Inject
    AuthenticationPresenter mPresenter;
    @BindView(R2.id.fake_status_bar)
    View                    fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView                jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button                  jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView                jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView                jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView                newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout          rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView                jkxTitleRight;
    @BindView(R2.id.et_name)
    EditText                etName;
    @BindView(R2.id.tv_sex)
    TextView                tvSex;
    @BindView(R2.id.et_hospitalName)
    EditText                etHospitalName;
    @BindView(R2.id.et_department)
    EditText                etDepartment;
    @BindView(R2.id.et_resign)
    EditText                etResign;
    @BindView(R2.id.et_workTel)
    EditText                etWorkTel;
    @BindView(R2.id.qr_name)
    TextView                qrName;
    @BindView(R2.id.gridview)
    GridView                gridview;
    @BindView(R2.id.flowlayout)
    FlowLayout              flowlayout;
    @BindView(R2.id.tv_commit)
    TextView                tvCommit;


    private static final String TAG = "AuthenticationActivity";
    @BindView(R2.id.ll_user_authentication)
    LinearLayout llUserAuthentication;
    private String                     mSession_id;
    private Intent                     mIntent;
    private String                     mStringExtra;
    private UserAuthenticationResponse userAuthenticationResponse;
    private String                     selectedQrID  = "110000";//选择之后的最低级区划id
    private List<LocalMedia>           selectList;
    private List<LocalMedia>           selectListTemp;    //该数组是为存储选中的图片而临时存在
    private int                        SelectedIndex = 0;    //标识当前选中的是哪个证件

    public static final int TAKE_ALBUM   = 16; // 相册
    public static final int TAKE_PICTURE = 3; // 拍照

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mIntent = getIntent();
        mStringExtra = mIntent.getStringExtra(Constant.CONTENT_TITLE);
        initTitle();
        initView();
        initData();
    }

    private void initView() {
        DaggerAuthenticationActivityComponent.builder()
                .appComponent(getAppComponent())
                .authenticationPresenterModule(new AuthenticationPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        selectList = new ArrayList<LocalMedia>();
        selectListTemp = new ArrayList<LocalMedia>();
        for (int i = 0; i < 4; i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setCompressPath("");
            selectListTemp.add(localMedia);
        }

        flowlayout.removeAllViews();
        for (int i = 0; i < 4; i++) {

            View selected_img = View.inflate(mContext, R.layout.jkx_author_item, null);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flowlayout.addView(selected_img, lp);

            ImageView src_img = selected_img.findViewById(R.id.src_img);
            RelativeLayout dest_img_layout = selected_img.findViewById(R.id.dest_img_layout);
            ImageView dest_img = selected_img.findViewById(R.id.dest_img);
            ImageView close = selected_img.findViewById(R.id.close);

            final int finalI = i;
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flowlayout.getChildAt(finalI).findViewById(R.id.src_img).setVisibility(View.VISIBLE);
                    flowlayout.getChildAt(finalI).findViewById(R.id.dest_img_layout).setVisibility(View.GONE);
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressPath("");
                    selectListTemp.set(finalI, localMedia);
                }
            });


            src_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedIndex = finalI;
                    SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llUserAuthentication);
                    selectPicPopupWindow.setData("拍摄", "从手机相册选择", null);
                    selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                        @Override
                        public void selectItem(String str) {

                            if ("拍摄".equals(str)) {
                                if (PermissionUtils.isGranted(PermissionConstants.CAMERA, PermissionConstants.STORAGE)) {
                                    photoSelection(true, 2, TAKE_PICTURE, selectList);
                                } else {
                                    requestPermission(TAKE_PICTURE, selectList, PermissionConstants.CAMERA, PermissionConstants.STORAGE);
                                }
                            } else if ("从手机相册选择".equals(str)) {
                                if (PermissionUtils.isGranted(PermissionConstants.CAMERA, PermissionConstants.STORAGE)) {
                                    photoSelection(false, 1, TAKE_ALBUM, selectList);
                                } else {
                                    requestPermission(TAKE_ALBUM, selectList, PermissionConstants.CAMERA, PermissionConstants.STORAGE);
                                }
                            }
                        }
                    });
                    selectPicPopupWindow.showPopWindow();
                }
            });
            dest_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureSelector.create(AuthenticationActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(finalI, selectListTemp);
                }
            });
            if (i == 0) {
                src_img.setVisibility(View.VISIBLE);
                dest_img_layout.setVisibility(View.GONE);
                src_img.setBackgroundResource(R.drawable.sf_zm);

            } else if (i == 1) {
                src_img.setVisibility(View.VISIBLE);
                dest_img_layout.setVisibility(View.GONE);
                src_img.setBackgroundResource(R.drawable.sf_fm);
            } else if (i == 2) {
                src_img.setVisibility(View.VISIBLE);
                dest_img_layout.setVisibility(View.GONE);
                src_img.setBackgroundResource(R.drawable.zyys);
            } else {
                src_img.setVisibility(View.VISIBLE);
                dest_img_layout.setVisibility(View.GONE);
                src_img.setBackgroundResource(R.drawable.gzz);
            }

        }
    }


    private void initData() {

        mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S010");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //返回按钮
        jkxTitleLeft.setVisibility(View.VISIBLE);
        //标题
        jkxTitleCenter.setText(mStringExtra);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(UserAuthenticationResponse userAuthenticationResponse) {
        this.userAuthenticationResponse = userAuthenticationResponse;
        try {
            String code = userAuthenticationResponse.getCode();
            String msg = userAuthenticationResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                setUserAuthentication(userAuthenticationResponse);


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

    public void setUserAuthentication(UserAuthenticationResponse userAuthenticationResponse) {
        UserAuthenticationResponse.DataBean data = userAuthenticationResponse.getData();

        if (data != null) {
            //姓名
            String name = data.getNAME();
            if (!TextUtils.isEmpty(name)) {
                etName.setText(name);
            }
            //性别
            String sex = data.getSEX();
            if (!TextUtils.isEmpty(sex)) {
                if ("1".equals(sex)) {
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");

                }
            }
            //医院名称
            String hospital_name = data.getHOSPITAL_NAME();
            if (!TextUtils.isEmpty(hospital_name)) {
                etHospitalName.setText(hospital_name);
            }
            //科室
            String department = data.getDEPARTMENT();
            if (!TextUtils.isEmpty(department)) {

                etDepartment.setText(department);
            }
            //职务
            String resign = data.getRESIGN();
            if (!TextUtils.isEmpty(resign)) {
                etResign.setText(resign);
            }


            //省市
            String provincial_code = data.getPROVINCIAL_CODE();

            if (!TextUtils.isEmpty(provincial_code)) {
                String[] areaArray = getResources().getStringArray(R.array.area_name_array);
                String ar_name = "";
                for (int i = 0; i < areaArray.length; i++) {
                    String[] value = areaArray[i].split("_");
                    if (provincial_code.equals(value[1])) {
                        selectedQrID = value[1];
                        ar_name = value[0];
                        break;
                    }
                }
                qrName.setText(ar_name);
            }


            //单位电话
            String work_tel = data.getWORK_TEL();
            if (!TextUtils.isEmpty(work_tel)) {
                etWorkTel.setText(work_tel);
            }
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

    @OnClick({R2.id.jkx_title_left, R2.id.tv_sex, R2.id.qr_name, R2.id.tv_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {


        } else if (i == R.id.tv_sex) {

            //性别
            SelectPicPopupWindow popupWindow = new SelectPicPopupWindow(mContext, llUserAuthentication);
            popupWindow.setData("男", "女", null);
            popupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {
                    tvSex.setText(str);
                }
            });
            popupWindow.showPopWindow();
        } else if (i == R.id.qr_name) {

            //省市


        } else if (i == R.id.tv_commit) {

            commitToServer();
        }
    }

    private void commitToServer() {
        if ("".equals(etName.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入姓名", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(etHospitalName.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入医院", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(etResign.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入职务", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(etDepartment.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入科室", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(etWorkTel.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入单位电话", Toast.LENGTH_SHORT);
            return;
        }
        if ("".equals(selectedQrID)) {
            ToastUtil.showToast(mContext, "请选择地区", Toast.LENGTH_SHORT);
            return;
        }
        boolean picInfoFinish = true;
        for (int i = 0; i < 4; i++) {
            if (flowlayout.getChildAt(i).findViewById(R.id.dest_img_layout).getVisibility() != View.VISIBLE) {
                picInfoFinish = false;
                ToastUtil.showToast(mContext, "请将证件信息上传完整", Toast.LENGTH_SHORT);
                break;
            }
        }
        if (!picInfoFinish) {
            return;
        }


    }


    public void photoSelection(boolean isCamera, int maxSelect, int resquestCode, List<LocalMedia> selectList) {
        if (isCamera) {
            // 单独拍照
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelect)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .previewImage(true)// 是否可预览图片
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    //                    .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                    //                    .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    //                    .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                    //                    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                    //                    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    //                    .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    //                    .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .cropCompressQuality(50)// 裁剪压缩质量 默认为100
                    .minimumCompressSize(500)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled() // 裁剪是否可旋转图片
                    //.scaleEnabled()// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()////显示多少秒以内的视频or音频也可适用
                    .forResult(resquestCode);//结果回调onActivityResult code
        } else {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(maxSelect)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    //                .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
                    //                .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                    .isCamera(false)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    //                .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    //                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                    //                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    //                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    //                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    //                        .videoMaxSecond(15)
                    //                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .cropCompressQuality(50)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(resquestCode);//结果回调onActivityResult code
        }


    }

    /**
     * 获取一组权限方法
     *
     * @param type        功能
     * @param permissions 申请的权限
     */
    public void requestPermission(final int type, final List<LocalMedia> listLocalMedia, final @PermissionConstants.Permission String... permissions) {
        //        final Fragment lFragment = mManager
        //                .mCurrentFragment(CustomFragmentManager.CONTENT);
        PermissionHelper.request(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                switch (type) {
                    case TAKE_ALBUM:
                        photoSelection(false, 1, type, listLocalMedia);
                        break;
                    case TAKE_PICTURE:
                        //                        Intent openCameraIntent = new Intent(
                        ////                                MediaStore.ACTION_IMAGE_CAPTURE);
                        ////                        openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        ////                        Uri uri = FileProvider7.getUriForFile(JkxContentActivity.this, new File(cameraPath));
                        ////                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        ////                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        photoSelection(true, 1, type, listLocalMedia);
                        break;
                    //                    case START_RECORD:
                    //                        ((JkxMyFeedBackFragment) lFragment).startRecord();
                    //                        break;
                }
            }
        }, permissions);

    }

    public void setImage(List<LocalMedia> uriList) {
        selectList = uriList;

        flowlayout.getChildAt(SelectedIndex).findViewById(R.id.dest_img_layout).setVisibility(View.VISIBLE);
        flowlayout.getChildAt(SelectedIndex).findViewById(R.id.src_img).setVisibility(View.GONE);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.home_pic1);
        Glide.with(mContext)
                .load(selectList.get(0).getCompressPath())
                .apply(options)
                .into(((ImageView) flowlayout.getChildAt(SelectedIndex).findViewById(R.id.dest_img)));
        selectListTemp.set(SelectedIndex, selectList.get(0));
        selectList.clear();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case TAKE_ALBUM:
                if (resultCode != RESULT_OK) {
                    return;
                }
                List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                setImage(list);

                break;
            case TAKE_PICTURE:

                if (resultCode != RESULT_OK) {
                    return;
                }
                List<LocalMedia> list1 = PictureSelector.obtainMultipleResult(data);
                setImage(list1);
                break;

        }
    }


}

