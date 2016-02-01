package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.repository.DocumentSerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/1.
 */
@Service
public class DocumentSerialServiceImpl implements DocumentSerialService {

    @Autowired
    DocumentSerialRepository documentSerialRepository;
}
