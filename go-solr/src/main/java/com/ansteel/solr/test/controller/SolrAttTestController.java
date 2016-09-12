package com.ansteel.solr.test.controller;

import com.ansteel.solr.core.service.SolrService;
import com.ansteel.solr.test.domain.AttModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.GroupParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/solr/att")
public class SolrAttTestController {

    @Autowired
    SolrService solrService;

    public class CompleteModel {
        private String n;
        private Integer i;

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }
    }


    @RequestMapping("/auto")
    @ResponseBody
    public Object autoComplete(String fq,String callback,Model model, HttpServletRequest request,
                               HttpServletResponse response){
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //query.setFilterQueries("nameTypeCreator:" + fq);
        query.setParam(GroupParams.GROUP_LIMIT, "10");


        List<AttModel> cms = new ArrayList<AttModel>();
        try {
            cms=solrService.query("att_core",AttModel.class,query);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(cms);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping("/page")
    @ResponseBody
    public Object page(String fq,String callback,Model model, HttpServletRequest request,
                               HttpServletResponse response){
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.addSort(new SolrQuery.SortClause("id", SolrQuery.ORDER.desc));
        Pageable pageable=new PageRequest(0,20);


        Page page=null;
        try {
            page=solrService.query("att_core",AttModel.class,query,pageable);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(page);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping("/group")
    @ResponseBody
    public Object group(String fq,String callback,Model model, HttpServletRequest request,
                               HttpServletResponse response){
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFilterQueries("nameTypeCreator:" + fq);
        query.setParam(GroupParams.GROUP, true);
        query.setParam(GroupParams.GROUP_FIELD, "loadFileName");
        query.setParam(GroupParams.GROUP_LIMIT, "10");


        List<CompleteModel> cms = new ArrayList<CompleteModel>();
        try {
            QueryResponse queryResponse=solrService.query("att_core",query);
            List<GroupCommand>  list=queryResponse.getGroupResponse().getValues();
            for(GroupCommand gc:list){
                for(Group g:gc.getValues()) {
                    CompleteModel cm = new CompleteModel();
                    cm.setN(g.getGroupValue());
                    cm.setI(g.getResult().size());
                    cms.add(cm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(cms);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping("/query")
    public String autoComplete(Model model, HttpServletRequest request,
                               HttpServletResponse response){
        return "WEB-INF/report/page/solr_att.html";
    }


}
