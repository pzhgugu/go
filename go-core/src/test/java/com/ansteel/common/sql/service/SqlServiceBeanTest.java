package com.ansteel.common.sql.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2015/8/28.
 */
public class SqlServiceBeanTest {

    @Test
    public void asToUpperCaseTest(){
        SqlServiceBean  sqlServiceBean= new SqlServiceBean();
        String sql="select name as name, id   As  id , alias  AS alias  ,cid aS  cid from ";
        sqlServiceBean.asToUpperCase(sql);
    }

}