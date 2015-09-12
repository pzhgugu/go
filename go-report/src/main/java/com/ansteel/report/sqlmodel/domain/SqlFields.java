package com.ansteel.report.sqlmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.common.prentmodel.domain.Fields;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型字段实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "sql_fields")
public class SqlFields extends Fields {


    /**
     *
     */
    private static final long serialVersionUID = -6044835479715148989L;


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    @JsonIgnore
    private SqlModels models = new SqlModels();


    public SqlModels getModels() {
        return models;
    }

    public void setModels(SqlModels models) {
        this.models = models;
    }

}
