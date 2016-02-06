package com.ansteel.shop.core.service;

import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.core.domain.Invoice;
import com.ansteel.shop.core.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
@Service
@Transactional(readOnly=true)
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public Invoice save(Invoice invoice) {
        invoice.setMemberId(this.getUserId());
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> findByMemberId() {
        return invoiceRepository.findByMemberId(this.getUserId());
    }

    @Override
    @Transactional
    public void delete(String id) {
        invoiceRepository.delete(id);
    }

    @Override
    public Invoice findOne(String id) {
        return invoiceRepository.findOne(id);
    }

    public String getUserId() {
        String userId=UserUtils.getUserId();
        Assert.hasText(userId, "请登陆系统");
        return userId;
    }
}
