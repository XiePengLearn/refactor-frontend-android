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
public class NotificationFragmentModule {

    private NotificationFragmentContract.View view;

    private MainDataManager mainDataManager;

    public NotificationFragmentModule(NotificationFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    NotificationFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
