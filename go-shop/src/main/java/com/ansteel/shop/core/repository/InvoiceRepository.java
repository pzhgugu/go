package com.ansteel.shop.core.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.core.domain.Invoice;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public interface InvoiceRepository extends ProjectRepository<Invoice,String> {
    List<Invoice> findByMemberId(String userId);
}
