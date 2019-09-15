package com.sxjs.jd.composition.main.middle;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class MiddlePageModule {

    private MiddlePageContract.View view;

    private MainDataManager mainDataManager;

    public MiddlePageModule(MiddlePageContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MiddlePageContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
