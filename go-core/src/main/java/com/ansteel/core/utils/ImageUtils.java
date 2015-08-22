package com.ansteel.core.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.exception.PageException;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：图片操作工具类。  
 */
public class ImageUtils {
	
	public static final String IMAGE_FILE_ALIAS = "图片文件";
	public static final String IMAGE_FILE_NAME = "ImageFile";	
	public static final String IMAGE_FILE_PATH = "/ImageFile";
	
	public static AttachmentTree getImageAttachmentTree(){
		return getAttachmentTree(IMAGE_FILE_NAME,IMAGE_FILE_ALIAS,IMAGE_FILE_PATH,1);
	}
	
	public static AttachmentTree getAttachmentTree(String name,String alias,String catlogue,Integer isDate){
		AttachmentTree attachmentTree = new AttachmentTree();
		attachmentTree.setName(name);
		attachmentTree.setAlias(alias);
		attachmentTree.setIsDate(isDate);
		attachmentTree.setCatalogue(catlogue);
		attachmentTree.setLayer(0);
		return attachmentTree;
	}

	/**
	 * @param srcImageFile
	 *            源图像地址
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	public static void abscut(InputStream inputStream, int x, int y, int destWidth,
			int destHeight,int size,String destImagePath) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(inputStream);
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度

			int scale=0;
			if(srcWidth>srcHeight){
				scale=srcWidth;
			}else{
				scale=srcHeight;
			}
			//if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);

				/* ****************************************
				 * 判断原图的宽高和DIV宽高的大小 根据图片外层DIV的宽度，选择的起始点则有相对变化
				 * **************************************
				 */
				int x1 = x*scale/size;
				int y1 = y*scale/size;
				int w = destWidth*scale/size;
				int h = destHeight*scale/size;

				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x1, y1, w, h);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
				File file = new File(destImagePath);  
			    // 判断目录或文件是否存在 ,不存在创建文件夹
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				ImageIO.write(tag, "JPEG",file);
			//}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PageException("图片剪切错误："+e.getMessage());
		}
	}

}
