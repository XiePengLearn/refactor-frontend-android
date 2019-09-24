package com.sxjs.jd.composition.kpibefore.indicators;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class IndicatorsMonitorFragmentModule {

    private IndicatorsMonitorFragmentContract.View view;

    private MainDataManager mainDataManager;

    public IndicatorsMonitorFragmentModule(IndicatorsMonitorFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    IndicatorsMonitorFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
