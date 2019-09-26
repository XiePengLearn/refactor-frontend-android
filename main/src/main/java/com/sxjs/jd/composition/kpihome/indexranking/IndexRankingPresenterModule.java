package com.sxjs.jd.composition.kpihome.indexranking;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class IndexRankingPresenterModule {
    private IndexRankingContract.View view;

    private MainDataManager mainDataManager;

    public IndexRankingPresenterModule(IndexRankingContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    IndexRankingContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
