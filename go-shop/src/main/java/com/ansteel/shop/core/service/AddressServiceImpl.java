package com.ansteel.shop.core.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.core.domain.Address;
import com.ansteel.shop.core.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
@Transactional(readOnly=true)
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional
    public Address save(Address address) {
        address.setMemberId(this.getUserId());
        return addressRepository.save(address);
    }

    @Override
    public Address findOneByIsDefaultAndMemberId() {
        return addressRepository.findOneByIsDefaultAndMemberId(1, this.getUserId());
    }

    @Override
    public List<Address> findByMemberIdOrderByIsDefaultDesc() {
        ;
        return addressRepository.findByMemberIdOrderByIsDefaultDesc(this.getUserId());
    }

    @Override
    @Transactional
    public void delect(String id) {
        addressRepository.delete(id);
    }

    @Override
    public Address findOne(String id) {
        return addressRepository.findOne(id);
    }

    public String getUserId() {
        String userId=UserUtils.getUserId();
        Assert.hasText(userId,"请登陆系统");
        return userId;
    }
}
