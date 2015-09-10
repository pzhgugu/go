package com.ansteel.shop.goods.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsBrand;
import com.ansteel.shop.goods.repository.GoodsBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2015/9/9.
 */
@Service
@Transactional(readOnly = true)
public class GoodsBrandServiceBean implements GoodsBrandService {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    GoodsBrandRepository goodsBrandRepository;

    @Override
    @Transactional
    public String saveAttachment(MultipartFile file,GoodsBrand goodsBrand) {
        Attachment attachment = null;
        if (StringUtils.hasText(goodsBrand.getId())) {
            String aId = goodsBrand.getLogoImage();
            if (StringUtils.hasText(aId)) {
                attachment = attachmentService.getAttachmentById(aId);
            }
        }

        if (attachment == null) {
            // 获取exclTpl附件目录
            attachment = attachmentService.saveAttachment(file);
        } else {
            attachment = attachmentService.saveAttachment(attachment, file);
        }
        goodsBrand.setLogoImage(attachment.getId());
        return attachment.getId();
    }

    @Override
    public boolean isNameRepeat(GoodsBrand goodsBrand) {
        GoodsBrand goodsBrandNew=goodsBrandRepository.findOneByBrandName(goodsBrand.getBrandName());
        if(goodsBrandNew==null){
            return false;
        }
        if(goodsBrand.getId()!=null&&goodsBrand.getId().equals(goodsBrandNew.getId())){
            return false;
        }
        return true;
    }
}
