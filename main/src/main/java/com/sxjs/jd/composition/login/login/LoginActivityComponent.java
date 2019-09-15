package com.sxjs.jd.composition.login.login;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = LoginPresenterModule.class)
public interface LoginActivityComponent {

    void inject(LoginActivity activity);
}
