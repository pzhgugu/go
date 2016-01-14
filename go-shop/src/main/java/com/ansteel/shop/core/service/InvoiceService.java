package com.ansteel.shop.core.service;

import com.ansteel.shop.core.domain.Invoice;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public interface InvoiceService {
    Invoice save(Invoice invoice);

    List<Invoice> findByMemberId();

    void delete(String id);
}
