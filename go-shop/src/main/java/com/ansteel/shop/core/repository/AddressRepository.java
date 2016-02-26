package com.ansteel.shop.core.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.core.domain.Address;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface AddressRepository extends ProjectRepository<Address,String> {
    Address findOneByIsDefaultAndMemberId(Integer isDefault, String userId);

    List<Address> findByMemberIdOrderByIsDefaultDesc(String userId);
}
