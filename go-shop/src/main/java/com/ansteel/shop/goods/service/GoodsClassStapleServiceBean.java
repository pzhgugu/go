package com.ansteel.shop.goods.service;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.GoodsClassStaple;
import com.ansteel.shop.goods.domain.JsonGoodsClass;
import com.ansteel.shop.goods.repository.GoodsClassStapleRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

@Service
@Transactional(readOnly=true)
public class GoodsClassStapleServiceBean implements GoodsClassStapleService {
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	GoodsClassStapleRepository goodsClassStapleRepository;
	
	@Autowired
	GoodsClassService goodsClassService;
	

	@Override
	public List<GoodsClassStaple> getCurrentGoodsClassStaple() {
		Store store=storeService.getCurrentStore();		
		return goodsClassStapleRepository.findByStoreId(store.getId());
	}

	@Override
	public GoodsClassStaple findByOne(String id) {
		return goodsClassStapleRepository.findOne(id);
	}

	public String getGoodsClassStr(List<GoodsClass> goodsClassList,String id){
		StringBuffer oneBuffer = new StringBuffer();
		for(GoodsClass g:goodsClassList){
			oneBuffer.append("<li data-param=\"{gcid:'");
			oneBuffer.append(g.getId());
			oneBuffer.append("',deep:1,tid:0}\" nctype=\"selClass\" >");
			oneBuffer.append("<a ");
			if(g.getId().equals(id)){
				oneBuffer.append(" class='classDivClick' ");
			}
			oneBuffer.append(" href=\"javascript:void(0)\">");
			oneBuffer.append("<i class=\"icon-double-angle-right\"></i>");
			oneBuffer.append(g.getAlias());
			oneBuffer.append("</a></li> ");
		}
		return oneBuffer.toString();
	}
	
	@Override
	public JsonGoodsClass selectGoodsClassStaple(String stapleid) {
		
		GoodsClassStaple goodsClassStaple=this.findByOne(stapleid);
		
		List<GoodsClass> one=goodsClassService.findByParentIsNull();
		List<GoodsClass> two = goodsClassService.findByParentId(goodsClassStaple.getGcId1());
		List<GoodsClass> three = goodsClassService.findByParentId(goodsClassStaple.getGcId2());
		
		JsonGoodsClass json = new JsonGoodsClass();
		json.setGc_id(goodsClassStaple.getGcId3());
		json.setType_id("0");
		json.setDone(true);
		json.setOne(this.getGoodsClassStr(one,goodsClassStaple.getGcId1()));
		json.setTwo(this.getGoodsClassStr(two,goodsClassStaple.getGcId2()));
		json.setThree(this.getGoodsClassStr(three,goodsClassStaple.getGcId3()));
		return json;
	}

	@Override
	@Transactional
	public void delect(String id) {
		goodsClassStapleRepository.delete(id);
	}

	@Override
	@Transactional
	public GoodsClassStaple checkSaveStaple(GoodsClass goodsClass) {
		Assert.notNull(goodsClass, "商品3级分类不能为空！");
		GoodsClass goodsClass2 = goodsClass.getParent();
		Assert.notNull(goodsClass2, "商品2级分类不能为空！");
		GoodsClass goodsClass1 = goodsClass2.getParent();
		Assert.notNull(goodsClass1, "商品1级分类不能为空！");

		Store store=storeService.getCurrentStore();	
		GoodsClassStaple goodsClassStaple=goodsClassStapleRepository.findOneByGcId3AndStoreId(goodsClass.getId(),store.getId());
		if(goodsClassStaple!=null){
			return goodsClassStaple;
		}
		goodsClassStaple=new GoodsClassStaple();
		goodsClassStaple.setStoreId(store.getId());
		goodsClassStaple.setGcId3(goodsClass.getId());
		goodsClassStaple.setGcId2(goodsClass2.getId());
		goodsClassStaple.setGcId1(goodsClass1.getId());
		
		String pattern="{0}>{1}>{2}";
		String stapleName = MessageFormat.format(pattern, goodsClass1.getName(),goodsClass2.getName(),goodsClass.getName());
		goodsClassStaple.setStapleName(stapleName);		
		return goodsClassStapleRepository.save(goodsClassStaple);
	}
}
