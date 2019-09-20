package com.sxjs.jd.composition.nationexam;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class NationExamPresenterModule {
    private NationExamContract.View view;

    private MainDataManager mainDataManager;

    public NationExamPresenterModule(NationExamContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    NationExamContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
