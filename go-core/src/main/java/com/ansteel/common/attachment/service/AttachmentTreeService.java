package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;

/**
 * Created by Administrator on 2016/2/25.
 */
public interface AttachmentTreeService {
    /**
     * 获取树类，但不保存数据
     * @param name
     * @param alias
     * @param catlogue
     * @param isDate
     * @return
     */
    AttachmentTree newAttachmentTree(String name,String alias,String catlogue,Integer isDate);

    AttachmentTree findOneByName(String name);

    AttachmentTree save(AttachmentTree attachmentTree);

    AttachmentTree findOne(String id);

    String getPath(AttachmentTree attachmentTree, String fileName, String type);
}
