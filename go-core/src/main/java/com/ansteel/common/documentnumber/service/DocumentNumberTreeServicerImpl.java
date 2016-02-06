package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumberTree;
import com.ansteel.common.documentnumber.repository.DocumentNumberTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/1.
 */
@Service
public class DocumentNumberTreeServicerImpl implements DocumentNumberTreeServicer {

    @Autowired
    DocumentNumberTreeRepository documentNumberTreeRepository;

    @Override
    public DocumentNumberTree findOneByName(String name) {
        return documentNumberTreeRepository.findOneByName(name);
    }

    @Override
    public DocumentNumberTree save(DocumentNumberTree documentNumberTree) {
        return documentNumberTreeRepository.save(documentNumberTree);
    }
}
