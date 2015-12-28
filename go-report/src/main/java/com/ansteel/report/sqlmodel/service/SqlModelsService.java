package com.ansteel.report.sqlmodel.service;

import com.ansteel.report.sqlmodel.domain.SqlModels;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型服务接口。
 */
public interface SqlModelsService {

    Map<String, String> getFieldNames(List mapList);

    /**
     * 扫描保存SQL模型
     *
     * @return
     */
    SqlModels scanSqlModels(String id, HttpServletRequest request);

    SqlModels getSqlModels(String modelName);

    /**
     * 获取sql语句
     *
     * @param sqlModels
     * @return
     */
    String getSql(SqlModels sqlModels);

    String findByNameToSql(String modelName);

    String showReport(String reportName, SqlModels sqlModels, Map<String, Object> operMap, String type, HttpServletRequest request, HttpServletResponse response,String fileName);
}
