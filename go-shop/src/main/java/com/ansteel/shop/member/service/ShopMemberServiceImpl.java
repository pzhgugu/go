package com.ansteel.shop.member.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.service.*;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.service.UserService;
import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.*;
import com.ansteel.shop.member.domain.ShopMember;
import com.ansteel.shop.member.repository.ShopMemberRepository;
import com.ansteel.shop.member.web.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/22.
 */
@Service
@Transactional(readOnly=true)
public class ShopMemberServiceImpl implements ShopMemberService {

    @Autowired
    ShopMemberRepository shopMemberRepository;

    @Autowired
    UserService userService;

    @Autowired
    FileAttachmentService fileAttachmentService;

    @Autowired
    AttachmentTempService attachmentTempService;

    @Autowired
    ImageAttachmentTreeService imageAttachmentTreeService;

    @Value("${go_pro.attPath}")
    private String attPath;

    @Override
    public ShopMember findOneByUserId(String userId) {
        return shopMemberRepository.findOneByUserId(userId);
    }

    @Override
    @Transactional
    public ShopMember getCurrentMemeber() {
        UserInfo userInfo = UserUtils.getUserInfo();
        Assert.notNull(userInfo, "请先登录系统！");
        ShopMember shopMember = this.findOneByUserId(userInfo.getId());
        if (shopMember == null) {
            shopMember = new ShopMember();
            shopMember.setUserId(userInfo.getId());
            shopMember.setUserName(userInfo.getUsername());
            shopMember.setAlias(userInfo.getAlias());
            shopMember = this.save(shopMember);
        }
        return shopMember;
    }

    public ShopMember getCurrentMemeber(UserInfo userInfo) {
        ShopMember shopMember = this.findOneByUserId(userInfo.getId());
        if (shopMember == null) {
            shopMember = new ShopMember();
            shopMember.setUserId(userInfo.getId());
            shopMember.setUserName(userInfo.getUsername());
            shopMember.setAlias(userInfo.getAlias());
            shopMember = this.save(shopMember);
        }
        return shopMember;
    }

    @Override
    @Transactional
    public ShopMember save(ShopMember shopMember) {
        return shopMemberRepository.save(shopMember);
    }

    @Override
    @Transactional
    public ShopMember save(MemberModel memberModel) {
        UserInfo userInfo = UserUtils.getUserInfo();
        ShopMember shopMember = this.findOneByUserId(userInfo.getId());
        if (shopMember == null) {
            shopMember = new ShopMember();
            shopMember.setUserId(userInfo.getId());
            shopMember.setUserName(userInfo.getUsername());
            shopMember.setAlias(userInfo.getAlias());
        }
        try {
            BeanUtils.applyIf(shopMember, memberModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtils.jsonFromObject(memberModel.getPrivacyModel());
        shopMember.setMemberPrivacy(json);
        return this.save(shopMember);
    }

    @Override
    @Transactional
    public ShopMember updateEmail(String oldPwd, String email) {
        UserInfo userInfo = UserUtils.getUserInfo();
        Users user = userService.findOne(userInfo.getId());
        Assert.isTrue(userService.isPasswordValid(user.getPassword(), oldPwd), "原密码输入错误，请重新输入！");
        ShopMember shopMember = this.getCurrentMemeber(userInfo);
        shopMember.setMemberEmail(email);
        return this.save(shopMember);
    }

    @Override
    @Transactional
    public String changePassword(String oldPwd, String newPwd, String newPwdConfirm, String userName) {
        return userService.changePassword(oldPwd, newPwd, newPwdConfirm, userName);
    }

    @Override
    public String saveUserImage(MultipartFile pic, String imageId) {
        Attachment attachment = null;
        if (StringUtils.hasText(imageId)) {
            attachment = fileAttachmentService.findOne(imageId);
            if (attachment == null) {
                attachment = fileAttachmentService.save(pic);
            } else {
                attachment = fileAttachmentService.save(attachment, pic);
            }
        } else {
            attachment = fileAttachmentService.save(pic);
        }
        return attachment.getId();
    }

    @Override
    public String saveUserImage(MultipartFile pic) {
        return attachmentTempService.saveFile(pic);
    }

    @Override
    public String getTempImagePath(String id) {
        return attachmentTempService.getPath(id);
    }

    @Override
    public File saveUserImage(MultipartFile pic, int maxWidth) {
        return attachmentTempService.saveFile(pic, maxWidth);
    }

    @Override
    @Transactional
    public void avatarSave(String fileName, int x, int y, int width, int height) {
        File file=new File(fileName);

        String imageName=StringUtils.getUuid()+".jpg";
        AttachmentTree imageAttTree=imageAttachmentTreeService.get();
        String imagePath=imageAttachmentTreeService.getPath(imageAttTree, imageName);
        String outPath=attPath+imagePath;

        try{
            InputStream is=new FileInputStream(file);
            ImageUtils.abscut(is, x, y, width, height, outPath);
        }catch (Exception e){
            e.printStackTrace();
            throw new PageException(e.getMessage());
        }
        UserInfo userInfo = UserUtils.getUserInfo();
        ShopMember shopMember = this.findOneByUserId(userInfo.getId());
        Attachment attachment=null;
        if (shopMember != null) {
            if(StringUtils.hasText(shopMember.getMemberAvatar())){
                attachment=fileAttachmentService.findOne(shopMember.getMemberAvatar());
            }
        }

        if(attachment==null) {
            attachment = fileAttachmentService.save(outPath, imageAttTree);
        }else{
            attachment.setPath(imagePath);
        }
        fileAttachmentService.save(attachment);
        shopMember.setMemberAvatar(attachment.getId());
        shopMemberRepository.save(shopMember);
    }
}