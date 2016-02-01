package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;
import com.ansteel.common.documentnumber.domain.DocumentSerial;
import com.ansteel.core.exception.PageException;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/1.
 */
public class ScopeFactory {

    /**
     * 集团
     */
    private final static String GROUP="0";
    /**
     * 公司
     */
    private final static String COMPANY="1";
    /**
     * 初始化值，不要大于0；
     */
    public final static int DEFAULE_NUM=-1;


    public DocumentSerial run(DocumentNumber entity,List<DocumentSerial> serialNumberList) {
        String scope=entity.getScope();
        if(serialNumberList.size()>0){
            if(serialNumberList.size()==1){
                return serialNumberList.get(0);
            }else{
                throw new PageException("流水号表有重复数据，请检查！");
            }
        }else{
            return createSerialNumber(entity);
        }
    }

    private DocumentSerial createSerialNumber(DocumentNumber entity) {
        DocumentSerial serialNumber = new DocumentSerial();
        serialNumber.setSerialNumber(DEFAULE_NUM);
        serialNumber.setNumberDate(new Date());
        serialNumber.setDocumentNumber(entity);
        return serialNumber;
    }
}
