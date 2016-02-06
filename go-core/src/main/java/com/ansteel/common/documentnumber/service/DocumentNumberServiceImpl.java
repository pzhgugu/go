package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;
import com.ansteel.common.documentnumber.repository.DocumentNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/1.
 */
@Service
public class DocumentNumberServiceImpl implements  DocumentNumberService {

    @Autowired
    DocumentNumberRepository documentNumberRepository;

    @Override
    public DocumentNumber findByName(String name) {
        return documentNumberRepository.findOneByName(name);
    }

    @Override
    public DocumentNumber save(DocumentNumber documentNumber) {
        return documentNumberRepository.save(documentNumber);
    }
}
