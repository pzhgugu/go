package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsBrand;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface GoodsBrandService {
    String saveAttachment(MultipartFile file,GoodsBrand goodsBrand);

    /**
     * 检测名称是否重复
     * @param goodsBrand
     * @return
     */
    boolean isNameRepeat(GoodsBrand goodsBrand);
}
