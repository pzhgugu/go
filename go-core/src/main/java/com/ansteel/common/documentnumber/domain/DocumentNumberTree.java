package com.ansteel.common.documentnumber.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = Constants.G_TABLE_PREFIX + "document_number_tree")
public class DocumentNumberTree extends TreeEntity<DocumentNumberTree> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentNumberTree")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("displayOrder")
    @JsonIgnore
    private Collection<DocumentNumber> documentNumberList = new ArrayList<>();


    public Collection<DocumentNumber> getDocumentNumberList() {
        return documentNumberList;
    }

    public void setDocumentNumberList(Collection<DocumentNumber> documentNumberList) {
        this.documentNumberList = documentNumberList;
    }
}
