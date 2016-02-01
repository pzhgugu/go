package com.ansteel.common.documentnumber.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "document_number")
public class DocumentNumber  extends OperEntity {

    /**
     * 单据类型
     */
    @Column(name = "TYPE" , length = 255)
    private String type;
    /**
     * 对象1
     */
    @Column(name = "OBJECT_ONE" , length = 255)
    private String objectOne;
    /**
     * 对象2
     */
    @Column(name = "OBJECT_TOW" , length = 255)
    private String objectTow;

    /**
     * 是否年   0否  1是
     */
    @Column(name = "IS_YEAR" , length = 255)
    private int isYear;
    /**
     * 是否月
     */
    @Column(name = "IS_MONTH" , length = 255)
    private int isMonth;
    /**
     * 是否日
     */
    @Column(name = "IS_DAY" , length = 255)
    private int isDay;
    /**
     * 流水号位数
     */
    @Column(name = "DIGIT_CAPACITY" , length = 255)
    private int digitCapacity;
    /**
     * 流水号归零标志(1:年，2:月,3:日)
     */
    @Column(name = "ZERO" , length = 255)
    private int zero;
    /**
     * 是否停用
     */
    @Column(name = "IS_STOP" , length = 255)
    private int isStop;

    @Column(name = "SCOPE" , length = 255)
    private String scope;

    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "treeid", nullable = false)
    @JsonIgnore
    private DocumentNumberTree documentNumberTree;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentNumber")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    private Collection<DocumentSerial> documentSerialList = new ArrayList<>();

    public Collection<DocumentSerial> getDocumentSerialList() {
        return documentSerialList;
    }

    public void setDocumentSerialList(Collection<DocumentSerial> documentSerialList) {
        this.documentSerialList = documentSerialList;
    }

    public DocumentNumberTree getDocumentNumberTree() {
        return documentNumberTree;
    }

    public void setDocumentNumberTree(DocumentNumberTree documentNumberTree) {
        this.documentNumberTree = documentNumberTree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectOne() {
        return objectOne;
    }

    public void setObjectOne(String objectOne) {
        this.objectOne = objectOne;
    }

    public String getObjectTow() {
        return objectTow;
    }

    public void setObjectTow(String objectTow) {
        this.objectTow = objectTow;
    }

    public int getIsYear() {
        return isYear;
    }

    public void setIsYear(int isYear) {
        this.isYear = isYear;
    }

    public int getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(int isMonth) {
        this.isMonth = isMonth;
    }

    public int getIsDay() {
        return isDay;
    }

    public void setIsDay(int isDay) {
        this.isDay = isDay;
    }

    public int getDigitCapacity() {
        return digitCapacity;
    }

    public void setDigitCapacity(int digitCapacity) {
        this.digitCapacity = digitCapacity;
    }

    public int getZero() {
        return zero;
    }

    public void setZero(int zero) {
        this.zero = zero;
    }

    public int getIsStop() {
        return isStop;
    }

    public void setIsStop(int isStop) {
        this.isStop = isStop;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
