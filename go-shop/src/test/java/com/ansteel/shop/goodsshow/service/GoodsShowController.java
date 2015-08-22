package com.ansteel.shop.goodsshow.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.GoodsClassStaple;
import com.ansteel.shop.goods.domain.JsonGoodsClass;
import com.ansteel.shop.goods.service.GoodsClassService;
import com.ansteel.shop.goods.service.GoodsClassStapleService;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

public class GoodsShowController {
	
	
}
