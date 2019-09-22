package com.sxjs.jd.composition.kpimine.change;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ChangeAuthenticationPresenterModule.class)
public interface ChangeAuthenticationActivityComponent {

    void inject(ChangeAuthenticationActivity activity);
}
