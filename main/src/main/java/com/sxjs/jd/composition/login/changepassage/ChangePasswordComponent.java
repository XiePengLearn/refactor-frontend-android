package com.sxjs.jd.composition.login.changepassage;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:48
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ChangePasswordPresenterModule.class)
public interface ChangePasswordComponent {

    void inject(ChangePasswordActivity activity);
}
