package com.sxjs.jd.composition.main.unused.quicklyfragment;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class QuicklyFragmentModule {

    private QuicklyFragmentContract.View view;

    private MainDataManager mainDataManager;

    public QuicklyFragmentModule(QuicklyFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    QuicklyFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
