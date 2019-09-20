package com.sxjs.jd.composition.nationexam.DetailsPage;

import com.sxjs.jd.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 20:18
 * @Description:
 */

@Module
public class NewsItemDetailsFragmentModule {

    private NewsItemDetailsFragmentContract.View view;

    private MainDataManager mainDataManager;

    public NewsItemDetailsFragmentModule(NewsItemDetailsFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    NewsItemDetailsFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
