package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly=true)
public class DefaultAttachmentTreeServiceImpl implements DefaultAttachmentTreeService  {

    public static final String OTHER_ALIAS = "其他文件";
    public static final String OTHER_NAME = "OtherFile";
    public static final String OTHER_PATH = "/OtherFile";

    @Autowired
    AttachmentTreeService attachmentTreeService;

    public AttachmentTree getDefaultFileAttachmentTree(){
        return attachmentTreeService.newAttachmentTree(OTHER_NAME, OTHER_ALIAS, OTHER_PATH, 1);
    }

    @Override
    @Transactional
    public AttachmentTree get(){
        AttachmentTree attachmentTree = attachmentTreeService.findOneByName(OTHER_NAME);
        if (attachmentTree == null) {
            attachmentTree=attachmentTreeService.save(getDefaultFileAttachmentTree());
        }
        return attachmentTree;
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName) {
        return attachmentTreeService.getPath(attachmentTree,fileName,OTHER_NAME);
    }

}
