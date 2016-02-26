package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.repository.AttachmentRepository;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class AttServiceImpl implements AttService {

    @Value("${go_pro.attPath}")
    private String attPath;

    @Autowired
    AttachmentTreeService attachmentTreeService;

    @Autowired
    FileAttachmentService fileAttachmentService;

    @Autowired
    DefaultAttachmentTreeService defaultAttachmentTreeService;

    @Autowired
    AttachmentRepository attachmentRepository;

    public String getPath(String path) {
        return  attPath+path;
    }

    @Override
    public UDataSet setAttachmentToPath(UDataSet dataSet, HttpServletRequest request) {
        List<Attachment> result = (List<Attachment>) ((Page) dataSet.getResult()).getContent();
        String url = (String) request
                .getAttribute(ViewModelConstant.S_URL);
        for(Attachment att:result){
            String path = att.getPath();
            att.setPath(this.getPath(path));
            String web=url+"/att/download/"+att.getId();
            att.setWebPath(web+"^"+web);
        }
        return dataSet;
    }

    @Override
    @Transactional
    public Attachment saveAttachment(MultipartFile file, String treeId, Attachment attachment) {
        if(!(StringUtils.hasText(attachment.getId())&&file==null)){
            Assert.notNull(file, "文件不能为空！");
            AttachmentTree attachmentTree = attachment.getAttachmentTree();
            Assert.hasText(treeId, "树id不能为空！");
            if(attachmentTree==null||!attachmentTree.getId().equals(treeId)){
                attachmentTree = attachmentTreeService.findOne(treeId);
                attachment.setAttachmentTree(attachmentTree);
            }
            attachment = fileAttachmentService.save(attachment, file);
        }

        return attachment;
    }

    @Override
    @Transactional
    public Attachment saveAttachment(String outPath, AttachmentTree attachmentTree) {
        if(attachmentTree==null){
            attachmentTree = defaultAttachmentTreeService.get();
        }
        File file = new File(outPath);
        Attachment entity=new Attachment();
        String fileName = file.getName();
        entity.setName(fileName);
        entity.setAlias(fileName);
        entity.setLoadFileName(fileName);
        entity.setFileSize(file.length());
        entity.setType(FileUtils.getFileType(fileName).toLowerCase());
        entity.setContentType(file.getName());
        entity.setPath(file.getPath().replace(attPath, ""));
        entity.setAttachmentTree(attachmentTree);
        return attachmentRepository.save(entity);
    }

    @Override
    @Transactional
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }
}
