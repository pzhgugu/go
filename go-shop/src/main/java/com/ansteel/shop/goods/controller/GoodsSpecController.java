package com.ansteel.shop.goods.controller;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.shop.goods.domain.GoodsSpec;
import com.ansteel.shop.goods.service.GoodsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
@Controller
@RequestMapping(value = Public.ADMIN + "/goodsscpec")
public class GoodsSpecController extends BaseController implements SaveBefore {

    @Autowired
    GoodsSpecService goodsSpecService;

    @Override
    public Collection<EntityInfo> getEntityInfos() {
        Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();
        EntityInfo goodsSpec = new EntityInfo();
        goodsSpec.setClazz(GoodsSpec.class);
        entityInfos.add(goodsSpec);
        return entityInfos;
    }

    public
    @ResponseBody
    UDataSet loadPageAjax(@PathClass("className") Class clazz,
                          @RequestParam(value = "_key", required = false) String key,//过滤字段名（一般用于主从表）
                          @RequestParam(value = "_value", required = false) String value,//过滤字段值（一般用于主从表）
                          @RequestParam(value = "posStart", required = false) String posStart,//分页当前记录行
                          @RequestParam(value = "count", required = false) String count,//分页记录行
                          @RequestParam(value = "_order", required = false) String order,//排序字段名
                          HttpServletRequest request,
                          HttpServletResponse response) {
        Pageable pageable = null;
        if (StringUtils.hasText(posStart)) {
            pageable = new PageRequest(PageUtils.getTotalPages(posStart), PageUtils.getMaxResults());
        }
        Page page = goodsSpecService.findSelectGoodsTypes(value, pageable);
        return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
    }

    public
    @ResponseBody
    UDataSet queryPageAjax(@PathClass("className") Class clazz,
                           @RequestParam(value = "_key", required = false) String key,//过滤字段名（一般用于主从表）
                           @RequestParam(value = "_value", required = false) String value,//过滤字段值（一般用于主从表）
                           @RequestParam(value = "posStart", required = false) String posStart,//分页当前记录行
                           @RequestParam(value = "count", required = false) String count,//分页记录行
                           @RequestParam(value = "_order", required = false) String order,//排序字段名
                           @QueryJson List<QueryMapping> queryList,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        UDataSet dataSet = super.queryPageAjax(clazz, key, value, posStart, count, order, queryList, request, response);

        Page page = (Page) dataSet.getResult();
        goodsSpecService.querySelect(page, value);
        return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
    }

    @Override
    public <T extends BaseEntity> void SaveCheck(T entity) {
        if (entity.getClass() == GoodsSpec.class) {
            GoodsSpec goodsSpec = (GoodsSpec) entity;

            List<GoodsSpec> list = goodsSpecService.findBySpName(goodsSpec.getSpName());
            int size = list.size();
            if (StringUtils.hasText(entity.getId())) {
                if (size == 1) {
                    if (!list.get(0).getId().equals(goodsSpec.getId())) {
                        Assert.isTrue(!(size > 0), goodsSpec.getSpName() + "已经存在！");
                    }
                }
                if (size > 1) {
                    Assert.isTrue(!(size > 0), goodsSpec.getSpName() + "数据异常！");
                }
            } else {
                Assert.isTrue(!(size > 0), goodsSpec.getSpName() + "已经存在！");
            }

        }
    }

    public void saveAjax(@Valid @PathDatabaseEntity("className") BaseEntity entity,
                         BindingResult result,
                         @RequestParam(value = "_key", required = false) String key,
                         @RequestParam(value = "_value", required = false) String value,
                         HttpServletRequest request,
                         HttpServletResponse response
    ) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        if (entity.getClass() == GoodsSpec.class) {
            GoodsSpec goodsSpec = (GoodsSpec) entity;
            Assert.notNull(value, "类型id不能为空！");
            goodsSpecService.save(goodsSpec, value);
        } else {
            super.saveAjax(entity, result, key, value, request, response);
        }
    }

    @RequestMapping("/a/updateAll/{className}")
    @ResponseBody
    public Data updateAllAjax(@PathGridData("className") List<BaseEntity> baseEntityLsit,
                              @RequestParam(value = "_key", required = false) String key,
                              @RequestParam(value = "_value", required = false) String value,
                              HttpServletRequest request) {

        goodsSpecService.select(baseEntityLsit, value);
        return super.updateAllAjax(baseEntityLsit, key, value, request);
    }
}
