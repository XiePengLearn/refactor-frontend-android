package com.sxjs.jd.composition.kpimine.authentication;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class AuthenticationPresenterModule {
    private AuthenticationContract.View view;

    private MainDataManager mainDataManager;

    public AuthenticationPresenterModule(AuthenticationContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AuthenticationContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
