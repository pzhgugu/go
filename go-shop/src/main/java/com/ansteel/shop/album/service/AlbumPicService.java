package com.ansteel.shop.album.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.shop.album.domain.AlbumPic;
import com.ansteel.shop.utils.JsonImage;

public interface AlbumPicService {

	/**
	 * 保存图片
	 * @param file
	 * @return
	 */
	JsonImage saveImage(String id,MultipartFile file);
	/**
	 * 保存图片
	 * @param id
	 * @param file
	 * @return
	 */
	AlbumPic saveAlbumPic(String id, MultipartFile file);

	/**
	 * 保存图片信息
	 * @param albumPic
	 * @return
	 */
	AlbumPic save(AlbumPic albumPic);

	List<AlbumPic> findByAclassIdOrderByUpdatedAsc(
			String id);

	void delete(String[] ids);

	/**
	 * 移动分类
	 * @param toCategoryId
	 * @param ids
	 */
	void move(String toCategoryId, String[] ids);

	void delete(String id);

	void move(String toCategoryId, String id);

	AlbumPic update(String id, MultipartFile file);

	/**
	 * 设置封面
	 * @param id
	 * @param categoryId
	 */
	void cover(String id, String categoryId);
	/**
	 * 查询相册中的图片
	 * @param id
	 * @param sortType
	 * @param curPage
	 * @return
	 */
	Page<AlbumPic> findByAclassId(String id, String sortType,Integer curPage,int size);
	/**
	 * 查询所有
	 * @param sortType
	 * @param curPage
	 * @return
	 */
	Page<AlbumPic> findAll(String sortType,Integer curPage,int size);

}
