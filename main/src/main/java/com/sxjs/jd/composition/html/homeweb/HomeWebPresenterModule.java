package com.sxjs.jd.composition.html.homeweb;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class HomeWebPresenterModule {
    private HomeWebContract.View view;

    private MainDataManager mainDataManager;

    public HomeWebPresenterModule(HomeWebContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    HomeWebContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
