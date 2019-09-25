package com.sxjs.jd.composition.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.widget.bottomnavigation.BadgeItem;
import com.sxjs.common.widget.bottomnavigation.BottomNavigationBar;
import com.sxjs.common.widget.bottomnavigation.BottomNavigationItem;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.main.befor.BeforePageFragment;
import com.sxjs.jd.composition.main.home.HomePageFragment;
import com.sxjs.jd.composition.main.middle.MiddlePageFragment;
import com.sxjs.jd.composition.main.mine.MinePageFragment;
import com.sxjs.jd.entities.LoginResponse;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity implements MainContract.View, BottomNavigationBar.OnTabSelectedListener {

    @Inject
    MainPresenter       presenter;
    @BindView(R2.id.bottom_navigation_bar1)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R2.id.main_container)
    FrameLayout         mainContainer;
    //    private MainHomeFragment       mMainHomeFragment;
    private       HomePageFragment   mMainHomeFragment;
    //    private ClassificationFragment mClassificationFragment;
    private       MiddlePageFragment mClassificationFragment;
    private       FragmentManager    mFragmentManager;
    //    private FindFragment       mFindFragment;
    private       BeforePageFragment mFindFragment;
    //    private MyFragment             mMyFragment;
    private       MinePageFragment   mMyFragment;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity       instance;//关闭当前页面的instance
    private final String             MESSAGE_ACTION = "com.jkx.message"; // 消息通知的广播名称


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initView();
        initData();
        //在oncreate中添加
        instance = this;
        registerMessageBroadcast();
    }

    /**
     * 注册消息广播
     */
    private void registerMessageBroadcast() {
        IntentFilter filter = new IntentFilter(MESSAGE_ACTION);
        registerReceiver(mSystemMessageReceiver, filter);// 注册广播
    }

    private BroadcastReceiver mSystemMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MESSAGE_ACTION.equals(action)) {
                initMoreState();
            }
        }

    };

    private void initMoreState() {

        LogUtil.e(TAG, "-----MainActivity收到信鸽的服务推送消息-----");
        //初始化首页数据
        initLogin();
    }

    private void initLogin() {
        String lAccount = PrefUtils.readUserNameDefault(this.getApplicationContext());
        String lPassword = PrefUtils.readPasswordDefault(this.getApplicationContext());

        String mXinGeToken = PrefUtils.readXinGeToken(this.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("MOBILE", lAccount);
        mapParameters.put("PASSWORD", lPassword);
        mapParameters.put("SIGNIN_TYPE", "1");
        mapParameters.put("USER_TYPE", "1");
        mapParameters.put("MOBILE_TYPE", "1");
        mapParameters.put("XINGE_TOKEN", mXinGeToken);
        LogUtil.e(TAG, "-------mXinGeToken-------" + mXinGeToken);

        Map<String, String> mapHeaders = new HashMap<>(1);
        mapHeaders.put("ACTION", "S002");
        //        mapHeaders.put("SESSION_ID", TaskManager.SESSION_ID);

        presenter.getLoginData(mapHeaders, mapParameters);
    }


    public void initView() {

        mMainHomeFragment = (HomePageFragment) mFragmentManager.findFragmentByTag("home_fg");
        mClassificationFragment = (MiddlePageFragment) mFragmentManager.findFragmentByTag("class_fg");
        mFindFragment = (BeforePageFragment) mFragmentManager.findFragmentByTag("find_fg");
        mMyFragment = (MinePageFragment) mFragmentManager.findFragmentByTag("my_fg");


        if (mMainHomeFragment == null) {
            mMainHomeFragment = HomePageFragment.newInstance();
            addFragment(R.id.main_container, mMainHomeFragment, "home_fg");
        }
        //更改位置,初始化在  点击底部导航栏 position 为1  初始化
        /*if(mClassificationFragment == null){
            mClassificationFragment = ClassificationFragment.newInstance();
            addFragment(R.id.main_container, mClassificationFragment, "class_fg");
        }*/

        //更改位置,初始化在  点击底部导航栏 position 为2  初始化
        /*if(mFindFragment == null){
            mFindFragment = FindFragment.newInstance();
            addFragment(R.id.main_container, mFindFragment, "find_fg");
        }*/

        //更改位置,初始化在  点击底部导航栏 position 为3  初始化
        /*if(mMyFragment == null){
            mMyFragment = MyFragment.newInstance();
            addFragment(R.id.main_container, mMyFragment, "my_fg");
        }*/

        mFragmentManager.beginTransaction().show(mMainHomeFragment)
                .commitAllowingStateLoss();
        hideClassificationFragmnt();
        hideFindFragment();
        hideMyFragment();

        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainPresenterModule(new MainPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        initBottomNavigation();


    }

    @Override
    public void onTabSelected(int position) {
        if (position == 0) {
            if (mMainHomeFragment == null) {
                mMainHomeFragment = HomePageFragment.newInstance();
                addFragment(R.id.main_container, mMainHomeFragment, "home_fg");
            }

            hideClassificationFragmnt();
            hideFindFragment();
            hideMyFragment();

            mFragmentManager.beginTransaction()

                    .show(mMainHomeFragment)
                    .commitAllowingStateLoss();


        } else if (position == 1) {
            if (mClassificationFragment == null) {
                mClassificationFragment = MiddlePageFragment.newInstance();
                addFragment(R.id.main_container, mClassificationFragment, "class_fg");
            }

            hideHomeFragment();

            hideFindFragment();
            hideMyFragment();


            mFragmentManager.beginTransaction()
                    .show(mClassificationFragment)
                    .commitAllowingStateLoss();


        } else if (position == 2) {
            if (mFindFragment == null) {
                mFindFragment = BeforePageFragment.newInstance();
                addFragment(R.id.main_container, mFindFragment, "find_fg");
            }


            hideHomeFragment();
            hideClassificationFragmnt();

            hideMyFragment();


            mFragmentManager.beginTransaction()
                    .show(mFindFragment)
                    .commitAllowingStateLoss();


        } else if (position == 3) {

            if (mMyFragment == null) {
                mMyFragment = MinePageFragment.newInstance();
                addFragment(R.id.main_container, mMyFragment, "my_fg");
            }

            hideHomeFragment();
            hideClassificationFragmnt();
            hideFindFragment();

            mFragmentManager.beginTransaction()
                    .show(mMyFragment)
                    .commitAllowingStateLoss();


        }
    }

    private void hideHomeFragment() {
        if (mMainHomeFragment != null) {
            mFragmentManager.beginTransaction().hide(mMainHomeFragment).commitAllowingStateLoss();
        }
    }

    private void hideMyFragment() {
        if (mMyFragment != null) {
            mFragmentManager.beginTransaction().hide(mMyFragment).commitAllowingStateLoss();
        }
    }

    private void hideFindFragment() {
        if (mFindFragment != null) {
            mFragmentManager.beginTransaction().hide(mFindFragment).commitAllowingStateLoss();
        }
    }

    private void hideClassificationFragmnt() {
        if (mClassificationFragment != null) {
            mFragmentManager.beginTransaction().hide(mClassificationFragment).commitAllowingStateLoss();
        }
    }

    private void initBottomNavigation() {
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("99+")
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomNavigationBar.setAutoHideEnabled(true);


        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.axh, "").setInactiveIconResource(R.drawable.axg).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.axd, "").setInactiveIconResource(R.drawable.axc).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.axf, "").setInactiveIconResource(R.drawable.axe).setActiveColorResource(R.color.colorAccent))
                //                .addItem(new BottomNavigationItem(R.drawable.axb, "").setInactiveIconResource(R.drawable.axa).setActiveColorResource(R.color.colorAccent).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.axj, "").setInactiveIconResource(R.drawable.axi).setActiveColorResource(R.color.colorAccent))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    private static final String TAG = "MainActivity";


    public void initData() {
        //        presenter.getText();
    }

    private String text;
    private String loginData;


    @Override
    public void setLoginData(LoginResponse loginResponse) {
        try {
            String code = loginResponse.getCode();
            String msg = loginResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "----------用户默认登录了-------------: ");

                String SESSION_ID = loginResponse.getData();
                PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面

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
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
        }

        if (mSystemMessageReceiver != null) {
            unregisterReceiver(mSystemMessageReceiver);
        }

    }

    private long timeMillis;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timeMillis = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
