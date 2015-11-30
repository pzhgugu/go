package com.ansteel.shop.album.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.album.domain.AlbumPic;
import com.ansteel.shop.album.repository.AlbumPicRepository;
import com.ansteel.shop.utils.JsonImage;

@Service
@Transactional(readOnly=true)
public class AlbumPicServiceImpl implements AlbumPicService {
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	AlbumClassService albumClassService;
	
	@Autowired
	AlbumPicRepository albumPicRepository;

	@Override
	@Transactional
	public JsonImage saveImage(String id,MultipartFile file) {	
		AlbumPic albumPic =this.saveAlbumPic(id,file);
		
		JsonImage jsonImage=new JsonImage();
		jsonImage.setFile_id(albumPic.getId());
		jsonImage.setFile_name(albumPic.getApicTag());
		jsonImage.setFile_path(albumPic.getApicCover());
		jsonImage.setOrigin_file_name(albumPic.getApicName());
		jsonImage.setState(true);
		return jsonImage;
	}

	@Override
	@Transactional
	public AlbumPic saveAlbumPic(String id, MultipartFile file) {			
			Attachment attachment = this.saveAttachment(file);			
			AlbumClass albumClass = albumClassService.findOne(id);
			Assert.notNull(albumClass, id+",此相册分类不存在！");
			AlbumPic albumPic =new AlbumPic();
			albumPic.setAclassId(albumClass.getId());		
			albumPic.setStoreId(albumClass.getStoreId());
			albumPic.setApicName(attachment.getLoadFileName());
			this.setImageInfo(albumPic, attachment, file);
			return this.save(albumPic);
	}
	
	private void setImageInfo(AlbumPic albumPic,Attachment attachment, MultipartFile file){

		albumPic.setApicCover(attachment.getId());
		albumPic.setApicSize(attachment.getFileSize());
		albumPic.setApicTag(attachment.getFileName());
		BufferedImage sourceImg=null;
		try {
			sourceImg = ImageIO.read(file.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		albumPic.setApicSpec(sourceImg.getWidth()+"x"+sourceImg.getHeight());
	}

	private Attachment saveAttachment(MultipartFile file) {
		Assert.isTrue(file.getSize()<1000000, "文件超过了1M，请编辑后重新上传！");
		return attachmentService.saveAttachment(file);
	}

	@Override
	@Transactional
	public AlbumPic save(AlbumPic albumPic) {
		return albumPicRepository.save(albumPic);
	}

	@Override
	public List<AlbumPic> findByAclassIdOrderByUpdatedAsc(String id) {
		return albumPicRepository.findByAclassIdOrderByUpdatedAsc(id);
	}

	@Override
	@Transactional
	public void delete(String[] ids) {
		for(String id:ids){
			this.delete(id);
		}
	}

	@Override
	@Transactional
	public void move(String toCategoryId, String[] ids) {
		for(String id:ids){
			this.move(toCategoryId, id);
		}
	}

	@Override
	@Transactional
	public void delete(String id) {
		AlbumPic albumPic = albumPicRepository.findOne(id);
		attachmentService.delect(albumPic.getApicCover());
		albumPicRepository.delete(id);
	}

	@Override
	@Transactional
	public void move(String toCategoryId, String id) {
		AlbumPic albumPic = albumPicRepository.findOne(id);
		albumPic.setAclassId(toCategoryId);
		albumPicRepository.save(albumPic);
	}

	@Override
	@Transactional
	public AlbumPic update(String id, MultipartFile file) {
		AlbumPic albumPic = albumPicRepository.findOne(id);
		attachmentService.delect(albumPic.getApicCover());
		Attachment attachment = this.saveAttachment(file);
		this.setImageInfo(albumPic, attachment, file);
		return albumPicRepository.save(albumPic);
	}

	@Override
	@Transactional
	public void cover(String id, String categoryId) {
		AlbumPic albumPic = albumPicRepository.findOne(id);
		AlbumClass albumClass = albumClassService.findOne(categoryId);
		albumClass.setAclassCover(albumPic.getApicCover());
		albumClassService.save(albumClass);
	}

	@Override
	public Page<AlbumPic> findByAclassId(String id, String sortType,
			Integer curPage,int size) {
		
		Sort sort =this.getSort(sortType);		
		if(curPage==null){
			curPage=0;
		}else if(curPage>0){
			curPage=curPage-1;
		}
		Pageable pageable = new PageRequest(curPage,size,sort);
		return albumPicRepository.findByAclassId(id,pageable);
	}

	private Sort getSort(String sortType) {
		Sort sort =null;
		if(!StringUtils.hasText(sortType)){
			sortType="5";
		}
		switch (sortType) {
		//按上传时间从晚到早
			case "0":
				sort =new Sort(Direction.DESC, "created");
				break;
		//按上传时间从早到晚
			case "1":
				sort =new Sort(Direction.ASC, "created");
				break;
		//按图片从大到小
			case "2":
				sort =new Sort(Direction.DESC, "apicSize");
				break;
		//按图片从小到大
			case "3":
				sort =new Sort(Direction.ASC, "apicSize");
				break;
		//按图片名降序
			case "4":
				sort =new Sort(Direction.DESC, "apicName");
				break;
		//按图片名升序
			case "5":
				
			default:
				sort =new Sort(Direction.ASC, "updated");
				break;
		}
		
		return sort;
	}

	@Override
	public Page<AlbumPic> findAll(String sortType, Integer curPage,int size) {
		Sort sort =this.getSort(sortType);		
		if(curPage==null){
			curPage=0;
		}else if(curPage>0){
			curPage=curPage-1;
		}
		Pageable pageable = new PageRequest(curPage,size,sort);
		return albumPicRepository.findAll(pageable);
	}

}
