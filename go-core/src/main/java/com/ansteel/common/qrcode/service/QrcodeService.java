package com.ansteel.common.qrcode.service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/12/31.
 */
public interface QrcodeService {
    /**
     * 生成二维码(QRCode)图片的公共方法
     * @param content 存储内容
     * @param width 图片类型
     * @param height 二维码尺寸
     * @return
     */
    BufferedImage qRCodeCommon(String content, int width, int height);

    /**
     * 解析二维码（QRCode）
     * @param imgPath 图片路径
     * @return
     */
    String decoderQRCode(String imgPath);

    /**
     * 解析二维码（QRCode）
     * @param input 输入流
     * @return
     */
    String decoderQRCode(InputStream input);
    /**
     * 生成二维码(QRCode)图片
     * @param content 存储内容
     * @param output 输出流
     * @param width 图片类型
     * @param height 二维码尺寸
     */
    void encoderQRCode(String content, OutputStream output, int width, int height);
}
