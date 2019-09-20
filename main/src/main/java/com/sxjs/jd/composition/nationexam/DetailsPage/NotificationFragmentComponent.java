package com.sxjs.jd.composition.nationexam.DetailsPage;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:15
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = NotificationFragmentModule.class)
public interface NotificationFragmentComponent {
    void inject(NotificationFragment fragment);
}
