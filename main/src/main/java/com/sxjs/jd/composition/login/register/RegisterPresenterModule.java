package com.sxjs.jd.composition.login.register;

import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.login.login.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:41
 * @Description:
 */

@Module
public class RegisterPresenterModule {
    private RegisterContract.View view;

    private MainDataManager mainDataManager;

    public RegisterPresenterModule(RegisterContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    RegisterContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
