package com.ansteel.core.repository;
import java.io.Serializable;   

import javax.persistence.EntityManager;   
  
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;   
import org.springframework.data.repository.core.support.RepositoryFactorySupport;   
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：项目通用数据持久层注入工厂。  
 */  
public class ProjectRepositoryFactoryBean <T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {   
  
       
    @Override  
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {   
  
        return new ProjectRepositoryFactory(em);   
    }   
}  