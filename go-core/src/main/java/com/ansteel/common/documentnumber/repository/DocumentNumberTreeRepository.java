package com.ansteel.common.documentnumber.repository;

import com.ansteel.common.documentnumber.domain.DocumentNumberTree;
import com.ansteel.core.repository.ProjectRepository;

/**
 * Created by Administrator on 2016/2/1.
 */
public interface DocumentNumberTreeRepository extends ProjectRepository<DocumentNumberTree,String> {
    DocumentNumberTree findOneByName(String name);
}
