package com.ansteel.shop.core.service;

import com.ansteel.core.exception.PageException;
import com.ansteel.shop.core.domain.Setting;
import com.ansteel.shop.core.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */
@Service
public class SettingGoodsVerifyServiceImpl implements SettingGoodsVerifyService {

    private static String GOODS_VERIFY_NAME="goods_verify";

    private static String GOODS_VERIFY_DEFAULT="1";

    @Autowired
    SettingRepository settingRepository;

    @Override
    public Integer findByGoodsVerify() {
        List<Setting> settingList=settingRepository.findByName(GOODS_VERIFY_NAME);
        Setting setting=null;
        if(settingList!=null&&settingList.size()>0){
            setting=settingList.get(0);
        }
        setting=this.setDefaultGoodsVerify();
        String value= setting.getValue();
        try {
            Integer vi = Integer.valueOf(value);
            return vi;
        }catch (Exception e){
            throw new PageException("商品审核设置错误！");
        }
    }

    @Override
    @Transactional
    public Setting setDefaultGoodsVerify() {
        Setting setting = new Setting();
        setting.setName(GOODS_VERIFY_NAME);
        setting.setValue(GOODS_VERIFY_DEFAULT);
        return settingRepository.save(setting);
    }
}
