package com.ansteel.common.documentnumber.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "document_serial")
public class DocumentSerial extends BaseEntity {

    /**
     * 流水号
     */
    @Column(name = "SERIAL_NUMBER" )
    private int serialNumber;
    /**
     * 流水号日期
     */
    @Column(name = "NUMBER_DATE" )
    private Date numberDate;

    public DocumentNumber getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(DocumentNumber documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getNumberDate() {
        return numberDate;
    }

    public void setNumberDate(Date numberDate) {
        this.numberDate = numberDate;
    }

    //模板模型操作主类,联合主键
    @ManyToOne(optional = false)
    @JoinColumn(name="DID",nullable = true)
    private DocumentNumber documentNumber;




}
