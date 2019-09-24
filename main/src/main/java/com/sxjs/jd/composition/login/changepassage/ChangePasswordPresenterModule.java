package com.sxjs.jd.composition.login.changepassage;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:49
 * @Description:
 */
@Module
public class ChangePasswordPresenterModule {

    private ChangePasswordContract.View view;

    private MainDataManager mainDataManager;

    public ChangePasswordPresenterModule(ChangePasswordContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ChangePasswordContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
