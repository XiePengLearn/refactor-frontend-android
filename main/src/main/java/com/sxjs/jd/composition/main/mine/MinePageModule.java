package com.sxjs.jd.composition.main.mine;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class MinePageModule {

    private MinePageContract.View view;

    private MainDataManager mainDataManager;

    public MinePageModule(MinePageContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MinePageContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
