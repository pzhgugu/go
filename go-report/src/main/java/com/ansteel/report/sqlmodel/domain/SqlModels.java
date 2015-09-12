package com.ansteel.report.sqlmodel.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsCategory;


/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型字段模型实体类。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "sql_models")
public class SqlModels extends TreeEntity<SqlModels> {

    private static final long serialVersionUID = -8349403529456317667L;
    /**
     * 模型描述
     */
    @Column(length = 4000)
    private String scription;
    /**
     * 模式类型（-1为目录，0为Javabean，2为动态建模模型，3SQL类型）
     */
    private Integer modelType;

    /**
     * sql语句
     */
    @Lob
    private String sqlContent;

    /**
     * sql模式 0：sql模式 1：报表模式
     */
    private Integer sqlMode;

    public Integer getSqlMode() {
        return sqlMode;
    }

    public void setSqlMode(Integer sqlMode) {
        this.sqlMode = sqlMode;
    }

    @Column(name = "VERSION_PUBLISH", columnDefinition = "INT default 0")
    private Long versionPublish;

    public Long getVersionPublish() {
        return versionPublish;
    }

    public void setVersionPublish(Long versionPublish) {
        this.versionPublish = versionPublish;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    @OneToMany(mappedBy = "models", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<SqlFieldsCategory> fieldsCategory = new ArrayList<SqlFieldsCategory>();

    public Collection<SqlFieldsCategory> getFieldsCategory() {
        return fieldsCategory;
    }

    public void setFieldsCategory(Collection<SqlFieldsCategory> fieldsCategory) {
        this.fieldsCategory = fieldsCategory;
    }

    @OneToMany(mappedBy = "models", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<SqlFields> fields = new ArrayList<SqlFields>();

    public Collection<SqlFields> getFields() {
        return fields;
    }

    public void setFields(Collection<SqlFields> fields) {
        this.fields = fields;
    }

    public String getScription() {
        return scription;
    }

    public void setScription(String scription) {
        this.scription = scription;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }
}
