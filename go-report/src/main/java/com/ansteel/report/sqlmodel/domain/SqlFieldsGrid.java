package com.ansteel.report.sqlmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.common.prentmodel.domain.FieldsCategory;
import com.ansteel.common.prentmodel.domain.FieldsGrid;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型字段表格实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "sql_fields_grid")
public class SqlFieldsGrid extends FieldsGrid {


    /**
     *
     */
    private static final long serialVersionUID = -9084954664952066815L;


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "fcid", nullable = true)
    @JsonIgnore
    SqlFieldsCategory fieldsCategory = new SqlFieldsCategory();


    public FieldsCategory getFieldsCategory() {
        return fieldsCategory;
    }

    public void setFieldsCategory(SqlFieldsCategory fieldsCategory) {
        this.fieldsCategory = fieldsCategory;
    }
}
