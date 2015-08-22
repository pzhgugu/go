package com.ansteel.shop.album.service;

import java.util.List;

import com.ansteel.shop.album.domain.AlbumClass;

public interface AlbumClassService {

	/**
	 * 获取当前用户的相册
	 * @return
	 */
	List<AlbumClass> getCurrentAlbumClass(String sortType);	
	
	List<AlbumClass> getCurrentAlbumClass();
	

	/**
	 * 添加当前用户默认相册
	 * @return
	 */
	AlbumClass addDefaultCurrentAlbumClass();
	
	/**
	 * 添加默认相册
	 * @return
	 */
	AlbumClass addDefaultAlbumClass(String storeId);

	/**
	 * 通过相册名称查询
	 * @param name
	 * @return
	 */
	AlbumClass findOneByAclassName(String name);

	/**
	 * 保存当前用户店铺分类
	 * @param albumClass
	 * @return
	 */
	AlbumClass saveUse(AlbumClass albumClass);

	void delete(String id);

	AlbumClass findOne(String id);

	AlbumClass save(AlbumClass album);

	/**
	 * 获取当前用户默认图片
	 * 如果相册为空建立默认相册
	 * 如果没有设置默认相册，随机设置一个
	 * @return
	 */
	AlbumClass getCurrentDefalue();

}
