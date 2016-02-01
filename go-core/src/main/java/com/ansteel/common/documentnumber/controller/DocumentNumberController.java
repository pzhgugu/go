package com.ansteel.common.documentnumber.controller;

import com.ansteel.common.documentnumber.domain.DocumentNumber;
import com.ansteel.common.documentnumber.domain.DocumentNumberTree;
import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.TreeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/2/1.
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/document_num")
public class DocumentNumberController extends BaseController {
    @Override
    public Collection<EntityInfo> getEntityInfos() {
        // 树设置
        TreeInfo treeInfo = new TreeInfo();
        treeInfo.setTree(DocumentNumberTree.class);
        Collection<Class> tables = new ArrayList<Class>();
        tables.add(DocumentNumber.class);
        treeInfo.setTables(tables);

        Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
        EntityInfo tree = new EntityInfo();
        tree.setClazz(DocumentNumberTree.class);
        tree.setTree(treeInfo);
        eis.add(tree);

        EntityInfo table = new EntityInfo();
        table.setClazz(DocumentNumber.class);
        table.setTree(treeInfo);
        eis.add(table);
        return eis;
    }
}
