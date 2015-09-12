package com.ansteel.report.sqlmodel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.common.prentmodel.domain.FieldsCategory;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型字段分类实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "sql_fields_category")
public class SqlFieldsCategory extends FieldsCategory {


    /**
     *
     */
    private static final long serialVersionUID = 7401564153040472481L;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    @JsonIgnore
    private SqlModels models = new SqlModels();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "fieldsCategory")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<SqlFieldsQuery> fieldsQuery = new ArrayList<SqlFieldsQuery>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "fieldsCategory")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<SqlFieldsGrid> fieldsGrid = new ArrayList<SqlFieldsGrid>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "fieldsCategory")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<SqlFieldsForm> fieldsForm = new ArrayList<SqlFieldsForm>();


    public Collection<SqlFieldsQuery> getFieldsQuery() {
        return fieldsQuery;
    }

    public void setFieldsQuery(Collection<SqlFieldsQuery> fieldsQuery) {
        this.fieldsQuery = fieldsQuery;
    }

    public Collection<SqlFieldsGrid> getFieldsGrid() {
        return fieldsGrid;
    }

    public void setFieldsGrid(Collection<SqlFieldsGrid> fieldsGrid) {
        this.fieldsGrid = fieldsGrid;
    }

    public Collection<SqlFieldsForm> getFieldsForm() {
        return fieldsForm;
    }

    public void setFieldsForm(Collection<SqlFieldsForm> fieldsForm) {
        this.fieldsForm = fieldsForm;
    }

    public SqlModels getModels() {
        return models;
    }

    public void setModels(SqlModels models) {
        this.models = models;
    }


}
