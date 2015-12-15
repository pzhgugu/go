package com.ansteel.shop.core.service;

/**
 * Created by Administrator on 2015/12/14.
 */

import com.ansteel.shop.core.domain.Setting;

/**
 * 设置商品审核
 */
public interface SettingGoodsVerifyService {

    Integer findByGoodsVerify();

    Setting setDefaultGoodsVerify();
}
