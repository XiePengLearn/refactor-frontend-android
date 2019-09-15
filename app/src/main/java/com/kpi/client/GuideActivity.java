package com.kpi.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sxjs.common.util.DensityUtils;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.composition.login.login.LoginActivity;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {




    protected static final String               TAG = GuideActivity.class.getSimpleName();
    private                ViewPager            mViewPager;
    private                ArrayList<ImageView> mImageList;// 引导页的ImageView集合
    private                LinearLayout         llPointGroup;// 点的集合
    private                View                 viewRedPoint;// 红点
    private                int                  mPointWidth; // 两点间距
    private                Button               btnStart;// 开始体验

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        initViews();
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_red_point);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        initData();
        mViewPager.setAdapter(new GuideAdapter());

        // measure -> layout -> draw
        // 获得视图树观察者, 观察当整个布局的layout时的事件
        viewRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // 完成布局后会回调改方法, 改方法可能会被回调多次
                    @Override
                    public void onGlobalLayout() {
                        // 此方法只需要执行一次就可以: 把当前的监听事件从视图树中移除掉, 以后就不会在回调此事件了.
                        viewRedPoint.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);

                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();

                        System.out.println("间距: " + mPointWidth);
                    }
                });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // 页面选中
            @Override
            public void onPageSelected(int position) {
                // Log.d(TAG, "onPageSelected:" + position);
                if (position == mImageList.size() - 1) {
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnStart.setVisibility(View.GONE);
                }
            }

            /**
             * 页面滑动监听
             *
             * @params position 当前选中的位置
             * @params positionOffset 偏移百分比
             * @params positionOffsetPixels 页面偏移长度
             */
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                int leftMargin = (int) (mPointWidth * (positionOffset + position));
                // Log.d(TAG, "当前位置:" + position + ";偏移比例:" + positionOffset
                // + ";点偏移:" + leftMargin);

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) viewRedPoint
                        .getLayoutParams();
                lp.leftMargin = leftMargin;
                viewRedPoint.setLayoutParams(lp);
            }

            // 状态改变
            @Override
            public void onPageScrollStateChanged(int state) {
                // Log.d(TAG, "onPageScrollStateChanged:" + state);
            }
        });
    }

    // 初始化ViewPager的数据
    private void initData() {
        int[] imageResIDs = {R.drawable.guide_1, R.drawable.guide_2,
                R.drawable.guide_3};

        mImageList = new ArrayList<ImageView>();
        for (int i = 0; i < imageResIDs.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(imageResIDs[i]);// 注意设置背景, 才可以填充屏幕
            mImageList.add(image);

            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_guide_point_default);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));

            if (i != 0) {
                params.leftMargin = DensityUtils.dp2px(this, 10);
            }

            point.setLayoutParams(params);
            llPointGroup.addView(point);
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageList.get(position));
            return mImageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                // 开始体验
                PrefUtils.putBoolean(this, SplashActivity.PREF_IS_USER_GUIDE_SHOWED, true);// 记录已经展现过了新手引导页
                PrefUtils.putBoolean(this, SplashActivity.PREF_IS_ENTER_LOG_OR_MAIN, false);
                // 记录已经展现过了新手引导页,为false就是判断进入主页面还是登录页面,为true就是进入登录界面

                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("title", "登录");
                startActivity(intent);

                finish();

                break;

            default:
                break;
        }
    }



}
