package com.ansteel.shop.core.controller;

import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.core.domain.Setting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015/12/14.
 */

@Controller
@RequestMapping(value = Public.ADMIN + "/setting")
public class SettingController extends BaseController {
    @Override
    public Collection<EntityInfo> getEntityInfos() {
        Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
        EntityInfo entity = new EntityInfo();
        entity.setClazz(Setting.class);
        entityInfos.add(entity);
        return entityInfos;
    }
}
