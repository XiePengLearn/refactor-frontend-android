package com.example.app_common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @Auther: xp
 * @Date: 2019/9/13 18:19
 * @Description:
 */
public interface ITokenService extends IProvider {

    /**
     *
     * @return 从App 模块获取信鸽Token
     */
    String getToken();
}
