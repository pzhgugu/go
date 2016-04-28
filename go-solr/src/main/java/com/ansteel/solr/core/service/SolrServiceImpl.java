package com.ansteel.solr.core.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.solr.test.domain.GoodsClassModel;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class SolrServiceImpl implements SolrService {

    private static final Logger logger=Logger.getLogger(SolrServiceImpl.class);

    private String urlPath;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }


    @Override
    public void add(String core,Object bean) throws IOException, SolrServerException {
        logger.info(urlPath);
        HttpSolrClient client =this.getHttpSolrClient(core);
        client.addBean(bean);
        client.commit();
    }

    @Override
    public void adds(String core, Collection<?> beans) throws IOException, SolrServerException {
        HttpSolrClient client =this.getHttpSolrClient(core);
        client.addBeans(beans);
        client.commit();
    }

    @Override
    public HttpSolrClient getHttpSolrClient(String core) {
        return new HttpSolrClient(urlPath+core);
    }

    @Override
    public <T>List<T> query(String core,Class<T> clazz, SolrQuery query) throws IOException, SolrServerException{
        HttpSolrClient client =this.getHttpSolrClient(core);
        QueryResponse response = client.query(query);
        return response.getBeans(clazz);
    }

    @Override
    public QueryResponse query(String core, SolrQuery query) throws IOException, SolrServerException{
        HttpSolrClient client =this.getHttpSolrClient(core);
        QueryResponse response = client.query(query);
        return response;
    }

    @Override
    public <T> Page query(String core, Class<T> clazz, SolrQuery query,Pageable pageable) throws IOException, SolrServerException {
        HttpSolrClient client =this.getHttpSolrClient(core);
        if(pageable!=null){
            query.setStart(pageable.getPageNumber());
            query.setRows(pageable.getPageSize());
        }else {
            Integer start = query.getStart();
            if(start==null){
                start=0;
            }
            Integer rows = query.getRows();
            Assert.notNull(start,"请设置分页条数rows");
            pageable = new PageRequest(start,rows);

        }
        QueryResponse response = client.query(query);
        SolrDocumentList documentList=response.getResults();
        List content = new DocumentObjectBinder().getBeans(clazz, documentList);
        Page page = new PageImpl(content,pageable,documentList.getNumFound());
        return page;
    }
}
