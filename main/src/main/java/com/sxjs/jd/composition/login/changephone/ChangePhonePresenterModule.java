package com.sxjs.jd.composition.login.changephone;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:49
 * @Description:
 */
@Module
public class ChangePhonePresenterModule {

    private ChangePhoneContract.View view;

    private MainDataManager mainDataManager;

    public ChangePhonePresenterModule(ChangePhoneContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ChangePhoneContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
