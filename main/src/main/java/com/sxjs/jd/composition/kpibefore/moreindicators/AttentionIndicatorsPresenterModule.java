package com.sxjs.jd.composition.kpibefore.moreindicators;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class AttentionIndicatorsPresenterModule {
    private AttentionIndicatorsContract.View view;

    private MainDataManager mainDataManager;

    public AttentionIndicatorsPresenterModule(AttentionIndicatorsContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AttentionIndicatorsContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
