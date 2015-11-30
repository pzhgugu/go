package com.ansteel.shop.goods.controller;

import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015/10/26.
 */
@Controller
@RequestMapping(value = Public.ADMIN + "/goodsattribute")
public class GoodsAttributeController extends BaseController {

    @Override
    public Collection<EntityInfo> getEntityInfos() {

        Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
        EntityInfo entity = new EntityInfo();
        entity.setClazz(GoodsAttribute.class);
        entityInfos.add(entity);

        return entityInfos;
    }
}
