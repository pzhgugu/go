package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/2/26.
 */
public interface AttService {

    Attachment saveAttachment(String outPath, AttachmentTree attachmentTree);

    Attachment save(Attachment attachment);
}
