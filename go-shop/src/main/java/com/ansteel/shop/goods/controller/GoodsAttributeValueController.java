package com.ansteel.shop.goods.controller;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.DaoException;
import com.ansteel.core.query.PageUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.dhtmlx.xml.GridXml;
import com.ansteel.shop.goods.domain.GoodsAttributeValue;
import com.ansteel.shop.goods.domain.GoodsBrand;
import com.ansteel.shop.goods.service.GoodsAttributeValueService;
import com.ansteel.shop.goods.service.GoodsSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
@Controller
@RequestMapping(Public.ADMIN + "/goodsattributevalue")
public class GoodsAttributeValueController {

    public Collection<EntityInfo> getEntityInfos() {
        Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
        EntityInfo entity = new EntityInfo();
        entity.setClazz(GoodsAttributeValue.class);
        entityInfos.add(entity);

        return entityInfos;
    }

    @Autowired
    GoodsAttributeValueService goodsAttributeValueService;

    public
    @ResponseBody
    @RequestMapping("/load")
    UDataSet loadPageAjax(@RequestParam(value = "attId", required = false) String attId,
                          @RequestParam(value = "posStart", required = false) String posStart,//分页当前记录行
                          @RequestParam(value = "count", required = false) String count,//分页记录行
                          @RequestParam(value = "_order", required = false) String order,//排序字段名
                          HttpServletRequest request,
                          HttpServletResponse response) {
        Pageable pageable = null;
        // if (StringUtils.hasText(posStart)) {
        Sort sort = new Sort("sort");
        pageable = new PageRequest(PageUtils.getTotalPages(posStart), PageUtils.getMaxResults(), sort);
        // }
        Page page = goodsAttributeValueService.findByGoodsAttribute_Id(attId, pageable);
        return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
    }

    @RequestMapping("/updateAll/{className}")
    @ResponseBody
    public Data updateAllAjax(@PathGridData("className") List<BaseEntity> baseEntityLsit,
                              @RequestParam(value = "_key", required = false) String key,
                              @RequestParam(value = "_value") String value,
                              HttpServletRequest request) {
        Data data = new Data();
        if (baseEntityLsit.size() > 0) {
            for (BaseEntity baseEntity : baseEntityLsit) {
                GridXml gridXml = new GridXml();
                String id = baseEntity.getId();
                if (id.lastIndexOf("inserted_") == 0) {
                    gridXml.setType("inserted");
                    baseEntity.setId(null);
                    id = id.replaceFirst("inserted_", "");
                    gridXml.setTid(id);
                    gridXml.setSid(id);
                } else {
                    gridXml.setType("update");
                    gridXml.setTid(baseEntity.getId());
                    gridXml.setSid(baseEntity.getId());
                }
                try {
                    goodsAttributeValueService.save((GoodsAttributeValue) baseEntity, value);
                } catch (Exception e) {
                    throw new DaoException(e.getMessage());
                }


                data.addAction(gridXml);
            }
        }


        goodsAttributeValueService.updateGoodsAttributeByAttrValue(value);
        return data;
    }

    @RequestMapping("/del")
    @ResponseBody
    public void delectAjax(@RequestParam("id") String id,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        goodsAttributeValueService.delete(id);
    }
}
