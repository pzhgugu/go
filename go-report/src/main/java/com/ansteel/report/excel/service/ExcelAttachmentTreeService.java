package com.ansteel.report.excel.service;

import com.ansteel.common.attachment.domain.AttachmentTree;

/**
 * Created by Administrator on 2016/2/26.
 */
public interface ExcelAttachmentTreeService {
    AttachmentTree get();

    String getPath(AttachmentTree attachmentTree, String fileName);

    String getPath(AttachmentTree attachmentTree, String newFileName, String type);
}
