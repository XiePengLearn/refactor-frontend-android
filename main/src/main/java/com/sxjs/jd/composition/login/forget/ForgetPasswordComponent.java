package com.sxjs.jd.composition.login.forget;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;
import com.sxjs.jd.composition.login.register.RegisterActivity;
import com.sxjs.jd.composition.login.register.RegisterPresenterModule;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:48
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ForgetPasswordPresenterModule.class)
public interface ForgetPasswordComponent {

    void inject(ForgetPasswordActivity activity);
}
