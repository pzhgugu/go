package com.ansteel.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;


/**
 * Created by admin on 2015/9/15.
 */
public class PageableUtils {
    /**
     * 组织分页类
     *
     * @param sortType 排序字段
     * @param curPage  当前页
     * @param size     显示条数
     * @return
     */
    public static Pageable getPageable(String sortType, Integer curPage, int size) {
        //当前页
        if (curPage == null) {
            curPage = 0;
        } else if (curPage > 0) {
            curPage = curPage - 1;
        }
        //排序
        Sort sort = null;
        if (StringUtils.hasText(sortType)) {
            sort = new Sort(Sort.Direction.ASC, sortType);
        } else {
            sort = new Sort(Sort.Direction.ASC, "updated");
        }
        //组织分页类
        Pageable pageable = new PageRequest(curPage, size, sort);
        return pageable;
    }
}
