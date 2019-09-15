package com.sxjs.jd.composition.main.mine;

import com.sxjs.common.AppComponent;
import com.sxjs.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:15
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MinePageModule.class)
public interface MinePageComponent {
    void inject(MinePageFragment fragment);
}
