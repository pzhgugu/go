package com.ansteel.report.excel.service;

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
public class ExcelAttachmentTreeServiceImpl implements ExcelAttachmentTreeService {
    //Excel模板路径
    public static final String EXCEL_TPL_PATH = "/ExcelTpl";
    //Excel模板名称
    public static final String EXCEL_TPL_NAME = "ExcelTpl";
    //Excel模板别名
    public static final String EXCEL_TPL_ALIAS = "excel报表模板";

    @Autowired
    AttachmentTreeService attachmentTreeService;

    public AttachmentTree getDefaultFileAttachmentTree(){
        return attachmentTreeService.newAttachmentTree(EXCEL_TPL_NAME, EXCEL_TPL_ALIAS, EXCEL_TPL_PATH, 1);
    }

    @Override
    @Transactional
    public AttachmentTree get(){
        AttachmentTree attachmentTree = attachmentTreeService.findOneByName(EXCEL_TPL_NAME);
        if (attachmentTree == null) {
            attachmentTree=attachmentTreeService.save(getDefaultFileAttachmentTree());
        }
        return attachmentTree;
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName) {
        return attachmentTreeService.getPath(attachmentTree,fileName,EXCEL_TPL_NAME);
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName, String type) {
        return attachmentTreeService.getPath(attachmentTree,fileName,type);
    }
}
