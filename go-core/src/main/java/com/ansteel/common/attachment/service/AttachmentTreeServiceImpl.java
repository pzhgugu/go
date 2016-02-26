package com.ansteel.common.attachment.service;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.repository.AttachmentTreeRepository;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly=true)
public class AttachmentTreeServiceImpl implements AttachmentTreeService {

    @Autowired
    AttachmentTreeRepository attachmentTreeRepository;

    @Override
    public AttachmentTree newAttachmentTree(String name, String alias, String catlogue, Integer isDate) {
        AttachmentTree attachmentTree = new AttachmentTree();
        attachmentTree.setName(name);
        attachmentTree.setAlias(alias);
        attachmentTree.setIsDate(isDate);
        attachmentTree.setCatalogue(catlogue);
        attachmentTree.setLayer(0);
        return attachmentTree;
    }

    @Override
    public AttachmentTree findOneByName(String name) {
        return attachmentTreeRepository.findOneByName(name);
    }

    @Override
    @Transactional
    public AttachmentTree save(AttachmentTree attachmentTree) {
        return attachmentTreeRepository.save(attachmentTree);
    }

    @Override
    public AttachmentTree findOne(String id) {
        return attachmentTreeRepository.findOne(id);
    }

    @Override
    public String getPath(AttachmentTree attachmentTree, String fileName,String type) {
        if(attachmentTree==null||!StringUtils.hasText(attachmentTree.getCatalogue())){
            return this.getDefaultPath(type,fileName);
        }
        String catalogue = attachmentTree.getCatalogue();
        if(attachmentTree.getIsDate()==1){
            catalogue+="/"+ DateTimeUtils.format(new Date(), "yyyyMMdd");
        }
        return catalogue+"/"+fileName;
    }

    private String getDefaultPath(String type,String fileName) {
        StringBuffer sb = new  StringBuffer();
        sb.append(this.getLocalPath(type));
        sb.append(fileName);
        return sb.toString();
    }

    private String getLocalPath(String type) {
        StringBuffer sb = new  StringBuffer();
        sb.append("/");
        sb.append(DateTimeUtils.format(new Date(), "yyyyMMdd"));
        sb.append("/");
        if(StringUtils.hasText(type)){
            sb.append(type);
            sb.append("/");
        }
        return sb.toString();
    }
}
