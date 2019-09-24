package com.sxjs.jd.composition.kpimine.feedback;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class FeedBackPresenterModule {
    private FeedBackContract.View view;

    private MainDataManager mainDataManager;

    public FeedBackPresenterModule(FeedBackContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    FeedBackContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
