package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/26.
 */
@Service
@Transactional(readOnly=true)
public class ImageAttachmentTreeServiceImpl implements ImageAttachmentTreeService {
    public static final String IMAGE_FILE_ALIAS = "图片文件";
    public static final String IMAGE_FILE_NAME = "ImageFile";
    public static final String IMAGE_FILE_PATH = "/ImageFile";

    @Autowired
    AttachmentTreeService attachmentTreeService;

    public AttachmentTree getDefaultFileAttachmentTree(){
        return attachmentTreeService.newAttachmentTree(IMAGE_FILE_NAME, IMAGE_FILE_ALIAS, IMAGE_FILE_PATH, 1);
    }

    @Override
    @Transactional
    public AttachmentTree get(){
        AttachmentTree attachmentTree = attachmentTreeService.findOneByName(IMAGE_FILE_NAME);
        if (attachmentTree == null) {
            attachmentTree=attachmentTreeService.save(getDefaultFileAttachmentTree());
        }
        return attachmentTree;
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName) {
        return attachmentTreeService.getPath(attachmentTree,fileName,IMAGE_FILE_NAME);
    }
}
