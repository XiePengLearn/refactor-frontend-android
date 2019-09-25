package com.sxjs.jd.composition.kpihome.schedule;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class ExamSchedulePresenterModule {
    private ExamScheduleContract.View view;

    private MainDataManager mainDataManager;

    public ExamSchedulePresenterModule(ExamScheduleContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ExamScheduleContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
