package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/2/25.
 */
public interface FileAttachmentService {

    /**
     * 保存文件，记录数据库，默认目录
     * @param file
     * @return
     */
    Attachment save(MultipartFile file);
    /**
     * 保存附件
     * @param attachment
     * @return
     */
    Attachment save(Attachment attachment,MultipartFile file);
    /**
     * 保存附件
     * @param id
     * @return
     */
    Attachment save(String id,MultipartFile file);
    /**
     * 保存文件，记录数据库，默认目录
     * @param file
     * @return
     */
    Attachment save(AttachmentTree attachmentTree,MultipartFile file);

    /**
     *
     * @param outPath 文件路径
     * @param attachmentTree
     * @return
     */
    Attachment save(String outPath, AttachmentTree attachmentTree);
    /**
     * 通过ID得到附件
     * @param id
     * @return
     */
    Attachment findOne(String id);
    /**
     * 删除附件
     * @param id
     */
    void delete(String id);

    Attachment save(Attachment attachment);
}
