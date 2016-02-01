package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;

/**
 * Created by Administrator on 2016/2/1.
 */
public interface SerialNumberCreateService {
    /**
     * 根据t单据类型得到流水号
     */
    String getSerialNumber(String type);

    String getSerialNumber(DocumentNumber documentNo);
}
