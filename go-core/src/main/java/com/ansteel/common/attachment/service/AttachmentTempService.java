package com.ansteel.common.attachment.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;

/**
 * Created by Administrator on 2016/2/24.
 */
public interface AttachmentTempService {
    /**
     * 保存文件返回物理路径
     * @param pic
     * @return
     */
    String saveFile(MultipartFile pic);

    String getPath(String id);

    File saveFile(MultipartFile pic, int maxWidth);
}
