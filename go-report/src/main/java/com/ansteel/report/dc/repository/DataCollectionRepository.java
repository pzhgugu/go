package com.ansteel.report.dc.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.dc.domain.DataCollection;

/**
 * Created by gugu on 2016/11/30.
 */
public interface DataCollectionRepository  extends ProjectRepository<DataCollection,String> {

    DataCollection findOneByName(String name);
}
