package com.ansteel.shop.album.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.album.domain.AlbumPic;

public interface AlbumPicRepository extends ProjectRepository<AlbumPic,String> {
	
	Long countByAclassIdAndStoreId(String aclassId,String storeId);

	List<AlbumPic> findByAclassIdOrderByUpdatedAsc(String id);

	Page<AlbumPic> findByAclassId(String id, Pageable pageable);

}
