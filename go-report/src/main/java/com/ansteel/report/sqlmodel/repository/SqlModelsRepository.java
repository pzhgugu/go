package com.ansteel.report.sqlmodel.repository;

import com.ansteel.report.sqlmodel.domain.SqlModels;

import com.ansteel.core.repository.ProjectRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型持久层。
 */
public interface SqlModelsRepository extends ProjectRepository<SqlModels, String> {

    SqlModels findOneByName(String name);
}
