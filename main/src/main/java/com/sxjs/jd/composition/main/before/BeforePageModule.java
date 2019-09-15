package com.sxjs.jd.composition.main.before;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class BeforePageModule {

    private BeforePageContract.View view;

    private MainDataManager mainDataManager;

    public BeforePageModule(BeforePageContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    BeforePageContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
