package com.sxjs.jd.composition.kpihome.abnormal;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class AbnormalPresenterModule {
    private AbnormalContract.View view;

    private MainDataManager mainDataManager;

    public AbnormalPresenterModule(AbnormalContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AbnormalContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
