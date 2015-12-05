package com.ansteel.shop.store.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.repository.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AttachmentService attachmentService;

    @Override
    public Store getCurrentStore() {
        return this.findOneByMemberId(UserUtils.getUserId());
    }

    @Override
    public Store findOneByMemberId(String userId) {
        return storeRepository.findOneByMemberId(userId);
    }

    @Override
    public List<Store> findBySellerName(String sellerName) {
        return storeRepository.findBySellerName(sellerName);
    }

    @Override
    public List<Store> findByStoreName(String storeName) {
        return storeRepository.findByName(storeName);
    }

    @Override
    public Store getCurrentStore(String userId) {
        return this.findOneByMemberId(userId);
    }

    @Override
    @Transactional
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    @Transactional
    public Store update(Store store) {
        Store storeDate = this.getCurrentStore();
        Assert.notNull(storeDate, store.getId() + ",没有找到此记录！");
        try {
            BeanUtils.applyIf(storeDate, store);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PageException(e.getMessage());
        }
        return storeRepository.save(storeDate);
    }

    @Override
    @Transactional
    public Store update(Store store, MultipartFile storeLabel, MultipartFile storeBanner) {
        try {
            if (storeLabel.getSize() > 0) {
                Attachment logo = attachmentService.saveAttachment(storeLabel);
                store.setStoreLabel(logo.getId());
            }
            if (storeBanner.getSize() > 0) {
                Attachment banner = attachmentService.saveAttachment(storeBanner);
                store.setStoreBanner(banner.getId());
            }
        } catch (Exception e) {
            throw new PageException(e.getMessage());
        }
        return this.update(store);
    }

}
