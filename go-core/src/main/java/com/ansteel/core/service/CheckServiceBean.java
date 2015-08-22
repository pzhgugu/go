package com.ansteel.core.service;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据校验接口实现。  
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.ansteel.core.query.QuerySettings;
import com.ansteel.core.utils.StringUtils;

@Transactional(readOnly=true)
@Service
public class CheckServiceBean implements CheckService {
	
	@Autowired
	BaseService baseService;

	@Override
	public <T extends OperEntity> boolean isNameRepeat(T entity) {
		String id = entity.getId();
		OperEntity o = baseService.findOneByName(entity.getClass(), entity.getName());
		if(o==null){
			return false;
		}
		if(StringUtils.hasText(id)){
			if(o.getId().equals(id)){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public <T extends OperEntity> boolean isNodeNameRepeat(T entity,
			String nodeName, String nodeId) {
		List<OperEntity> list = baseService.findByNameNode(entity.getClass(),entity.getName(),nodeName,nodeId);
		String id = entity.getId();
		if(list.size()>0){
			OperEntity o=list.get(0);
			if(StringUtils.hasText(id)){
				if(o.getId().equals(id)){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	}

}
