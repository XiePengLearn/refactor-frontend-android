package com.sxjs.jd.composition.login.register;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:40
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = RegisterPresenterModule.class)
public interface RegisterActivityComponent {
    void inject(RegisterActivity activity);
}
