package com.ansteel.shop.album.repository;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.album.domain.AlbumClass;

public interface AlbumClassRepository  extends ProjectRepository<AlbumClass,String>{

	List<AlbumClass> findByStoreIdOrderByAclassSortAsc(String id);

	AlbumClass findOneByAclassName(String name);

	List<AlbumClass> findByStoreId(String id,Sort sort);

}
