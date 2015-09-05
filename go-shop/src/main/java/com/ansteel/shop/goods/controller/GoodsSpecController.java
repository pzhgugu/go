package com.ansteel.shop.goods.controller;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsSpec;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015/9/1.
 */
@Controller
@RequestMapping(value = "/goodsscpec")
public class GoodsSpecController  extends BaseController {
    @Override
    public Collection<EntityInfo> getEntityInfos() {
        Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
        EntityInfo goodsSpec = new EntityInfo();
        goodsSpec.setClazz(GoodsSpec.class);
        entityInfos.add(goodsSpec);
        return entityInfos;
    }
}
