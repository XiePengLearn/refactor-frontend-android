package com.sxjs.jd.composition.login.forget;

import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.composition.login.register.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:49
 * @Description:
 */
@Module
public class ForgetPasswordPresenterModule {

    private ForgetPasswordContract.View view;

    private MainDataManager mainDataManager;

    public ForgetPasswordPresenterModule(ForgetPasswordContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ForgetPasswordContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
