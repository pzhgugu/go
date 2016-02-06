package com.ansteel.common.documentnumber.service;

import com.ansteel.common.documentnumber.domain.DocumentNumber;
import com.ansteel.common.documentnumber.domain.DocumentSerial;
import com.ansteel.common.documentnumber.repository.DocumentNumberRepository;
import com.ansteel.common.documentnumber.repository.DocumentSerialRepository;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.utils.SystemDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/1.
 */
@Service
public class SerialNumberCreateServiceImpl implements SerialNumberCreateService {

    @Autowired
    DocumentNumberRepository documentNumberRepository;

    @Autowired
    DocumentSerialRepository documentSerialRepository;

    public static int INIT_NUM=1;

    @Override
    public String getSerialNumber(String name) {
        DocumentNumber documentNo=null;
        try {
            documentNo=documentNumberRepository.findOneByName(name);
        }catch (Exception e){
            throw new PageException("【"+name+"】单据号重复，请检查！");
        }

        if(documentNo==null){
            throw new PageException(name+"没有找到此单据号类型！");
        }

        return this.getSerialNumber(documentNo);
    }

    @Override
    public String getSerialNumber(DocumentNumber documentNumber) {
        if(documentNumber.getIsStop()==1){
            throw new PageException("此单据号类型已经被停用！");
        }
        StringBuffer sb=new StringBuffer();
        sb.append(this.getType(documentNumber));
        sb.append(this.getObjectOne(documentNumber));
        sb.append(this.getObjectOne(documentNumber));
        sb.append(this.getYear(documentNumber));
        sb.append(this.getMonth(documentNumber));
        sb.append(this.getDay(documentNumber));
        sb.append(this.getDigitCapacity(documentNumber));
        return sb.toString();
    }

    private String getType(DocumentNumber documentNo) {
        String type = documentNo.getType();
        if(StringUtils.hasText(type)){
            return type;
        }
        return "";
    }

    private String getObjectOne(DocumentNumber documentNo) {
        String os=documentNo.getObjectOne();
        if(StringUtils.hasText(os)){
            return os;
        }
        return "";
    }

    private String getYear(DocumentNumber documentNo) {
        if(documentNo.getIsYear()==1){
            return SystemDateUtil.getYear();
        }
        return "";
    }

    private String getMonth(DocumentNumber documentNo) {
        if(documentNo.getIsMonth()==1){
            return SystemDateUtil.getMonth();
        }
        return "";
    }

    private String getDay(DocumentNumber documentNo) {
        if(documentNo.getIsDay()==1){
            return SystemDateUtil.getDay();
        }
        return "";
    }

    private synchronized String getDigitCapacity(DocumentNumber documentNo) {
        DocumentSerial currentSerialNumber=this.getCurrentSerialNumber(documentNo);

        boolean isZero=this.isZero(documentNo,currentSerialNumber);
        String initDC=this.initDigitCapacity(documentNo);
        DecimalFormat df = new DecimalFormat(initDC);
        if(currentSerialNumber.getSerialNumber()==ScopeFactory.DEFAULE_NUM){
            currentSerialNumber.setSerialNumber(INIT_NUM);
            documentSerialRepository.save(currentSerialNumber);
            return df.format(INIT_NUM);
        }
        if(isZero){
            this.updateSerialNumber(INIT_NUM,currentSerialNumber);
            return df.format(INIT_NUM);
        }
        int digitCapacity = documentNo.getDigitCapacity();
        int number=currentSerialNumber.getSerialNumber()+1;
        double n = Math.pow(10, digitCapacity);
        if(number<n){
            this.updateSerialNumber(number,currentSerialNumber);
            return df.format(number);
        }else{
            throw new PageException("单据号已超出最大位数！");
        }
    }

    private void updateSerialNumber(int number,DocumentSerial currentSerialNumber) {
        currentSerialNumber.setSerialNumber(number);
        currentSerialNumber.setNumberDate(new Date());
        documentSerialRepository.saveAndFlush(currentSerialNumber);
    }

    private String initDigitCapacity(DocumentNumber entity){
        int digitCapacity = entity.getDigitCapacity();
        if(digitCapacity<1){
            throw new PageException("单据号位数（"+digitCapacity+"),不能小于1！");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digitCapacity; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    private boolean isZero(DocumentNumber entity,DocumentSerial currentSerialNumber) {
        Date oldDate = currentSerialNumber.getNumberDate();
        Date currentDate=new Date();
        if(oldDate==null){
            return true;
        }
        int zero=entity.getZero();
        if(zero== SystemDateUtil.SYSTEM_YEAR){
            if(currentDate.getYear()>oldDate.getYear()){
                return true;
            }
        }else if(zero==SystemDateUtil.SYSTEM_MONTH){
            if(currentDate.getYear()>oldDate.getYear()||currentDate.getMonth()>oldDate.getMonth()){
                return true;
            }
        }else if(zero==SystemDateUtil.SYSTEM_DAY){
            if(currentDate.getYear()>oldDate.getYear()||currentDate.getMonth()>oldDate.getMonth()||currentDate.getDate()>oldDate.getDate()){
                return true;
            }
        }
        return false;
    }

    private DocumentSerial getCurrentSerialNumber(DocumentNumber documentNo) {
        return new ScopeFactory().run(documentNo, (List<DocumentSerial>) documentNo.getDocumentSerialList());
    }
}
