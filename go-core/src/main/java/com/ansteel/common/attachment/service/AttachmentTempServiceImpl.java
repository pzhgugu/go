package com.ansteel.common.attachment.service;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.ImageUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class AttachmentTempServiceImpl implements AttachmentTempService {
    @Value("${go_pro.attTempPath}")
    private String attTempPath;

    @Override
    public String saveFile(MultipartFile pic) {

        StringBuffer pathSB = new StringBuffer();
        pathSB.append("/");
        pathSB.append(DateTimeUtils.format(new Date(), "yyyyMMdd"));
        pathSB.append("/");
        String fileName = pic.getOriginalFilename();
        String type=FileUtils.getFileType(fileName).toLowerCase();
        String uuid = StringUtils.getUuid();
        pathSB.append(uuid+"."+type);
        String path = attTempPath+pathSB.toString();
        try {
            FileUtils.saveFile(pic.getInputStream(),path);
        } catch (Exception e) {
            throw new PageException(e.getMessage());
        }
        return pathSB.toString();
    }

    @Override
    public String getPath(String id) {
        return attTempPath+id;
    }

    @Override
    public File saveFile(MultipartFile pic, int maxWidth) {

        BufferedImage bi = null;
        InputStream is =null;
        try {
            is = pic.getInputStream();
            // 读取源图像
            bi = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new PageException("读取图片错误！");
        }
        int srcWidth = bi.getWidth(); // 源图宽度
        int srcHeight = bi.getHeight(); // 源图高度
        Image image = bi.getScaledInstance(srcWidth, srcHeight,
                Image.SCALE_DEFAULT);
        String fileName = pic.getOriginalFilename();
        String type=FileUtils.getFileType(fileName).toLowerCase();

        StringBuffer pathSB = new StringBuffer();
        pathSB.append("/");
        pathSB.append(DateTimeUtils.format(new Date(), "yyyyMMdd"));
        pathSB.append("/");

        String uuid = StringUtils.getUuid();
        pathSB.append(uuid+"."+type);
        String path = attTempPath+pathSB.toString();

        if(srcWidth>maxWidth){
            srcHeight=maxWidth;
        }
        try {
            File file= new File(path);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            ImageUtils.resize(image,file,srcHeight,1);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
