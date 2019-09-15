package com.kpi.client.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.app_common.service.ITokenService;
import com.kpi.client.MyApplication;
import com.sxjs.common.util.PrefUtils;

/**
 * @Auther: xp
 * @Date: 2019/9/13 18:21
 * @Description:
 *
 * 在其他模块获取 信鸽Token的Service
 */

@Route(path = "/TokenService/TokenService", name = "获取Token服务")
public class TokenService implements ITokenService {
    @Override
    public String getToken() {

        return PrefUtils.readXinGeToken(MyApplication.getContext());
    }

    @Override
    public void init(Context context) {

    }
}
