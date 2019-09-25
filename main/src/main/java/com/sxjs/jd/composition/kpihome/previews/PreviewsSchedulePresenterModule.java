package com.sxjs.jd.composition.kpihome.previews;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class PreviewsSchedulePresenterModule {
    private PreviewsScheduleContract.View view;

    private MainDataManager mainDataManager;

    public PreviewsSchedulePresenterModule(PreviewsScheduleContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    PreviewsScheduleContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
