package com.sxjs.jd.composition.login.changephone;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:48
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ChangePhonePresenterModule.class)
public interface ChangePhoneComponent {

    void inject(ChangePhoneActivity activity);
}
