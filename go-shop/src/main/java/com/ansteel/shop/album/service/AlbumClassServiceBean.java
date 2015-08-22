package com.ansteel.shop.album.service;

import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.album.repository.AlbumClassRepository;
import com.ansteel.shop.album.repository.AlbumPicRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

@Service
@Transactional(readOnly=true)
public class AlbumClassServiceBean implements AlbumClassService {
	
	@Autowired
	AlbumClassRepository albumClassRepository;
	
	@Autowired
	AlbumPicRepository albumPicRepository;
	
	@Autowired
	StoreService storeService;
	

	@Override
	public List<AlbumClass> getCurrentAlbumClass() {
		return this.getCurrentAlbumClass(null);
	}

	@Override
	public List<AlbumClass> getCurrentAlbumClass(String sortType) {
		Store store=storeService.getCurrentStore();

		Sort sort =null;
		if(!StringUtils.hasText(sortType)){
			sortType="5";
		}
		switch (sortType) {
		//按创建时间从晚到早
			case "0":
				sort =new Sort(Direction.DESC, "created");
				break;
		//按创建时间从早到晚
			case "1":
				sort =new Sort(Direction.ASC, "created");
				break;
		//按相册名降序
			case "2":
				sort =new Sort(Direction.DESC, "aclassName");
				break;
		//按相册名升序
			case "3":
				sort =new Sort(Direction.ASC, "aclassName");
				break;
		//按排序从大到小
			case "4":
				sort =new Sort(Direction.DESC, "aclassSort");
				break;
		//按排序从小到大
			case "5":
				
			default:
				sort =new Sort(Direction.ASC, "aclassSort");
				break;
		}
		
		
		List<AlbumClass> acList =albumClassRepository.findByStoreId(store.getId(),sort);
		if(acList.size()==0){
			AlbumClass defalutAlbumClass=this.addDefaultAlbumClass(store.getId());
			acList.add(defalutAlbumClass);
		}
		this.setPicNumber(acList,store.getId());
		return acList;
	}

	/**
	 * 设置图片数量
	 * @param acList
	 */
	private void setPicNumber(List<AlbumClass> acList,String storeId) {
		for(AlbumClass albumClass:acList){
			Long number = albumPicRepository.countByAclassIdAndStoreId(albumClass.getId(),storeId);
			albumClass.setPicNumber(number);
		}
	}
	
	@Override
	public AlbumClass addDefaultCurrentAlbumClass() {
		Store store=storeService.getCurrentStore();
		return this.addDefaultAlbumClass(store.getId());
	}

	@Override
	public AlbumClass addDefaultAlbumClass(String storeId) {
		AlbumClass  albumClass = new AlbumClass();
		albumClass.setAclassName("默认相册");
		albumClass.setStoreId(storeId);
		albumClass.setIsDefault(1);
		albumClass.setAclassSort(0);
		return albumClassRepository.save(albumClass);
	}

	@Override
	public AlbumClass findOneByAclassName(String name) {
		return albumClassRepository.findOneByAclassName(name);
	}

	@Override
	@Transactional
	public AlbumClass saveUse(AlbumClass albumClass) {
		Store store=storeService.getCurrentStore();
		albumClass.setStoreId(store.getId());
		if(albumClass.getAclassSort()==null){
			albumClass.setAclassSort(100);
		}
		return albumClassRepository.save(albumClass);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		albumClassRepository.delete(id);
	}

	@Override
	public AlbumClass findOne(String id) {
		return albumClassRepository.findOne(id);
	}

	@Override
	@Transactional
	public AlbumClass save(AlbumClass album) {
		return albumClassRepository.save(album);
	}

	@Override
	public AlbumClass getCurrentDefalue() {
		List<AlbumClass> albumClassList= getCurrentAlbumClass();
		if(albumClassList!=null&&albumClassList.size()>0){
			for(AlbumClass albumClass:albumClassList){
				if(albumClass.getIsDefault()==1){
					return albumClass;
				}
			}
			AlbumClass albumClass=albumClassList.get(0);
			albumClass.setIsDefault(1);
			return this.save(albumClass);
		}
		return addDefaultCurrentAlbumClass();
	}

}
