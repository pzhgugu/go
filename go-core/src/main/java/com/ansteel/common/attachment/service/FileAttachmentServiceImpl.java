package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.repository.AttachmentRepository;
import com.ansteel.common.attachment.repository.AttachmentTreeRepository;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@Transactional(readOnly=true)
public class FileAttachmentServiceImpl implements FileAttachmentService {


    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentTreeRepository attachmentTreeRepository;

    @Autowired
    DefaultAttachmentTreeService defaultAttachmentTreeService;

    @Autowired
    AttService attService;

    @Value("${go_pro.attPath}")
    private String attPath;

    private Attachment newAttachment(MultipartFile file) {
        Attachment attachment = new Attachment();
        String fileName = file.getOriginalFilename();
        attachment.setName(fileName);
        attachment.setAlias(fileName);
        return this.setAttachment(file,attachment);
    }

    private Attachment setAttachment(MultipartFile file,Attachment attachment) {
        String fileName = file.getOriginalFilename();
        attachment.setLoadFileName(fileName);
        attachment.setFileSize(file.getSize());
        attachment.setType(FileUtils.getFileType(fileName).toLowerCase());
        attachment.setContentType(file.getContentType());
        return attachment;
    }

    private void saveFile(MultipartFile file, Attachment attachment,
                          AttachmentTree attachmentTree) {
        //获取文件名称
        String fileName=attachment.getFileName();
        if(!StringUtils.hasText(fileName)){
            String uuid = StringUtils.getUuid();
            fileName=uuid+"."+attachment.getType();
            attachment.setFileName(fileName);
        }
        //获取目录名称
        String path=attachment.getPath();
        if(!StringUtils.hasText(path)){
            path = defaultAttachmentTreeService.getPath(attachmentTree, fileName);
        }

        //保存文件
        try {
            FileUtils.saveFile(file.getInputStream(),this.getPath(path));
        } catch (Exception e) {
            throw new PageException(e.getMessage());
        }
        //没有编码则设置为文件uuid
        if(!StringUtils.hasText(attachment.getName())){
            attachment.setName(fileName);
        }
        //没有名称设置为上传文件名称
        if(!StringUtils.hasText(attachment.getAlias())){
            attachment.setAlias(attachment.getLoadFileName());
        }
        //没有名称设置为上传文件名称
        if(!StringUtils.hasText(attachment.getPath())){
            attachment.setPath(path);
        }
    }

    @Override
    public String getPath(String path) {
        return  attPath+path;
    }

    @Override
    @Transactional
    public Attachment save(MultipartFile file) {
        AttachmentTree attachmentTree=defaultAttachmentTreeService.get();
        return this.save(attachmentTree,file);
    }

    @Override
    @Transactional
    public Attachment save(Attachment attachment, MultipartFile file) {
        this.setAttachment(file, attachment);
        AttachmentTree tree = attachment.getAttachmentTree();
        Assert.notNull(tree,"请设置附件树！");
        this.saveFile(file,attachment,tree);
        return attachmentRepository.save(attachment);
    }

    @Override
    @Transactional
    public Attachment save(String id, MultipartFile file) {
        Attachment attachment=attachmentRepository.findOne(id);
        Assert.notNull(attachment, id + ",不存在！");
        return this.save(attachment, file);
    }

    @Override
    @Transactional
    public Attachment save(AttachmentTree attachmentTree, MultipartFile file) {
        Attachment attachment=this.newAttachment(file);
        this.saveFile(file,attachment,attachmentTree);
        attachment.setAttachmentTree(attachmentTree);
        return attachmentRepository.save(attachment);
    }

    @Override
    @Transactional
    public Attachment save(String outPath, AttachmentTree attachmentTree) {
        return attService.saveAttachment(outPath, attachmentTree);
    }

    @Override
    public Attachment findOne(String id) {
        Attachment attachment=attachmentRepository.findOne(id);
        if(attachment!=null) {
            attachment.setPath(this.getPath(attachment.getPath()));
        }
        return attachment;
    }

    @Override
    @Transactional
    public void delete(String id) {
        Attachment attachment = attachmentRepository.findOne(id);
        if(attachment!=null){
            String path = this.getPath(attachment.getPath());
            FileUtils.deleteFile(path);
            attachmentRepository.delete(attachment);
        }
    }

    @Override
    @Transactional
    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    @Override
    @Transactional
    public Attachment save(MultipartFile file, String treeId, Attachment attachment) {
        if(!(StringUtils.hasText(attachment.getId())&&file==null)){
            Assert.notNull(file, "文件不能为空！");
            AttachmentTree attachmentTree = attachment.getAttachmentTree();
            Assert.hasText(treeId, "树id不能为空！");
            if(attachmentTree==null||!attachmentTree.getId().equals(treeId)){
                attachmentTree = attachmentTreeRepository.findOne(treeId);
                attachment.setAttachmentTree(attachmentTree);
            }
            attachment = this.save(attachment, file);
        }

        return attachment;
    }
}
