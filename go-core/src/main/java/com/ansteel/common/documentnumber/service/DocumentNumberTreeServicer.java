package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumberTree;

/**
 * Created by Administrator on 2016/2/1.
 */
public interface DocumentNumberTreeServicer {
    DocumentNumberTree findOneByName(String name);

    DocumentNumberTree save(DocumentNumberTree documentNumberTree);
}
