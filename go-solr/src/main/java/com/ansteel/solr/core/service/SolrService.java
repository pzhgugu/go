package com.ansteel.solr.core.service;

import com.ansteel.solr.test.domain.GoodsClassModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public interface SolrService {
    /**
     * 增加单个实体
     * @param core 域名
     * @param bean 实体
     * @throws IOException
     * @throws SolrServerException
     */
    void add(String core,Object bean) throws IOException, SolrServerException;

    /**
     * 增加一个集合
     * @param core 域名
     * @param beans 实体集合
     * @throws IOException
     * @throws SolrServerException
     */
    void adds(String core,Collection<?> beans) throws IOException, SolrServerException;

    /**
     * 获取域客户端
     * @param core
     * @return
     */
    HttpSolrClient getHttpSolrClient(String core);

    /**
     * 查询list
     * @param core 域名
     * @param clazz 查询类class
     * @param query
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    <T>List<T> query(String core,Class<T> clazz, SolrQuery query) throws IOException, SolrServerException;

    QueryResponse query(String core, SolrQuery query) throws IOException, SolrServerException;

    <T>Page query(String core,Class<T> clazz, SolrQuery query,Pageable pageable) throws IOException, SolrServerException;

}
