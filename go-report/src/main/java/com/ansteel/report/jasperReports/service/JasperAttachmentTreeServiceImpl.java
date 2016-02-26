package com.ansteel.report.jasperReports.service;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.service.AttachmentTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/26.
 */
@Service
@Transactional(readOnly=true)
public class JasperAttachmentTreeServiceImpl implements JasperAttachmentTreeService {
    //Jasper模板路径
    public static final String JASPER_TPL_PATH = "/JasperTpl";
    //Jasper模板名称
    public static final String JASPER_TPL_NAME = "JasperTpl";
    //Jasper模板别名
    public static final String JASPER_TPL_ALIAS = "Jasper报表模板";

    @Autowired
    AttachmentTreeService attachmentTreeService;

    public AttachmentTree getDefaultFileAttachmentTree(){
        return attachmentTreeService.newAttachmentTree(JASPER_TPL_NAME, JASPER_TPL_ALIAS, JASPER_TPL_PATH, 1);
    }

    @Override
    @Transactional
    public AttachmentTree get(){
        AttachmentTree attachmentTree = attachmentTreeService.findOneByName(JASPER_TPL_NAME);
        if (attachmentTree == null) {
            attachmentTree=attachmentTreeService.save(getDefaultFileAttachmentTree());
        }
        return attachmentTree;
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName) {
        return attachmentTreeService.getPath(attachmentTree,fileName,JASPER_TPL_NAME);
    }
}
