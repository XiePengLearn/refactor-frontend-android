package com.sxjs.jd.composition.main.unused.quicklyactivity;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = QuicklyPresenterModule.class)
public interface QuicklyActivityComponent {

    void inject(QuicklyActivity activity);
}
