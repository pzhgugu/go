package com.ansteel.common.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/12/31.
 */
@Service
public class QrcodeServiceImpl implements QrcodeService {

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    @Override
    public BufferedImage qRCodeCommon(String content, int width, int height) {
        BitMatrix byteMatrix = null;
        try {
            byteMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return toBufferedImage(byteMatrix);
    }



    @Override
    public String decoderQRCode(String imgPath) {
        return null;
    }

    @Override
    public String decoderQRCode(InputStream input) {
        return null;
    }

    @Override
    public void encoderQRCode(String content, OutputStream output,int width, int height) {
        try {
            BufferedImage bufImg = this.qRCodeCommon(content, width, height);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, "png", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage toBufferedImage(BitMatrix matrix) {

        int width = matrix.getWidth();

        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }
}
