package com.sxjs.jd.composition.kpibefore.quality;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class MedicalQualityFragmentModule {

    private MedicalQualityFragmentContract.View view;

    private MainDataManager mainDataManager;

    public MedicalQualityFragmentModule(MedicalQualityFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MedicalQualityFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
