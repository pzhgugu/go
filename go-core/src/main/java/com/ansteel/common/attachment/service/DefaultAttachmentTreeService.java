package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;

/**
 * Created by Administrator on 2016/2/25.
 */
public interface DefaultAttachmentTreeService{
    AttachmentTree get();

    String getPath(AttachmentTree attachmentTree, String fileName);
}
