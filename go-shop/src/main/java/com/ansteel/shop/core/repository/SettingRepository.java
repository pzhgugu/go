package com.ansteel.shop.core.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.core.domain.Setting;

import java.util.List;

/**
 * Created by Administrator on 2015/12/14.
 */
public interface SettingRepository extends ProjectRepository<Setting,String> {
    List<Setting> findByName(String name);
}
