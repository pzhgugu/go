package com.ansteel.shop.goods.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;
import com.ansteel.common.documentnumber.domain.DocumentNumberTree;
import com.ansteel.common.documentnumber.service.DocumentNumberService;
import com.ansteel.common.documentnumber.service.DocumentNumberTreeServicer;
import com.ansteel.common.documentnumber.service.SerialNumberCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/1.
 */
@Service
public class OrderSerialNumberServiceImpl implements OrderSerialNumberService {

    private static final String ORDER_SERIAL_NAME="OrderNum";

    private static final String ORDER_SERIAL_TREE_NAME="shop";

    private static final String ORDER_SERIAL_TREE_ALIAS="商城";

    @Autowired
    DocumentNumberService documentNumberService;

    @Autowired
    DocumentNumberTreeServicer documentNumberTreeServicer;

    @Autowired
    SerialNumberCreateService serialNumberCreateService;

    @Override
    public String getOrderSerialNumber() {
        DocumentNumber documentNumber=this.getDocumentNumber();
        return serialNumberCreateService.getSerialNumber(documentNumber);
    }

    public DocumentNumber getDocumentNumber() {
        DocumentNumber documentNumber=documentNumberService.findByName(ORDER_SERIAL_NAME);

        if(documentNumber==null){
            DocumentNumberTree documentNumberTree=documentNumberTreeServicer.findOneByName(ORDER_SERIAL_TREE_NAME);
            if(documentNumberTree==null){
                documentNumberTree=new DocumentNumberTree();
                documentNumberTree.setName(ORDER_SERIAL_TREE_NAME);
                documentNumberTree.setAlias(ORDER_SERIAL_TREE_ALIAS);
                documentNumberTree=documentNumberTreeServicer.save(documentNumberTree);
            }
            documentNumber=new DocumentNumber();
            documentNumber.setType(ORDER_SERIAL_NAME);
            documentNumber.setIsYear(1);
            documentNumber.setIsDay(1);
            documentNumber.setIsMonth(1);
            documentNumber.setDigitCapacity(3);
            documentNumber.setDocumentNumberTree(documentNumberTree);
            documentNumber=documentNumberService.save(documentNumber);
        }
        return documentNumber;
    }
}
