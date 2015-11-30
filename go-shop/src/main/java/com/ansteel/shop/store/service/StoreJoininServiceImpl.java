package com.ansteel.shop.store.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.JsonManagement;
import com.ansteel.shop.goods.domain.JsonManagementClass;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreJoinin;
import com.ansteel.shop.store.repository.StoreJoininRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
@Service
@Transactional(readOnly = true)
public class StoreJoininServiceImpl implements StoreJoininService {

    //已提交申请
    private static String JOININ_STATIC_APPLY = "10";
    //缴费完成
    private static String JOININ_STATIC_PAY = "11";
    //审核成功
    private static String JOININ_STATIC_EXMINE_YES = "20";
    //审核失败
    private static String JOININ_STATIC_EXMINE_NO = "30";
    //缴费审核失败
    private static String JOININ_STATIC_PAY_NO = "31";
    //审核通过开店
    private static String JOININ_STATIC_YES = "40";

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    StoreJoininRepository storeJoininRepository;

    @Autowired
    StoreService storeService;

    @Override
    @Transactional
    public StoreJoinin save(StoreJoinin storeJoinint, MultipartFile businessLicenceNumberFile, MultipartFile organizationCodeFile, MultipartFile generalTaxpayerFile) {
        Assert.isTrue(businessLicenceNumberFile.getSize() > 0, "请上传营业执照号电子版！");
        Assert.isTrue(businessLicenceNumberFile.getSize() < 1000000, "营业执照号电子版文件超过了1M，请编辑后重新上传！");

        Assert.isTrue(organizationCodeFile.getSize() > 0, "请上传组织机构代码证电子版！");
        Assert.isTrue(organizationCodeFile.getSize() < 1000000, "组织机构代码证电子版文件超过了1M，请编辑后重新上传！");

        Assert.isTrue(generalTaxpayerFile.getSize() > 0, "请上传一般纳税人证明！");
        Assert.isTrue(generalTaxpayerFile.getSize() < 1000000, "一般纳税人证明文件超过了1M，请编辑后重新上传！");

        try {
            Attachment bnfAtt = attachmentService.saveAttachment(businessLicenceNumberFile);
            Attachment ocfAtt = attachmentService.saveAttachment(organizationCodeFile);
            Attachment gtfAtt = attachmentService.saveAttachment(generalTaxpayerFile);

            storeJoinint.setBusinessLicenceNumberElectronic(bnfAtt.getId());
            storeJoinint.setOrganizationCodeElectronic(ocfAtt.getId());
            storeJoinint.setGeneralTaxpayer(gtfAtt.getId());
        } catch (Exception e) {
            throw new PageException("文件保存错误：" + e.getMessage());
        }

        StoreJoinin userStoreJoinin = this.getCurrentUserStoreJoinin();

        if (userStoreJoinin == null) {
            storeJoinint.setMemberId(UserUtils.getUserId());
            storeJoinint.setMemberName(UserUtils.getUserName());
            return storeJoininRepository.save(storeJoinint);
        } else {
            storeJoinint.setId(null);
            try {
                BeanUtils.applyIf(userStoreJoinin, storeJoinint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return storeJoininRepository.save(userStoreJoinin);
        }


    }

    @Override
    @Transactional
    public StoreJoinin save(StoreJoinin storeJoinint, MultipartFile bankLicenceElectronicFile, MultipartFile taxRegistrationCertificateElectronicFile) {
        StoreJoinin userStoreJoinin = getCurrentUserStoreJoinin();
        Assert.notNull(userStoreJoinin, "请先执行第一步！");

        Assert.isTrue(bankLicenceElectronicFile.getSize() > 0, "请上传开户银行许可证电子版！");
        Assert.isTrue(bankLicenceElectronicFile.getSize() < 1000000, "开户银行许可证电子版文件超过了1M，请编辑后重新上传！");

        Assert.isTrue(taxRegistrationCertificateElectronicFile.getSize() > 0, "请上传税务登记证号电子版！");
        Assert.isTrue(taxRegistrationCertificateElectronicFile.getSize() < 1000000, "税务登记证号电子版文件超过了1M，请编辑后重新上传！");

        try {
            Attachment bleAtt = attachmentService.saveAttachment(bankLicenceElectronicFile);
            Attachment trceAtt = attachmentService.saveAttachment(taxRegistrationCertificateElectronicFile);

            storeJoinint.setBankLicenceElectronic(bleAtt.getId());
            storeJoinint.setTaxRegistrationCertificateElectronic(trceAtt.getId());
        } catch (Exception e) {
            throw new PageException("文件保存错误：" + e.getMessage());
        }

        storeJoinint.setId(null);
        try {
            BeanUtils.applyIf(userStoreJoinin, storeJoinint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storeJoininRepository.save(userStoreJoinin);
    }

    @Override
    @Transactional
    public StoreJoinin save(StoreJoinin storeJoinint, String[] ids, String[] names) {
        StoreJoinin userStoreJoinin = getCurrentUserStoreJoinin();
        Assert.notNull(userStoreJoinin, "请先执行第一步！");
        storeJoinint.setId(null);
        Assert.isTrue(ids.length == names.length, "经营类目,数据异常！");
        try {
            BeanUtils.applyIf(userStoreJoinin, storeJoinint);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<JsonManagement> jsonManagementList = new ArrayList<>();


        for (int i = 0; i < ids.length; i++) {
            JsonManagement jm = new JsonManagement();

            String[] idArray = ids[i].split(",");
            String[] nameArray = names[i].split(",");
            if (ids.length == 4 && idArray.length == 1) {
                List<JsonManagementClass> JsonManagementClassList = new ArrayList<>();
                for (int y = 0; y < 3; y++) {
                    try {
                        JsonManagementClass jsonManagementClass = new JsonManagementClass();
                        jsonManagementClass.setId(ids[y]);
                        jsonManagementClass.setName(names[y]);
                        JsonManagementClassList.add(jsonManagementClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                jm.setJmcs(JsonManagementClassList);
                jsonManagementList.add(jm);
                break;
            } else {
                List<JsonManagementClass> JsonManagementClassList = new ArrayList<>();
                for (int y = 0; y < idArray.length; y++) {
                    try {
                        JsonManagementClass jsonManagementClass = new JsonManagementClass();
                        jsonManagementClass.setId(idArray[y]);
                        jsonManagementClass.setName(nameArray[y]);
                        JsonManagementClassList.add(jsonManagementClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                jm.setJmcs(JsonManagementClassList);
                jsonManagementList.add(jm);
            }
        }
        userStoreJoinin.setStoreClassIds(JsonUtils.jsonFromObject(jsonManagementList));
        userStoreJoinin.setJoininState(JOININ_STATIC_APPLY);
        return storeJoininRepository.save(userStoreJoinin);
    }

    @Override
    public Boolean verificationSellerName(String sellerName) {
        List<StoreJoinin> storeJoininList = storeJoininRepository.findBySellerName(sellerName);
        if (storeJoininList.size() > 0) {
            if (storeJoininList.size() == 1) {
                StoreJoinin storeJoinin = storeJoininList.get(0);
                if (storeJoinin.getMemberId().equals(UserUtils.getUserId())) {
                    return false;
                }
            }
            return true;
        }
        List<Store> storeList = storeService.findBySellerName(sellerName);
        if (storeList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean verificationStoreName(String storeName) {
        List<StoreJoinin> storeJoininList = storeJoininRepository.findByStoreName(storeName);
        if (storeJoininList.size() > 0) {
            if (storeJoininList.size() == 1) {
                StoreJoinin storeJoinin = storeJoininList.get(0);
                if (storeJoinin.getMemberId().equals(UserUtils.getUserId())) {
                    return false;
                }
            }
            return true;
        }
        List<Store> storeList = storeService.findByStoreName(storeName);
        if (storeList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public StoreJoinin getCurrentUserStoreJoinin() {
        StoreJoinin storeJoinin = storeJoininRepository.findOneByMemberId(UserUtils.getUserId());
        return storeJoinin;
    }

    @Override
    public StoreJoinin findOne(String id) {
        return storeJoininRepository.findOne(id);
    }

    @Override
    @Transactional
    public StoreJoinin save(StoreJoinin storeJoinin) {
        return storeJoininRepository.save(storeJoinin);
    }

    @Override
    @Transactional
    public void verify(String id, String type, String joininMessage) {
        Assert.hasText(type, "验证类型不能为空！");
        StoreJoinin storeJoinin = this.findOne(id);
        storeJoinin.setJoininMessage(joininMessage);
        if (type.equals("pass")) {
            storeJoinin.setJoininState(JOININ_STATIC_EXMINE_YES);
        } else if (type.equals("fail")) {
            storeJoinin.setJoininState(JOININ_STATIC_EXMINE_NO);
        } else if (type.equals("pass_payment")) {
            storeJoinin.setJoininState(JOININ_STATIC_YES);
            this.saveStore(storeJoinin);
        } else if (type.equals("fail_payment")) {
            storeJoinin.setJoininState(JOININ_STATIC_PAY_NO);
        } else {
            throw new PageException("错误验证类型！");
        }
        this.save(storeJoinin);
    }

    /**
     * 开店成功，保存店铺
     *
     * @param storeJoinin
     */
    @Transactional
    private void saveStore(StoreJoinin storeJoinin) {
        Store store = new Store();
        store.setName(storeJoinin.getStoreName());
        store.setGradeId(storeJoinin.getSgId());
        store.setMemberName(storeJoinin.getMemberName());
        store.setMemberId(storeJoinin.getMemberId());
        store.setSellerName(storeJoinin.getSellerName());
        store.setScId(storeJoinin.getScId());
        store.setStoreCompanyName(storeJoinin.getCompanyName());
        store.setAreaId(storeJoinin.getCompanyAddress());
        store.setStoreAddress(storeJoinin.getCompanyAddressDsetail());
        store.setStoreTel(storeJoinin.getContactsPhone());
        store.setLicenseImage(storeJoinin.getBusinessLicenceNumberElectronic());
        store.setStoreState("1");
        storeService.save(store);
    }

    @Override
    @Transactional
    public StoreJoinin savePay(MultipartFile certificate, StoreJoinin storeJoinin) {
        Assert.isTrue(certificate.getSize() > 0, "请上传付款凭证！");
        Assert.isTrue(certificate.getSize() < 1000000, "付款凭证文件超过了1M，请编辑后重新上传！");

        try {
            Attachment bleAtt = attachmentService.saveAttachment(certificate);
            storeJoinin.setPayingMoneyCertificate(bleAtt.getId());
        } catch (Exception e) {
            throw new PageException("文件保存错误" + e.getMessage());
        }
        storeJoinin.setJoininState(JOININ_STATIC_PAY);
        return storeJoininRepository.save(storeJoinin);
    }

}
