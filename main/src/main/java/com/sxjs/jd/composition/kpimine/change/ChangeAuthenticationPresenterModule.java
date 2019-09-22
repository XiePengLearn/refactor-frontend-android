package com.sxjs.jd.composition.kpimine.change;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class ChangeAuthenticationPresenterModule {
    private ChangeAuthenticationContract.View view;

    private MainDataManager mainDataManager;

    public ChangeAuthenticationPresenterModule(ChangeAuthenticationContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ChangeAuthenticationContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
