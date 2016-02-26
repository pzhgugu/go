package com.ansteel.report.excel.service;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.service.AttachmentTreeService;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ReportAttachmentTreeServiceImpl implements ReportAttachmentTreeService {
    //报表文件别名
    public static final String REPORT_FILE_ALIAS = "报表文件";
    //报表文件名称
    public static final String REPORT_FILE_NAME = "ReportFile";
    //报表文件路径
    public static final String REPORT_FILE_PATH = "/ReportFile";

    @Autowired
    AttachmentTreeService attachmentTreeService;

    public AttachmentTree getDefaultFileAttachmentTree(){
        return attachmentTreeService.newAttachmentTree(REPORT_FILE_NAME, REPORT_FILE_ALIAS, REPORT_FILE_PATH, 1);
    }

    @Override
    @Transactional
    public AttachmentTree get(){
        AttachmentTree attachmentTree = attachmentTreeService.findOneByName(REPORT_FILE_NAME);
        if (attachmentTree == null) {
            attachmentTree=attachmentTreeService.save(getDefaultFileAttachmentTree());
        }
        return attachmentTree;
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName) {
        return attachmentTreeService.getPath(attachmentTree,fileName,REPORT_FILE_NAME);
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName, String type) {
        return attachmentTreeService.getPath(attachmentTree,fileName,type);
    }

    @Override
    public String getCatalogue(AttachmentTree attachmentTree, String type) {
        StringBuffer sb = new  StringBuffer();
        if(StringUtils.hasText(attachmentTree.getCatalogue())){
            sb.append(attachmentTree.getCatalogue());
            sb.append("/");
        }else{
            sb.append("/");
            sb.append(attachmentTree.getName());
            sb.append("/");
        }
        if(attachmentTree.getIsDate()!=null&&attachmentTree.getIsDate()==1){
            sb.append(DateTimeUtils.format(new Date(), "yyyyMMdd"));
            sb.append("/");
        }
        if(StringUtils.hasText(type)){
            sb.append(type);
            sb.append("/");
        }
        return sb.toString();
    }
}
