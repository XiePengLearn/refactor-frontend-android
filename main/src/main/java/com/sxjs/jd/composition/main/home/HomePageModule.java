package com.sxjs.jd.composition.main.home;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class HomePageModule {

    private HomePageContract.View view;

    private MainDataManager mainDataManager;

    public HomePageModule(HomePageContract.View  view,MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    HomePageContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
