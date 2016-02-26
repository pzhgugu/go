package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;

/**
 * Created by Administrator on 2016/2/26.
 */
public interface ImageAttachmentTreeService {
    AttachmentTree get();

    String getPath(AttachmentTree attachmentTree,String fileName);
}
