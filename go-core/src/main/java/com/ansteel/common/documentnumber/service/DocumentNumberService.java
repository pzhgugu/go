package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;

/**
 * Created by Administrator on 2016/2/1.
 */
public interface DocumentNumberService {
    DocumentNumber findByName(String name);

    DocumentNumber save(DocumentNumber documentNumber);
}
