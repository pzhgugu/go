package com.ansteel.shop.store.controller;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.shop.store.domain.StoreJoinin;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/8.
 */
@Controller
@RequestMapping(value = Public.ADMIN + "/storejoinin")
public class StoreJoininAdminController extends BaseController {
    @Override
    public Collection<EntityInfo> getEntityInfos() {
        Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
        EntityInfo storeJoinin = new EntityInfo();
        storeJoinin.setClazz(StoreJoinin.class);
        entityInfos.add(storeJoinin);
        return entityInfos;
    }

}
