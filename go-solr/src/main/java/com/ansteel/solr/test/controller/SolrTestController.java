package com.ansteel.solr.test.controller;

import com.ansteel.core.query.PageUtils;
import com.ansteel.solr.core.service.SolrService;
import com.ansteel.solr.test.domain.GoodsClassModel;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/solr/test")
public class SolrTestController {

    private static final Logger logger=Logger.getLogger(SolrTestController.class);

    @Autowired
    SolrService solrService;

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request,
                          HttpServletResponse response) {

        GoodsClassModel goodsClassModel =new GoodsClassModel();
        goodsClassModel.setId("888888");
        goodsClassModel.setGoodsClassName("我的名称1");
        goodsClassModel.setGoodsClassTypeName("名称类型");
        goodsClassModel.setGoodsClassSort(34);
        try {
            solrService.add("new_core",goodsClassModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/addlist")
    public String addList(Model model, HttpServletRequest request,
                      HttpServletResponse response) {

        List<GoodsClassModel> list = new ArrayList<>();
        GoodsClassModel goodsClassModel =new GoodsClassModel();
        goodsClassModel.setId("888888");
        goodsClassModel.setGoodsClassName("我的名称1");
        goodsClassModel.setGoodsClassTypeName("名称类型");
        goodsClassModel.setGoodsClassSort(34);
        list.add(goodsClassModel);

        GoodsClassModel goodsClassModel1 =new GoodsClassModel();
        goodsClassModel1.setId("999999");
        goodsClassModel1.setGoodsClassName("9名称1");
        goodsClassModel1.setGoodsClassTypeName("9类型");
        goodsClassModel1.setGoodsClassSort(39);
        list.add(goodsClassModel1);
        try {
            solrService.adds("new_core", list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/query")
    public String query(Model model, HttpServletRequest request,
                      HttpServletResponse response) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.addSort(new SortClause("id", ORDER.desc));
        try {
            List<GoodsClassModel> list=solrService.query("new_core", GoodsClassModel.class,query);
            for(GoodsClassModel gcm:list){
                logger.info(gcm.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/querypage")
    public String queryPage(Model model, HttpServletRequest request,
                        HttpServletResponse response) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.addSort(new SortClause("id", ORDER.desc));
        query.setStart(0);
        query.setRows(20);
        try {
            Page page=solrService.query("new_core", GoodsClassModel.class,query,null);
            for(Object gcm:page.getContent()){
                logger.info(((GoodsClassModel) gcm).getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/querypage1")
    public String queryPage1(Model model, HttpServletRequest request,
                            HttpServletResponse response) {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.addSort(new SortClause("id", ORDER.desc));
        Pageable pageable=new PageRequest(0,20);

        try {
            Page page=solrService.query("new_core", GoodsClassModel.class,query,pageable);
            for(Object gcm:page.getContent()){
                logger.info(((GoodsClassModel) gcm).getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
