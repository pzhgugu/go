package com.ansteel.shop.member.service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.shop.member.domain.ShopMember;
import com.ansteel.shop.member.web.MemberModel;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;

/**
 * Created by Administrator on 2016/2/22.
 */
public interface ShopMemberService {
    ShopMember findOneByUserId(String userId);

    ShopMember getCurrentMemeber();

    ShopMember save(ShopMember shopMember);

    ShopMember save(MemberModel memberModel);

    ShopMember updateEmail(String oldPwd, String email);

    String changePassword(String oldPwd, String newPwd, String newPwdConfirm, String userName);

    String saveUserImage(MultipartFile pic,String imageId);

    String saveUserImage(MultipartFile pic);

    String getTempImagePath(String id);

    File saveUserImage(MultipartFile pic, int maxWidth);

    void avatarSave(String fileName, int x, int y, int width, int height);
}
