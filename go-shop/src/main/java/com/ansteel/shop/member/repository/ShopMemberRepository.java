package com.ansteel.shop.member.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.member.domain.ShopMember;

/**
 * Created by Administrator on 2016/2/22.
 */
public interface ShopMemberRepository extends ProjectRepository<ShopMember,String> {
    ShopMember findOneByUserId(String userId);
}
