package com.ansteel.shop.member.web;

import com.ansteel.common.security.service.UserService;
import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.context.CustomTimestampEditor;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.*;
import com.ansteel.shop.member.domain.ShopMember;
import com.ansteel.shop.member.service.ShopMemberService;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/shop/member")
public class MemberController {

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        datetimeFormat.setLenient(false);

        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
                dateFormat, true));
        binder.registerCustomEditor(java.sql.Timestamp.class,
                new CustomTimestampEditor(datetimeFormat, true));
    }


    @Autowired
    ShopMemberService shopMemberService;

    @RequestMapping("/index")
    public String index(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER",shopMember);

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","index");
        model.addAttribute("P_VIEW","index.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");
    }

    @RequestMapping("/set")
    public String set(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER",shopMember);
        String json=shopMember.getMemberPrivacy();
        if(StringUtils.hasText(json)) {
            PrivacyModel pm = (PrivacyModel) JsonUtils.objectFromJson(json, PrivacyModel.class);
            model.addAttribute("P_PRIVACY", pm);
        }

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","set");
        model.addAttribute("P_OP","base");
        model.addAttribute("P_VIEW", "set.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");

    }

    @RequestMapping("/set_save")
    @ResponseBody
    public void setSave(Model model, MemberModel memberModel,
                      HttpServletRequest request,
                      HttpServletResponse response){
        String url = request.getContextPath() + "/shop/member/set";
        String name = "";
        try {
            ShopMember shopMember=shopMemberService.save(memberModel);
        } catch (Exception ex) {
            ex.printStackTrace();
            name = "保存失败！";
        }
        name = "保存成功！";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/passwd")
    public String passWord(Model model,
                      HttpServletRequest request,
                      HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER",shopMember);

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","set");
        model.addAttribute("P_OP","passwd");
        model.addAttribute("P_VIEW", "passwd.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");

    }

    @RequestMapping("/passwd_save")
    @ResponseBody
    public void passWordSave(Model model,
                        @RequestParam(value = "orig_password") String oldPwd,
                        @RequestParam(value = "new_password") String newPwd,
                        @RequestParam(value = "confirm_password") String newPwdConfirm,
                        HttpServletRequest request,
                        HttpServletResponse response){
        String url = request.getContextPath() + "/shop/member/passwd";
        String name = "修改密码成功！";
        try {
            shopMemberService.changePassword(oldPwd,newPwd,newPwdConfirm,UserUtils.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
            name = ex.getMessage();
        }

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/email")
    public String email(Model model,
                           HttpServletRequest request,
                           HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER",shopMember);

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","set");
        model.addAttribute("P_OP","email");
        model.addAttribute("P_VIEW", "email.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");

    }

    @RequestMapping("/email_save")
    @ResponseBody
    public void emailSave(Model model,
                             @RequestParam(value = "orig_password") String oldPwd,
                             @RequestParam(value = "email") String email,
                             HttpServletRequest request,
                             HttpServletResponse response){
        String url = request.getContextPath() + "/shop/member/email";
        String name = "电子邮件修改成功！";
        try {
            ShopMember shopMember=shopMemberService.updateEmail(oldPwd, email);
        } catch (Exception ex) {
            ex.printStackTrace();
            name = ex.getMessage();
        }

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/avatar")
    public String avatar(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER",shopMember);



        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","set");
        model.addAttribute("P_OP","avatar");
        model.addAttribute("P_VIEW", "avatar.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");

    }

    @RequestMapping("/avatar_save")
    public String avatarSave(Model model,
                          @RequestParam(value = "pic") MultipartFile pic,
                          HttpServletRequest request,
                          HttpServletResponse response){
        ShopMember shopMember=shopMemberService.getCurrentMemeber();
        model.addAttribute("P_MEMBER", shopMember);
        File file=shopMemberService.saveUserImage(pic, 500);
        model.addAttribute("P_USER_IMAGE",file.getPath());
        try{
            InputStream is=new FileInputStream(file);
            BufferedImage bi=ImageIO.read(is);
            model.addAttribute("P_IMAGE_WIDTH",bi.getWidth());
            model.addAttribute("P_IMAGE_HEIGHT",bi.getHeight());
        }catch (Exception e){
            e.printStackTrace();
            throw new PageException(e.getMessage());
        }

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_ACT","set");
        model.addAttribute("P_OP","upload");
        model.addAttribute("P_VIEW", "upload.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");
    }

    @RequestMapping("/image")
    public void download(@RequestParam(value = "path") String  path,
                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DownloadUtils.download(response, path, null);
    }

    @RequestMapping("/image/save")
    public String  imageSave(
            @RequestParam("newfile")String newfile,
            @RequestParam("x1")Double x1,
            @RequestParam("y1")Double y1,
            @RequestParam("x2")Double x2,
            @RequestParam("y2")Double y2,
            @RequestParam("w")Double width,
            @RequestParam("h")Double height,
            HttpServletRequest request,
            HttpServletResponse response){

        shopMemberService.avatarSave(newfile,x1.intValue(), y1.intValue(), width.intValue(), height.intValue());

        return "redirect:/shop/member/avatar";
    }
}
