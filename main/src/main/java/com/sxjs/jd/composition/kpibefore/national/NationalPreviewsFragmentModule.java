package com.sxjs.jd.composition.kpibefore.national;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class NationalPreviewsFragmentModule {

    private NationalPreviewsFragmentContract.View view;

    private MainDataManager mainDataManager;

    public NationalPreviewsFragmentModule(NationalPreviewsFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    NationalPreviewsFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
