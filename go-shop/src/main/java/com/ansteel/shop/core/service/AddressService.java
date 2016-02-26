package com.ansteel.shop.core.service;

import com.ansteel.shop.core.domain.Address;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface AddressService {

    Address save(Address address);

    Address findOneByIsDefaultAndMemberId();

    List<Address> findByMemberIdOrderByIsDefaultDesc();

    void delect(String id);

    Address findOne(String id);
}
