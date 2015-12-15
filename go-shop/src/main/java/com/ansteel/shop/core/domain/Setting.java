package com.ansteel.shop.core.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015/12/14.
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "setting")
public class Setting  extends BaseEntity {

    private  String name;

    @Lob
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
