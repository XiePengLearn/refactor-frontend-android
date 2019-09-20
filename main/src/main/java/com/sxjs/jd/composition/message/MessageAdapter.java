package com.sxjs.jd.composition.message;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ylwx on 2018/8/5.
 */

public class MessageAdapter extends FragmentPagerAdapter {


    private List<Fragment> list;
    private String[]       title;

    public MessageAdapter(FragmentManager fm, List<Fragment> list, String[] title) {
        super(fm);
        this.list = list;
        this.title = title;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    /**
     * 注意：ViewPager默认只保留当前视图的前后各一个视图，其他的视图会被销毁。
     * 如果不想让视图被销毁要重写FragmentPagerAdapter的destroyItem方法，并注释掉原本的代码
     *
     * @param container 1
     * @param position  1
     * @param object    1
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
    }

}
