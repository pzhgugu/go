package com.ansteel.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.hibernate.type.Type;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体监听类，用于实体保存时加入创建人、创建时间、更新人、更新时间。 
 */
@Component
public class BaseEntityListener {

	private static final Logger logger=Logger.getLogger(BaseEntityListener.class);
	
	//持久化之前

    @PrePersist
    public void prePersist(BaseEntity entity){
    	String userName="";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			userName=userDetails.getUsername();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		entity.setCreated(new Date());
		entity.setUpdated(new Date());
		entity.setCreator(userName);
		entity.setLastUpdater(userName);
    }
    
  //修改前

    @PreUpdate
    public void preUpdate(BaseEntity entity){
    	String userName="";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			userName=userDetails.getUsername();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		entity.setUpdated(new Date());
		entity.setLastUpdater(userName);
    }
}
