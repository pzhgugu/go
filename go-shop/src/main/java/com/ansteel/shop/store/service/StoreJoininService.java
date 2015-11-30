package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreJoinin;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface StoreJoininService {
    StoreJoinin save(StoreJoinin storeJoinint, MultipartFile businessLicenceNumberFile, MultipartFile organizationCodeFile, MultipartFile generalTaxpayerFile);

    StoreJoinin save(StoreJoinin storeJoinint, MultipartFile bankLicenceElectronicFile, MultipartFile taxRegistrationCertificateElectronicFile);

    StoreJoinin save(StoreJoinin storeJoinint, String[] ids, String[] names);

    Boolean verificationSellerName(String sellerName);

    Boolean verificationStoreName(String storeName);

    StoreJoinin getCurrentUserStoreJoinin();

    StoreJoinin findOne(String id);

    StoreJoinin save(StoreJoinin storeJoinin);

    void verify(String id, String type, String joininMessage);

    /**
     * 保存付款凭证
     *
     * @param certificate
     * @param storeJoinin
     * @return
     */
    StoreJoinin savePay(MultipartFile certificate, StoreJoinin storeJoinin);
}
