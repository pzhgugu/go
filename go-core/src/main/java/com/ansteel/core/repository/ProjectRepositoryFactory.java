package com.ansteel.core.repository;

import static org.mockito.Mockito.*;   

import java.io.Serializable;

import javax.persistence.EntityManager;   
  
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.support.JpaEntityInformation;   
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;   
import org.springframework.data.repository.core.RepositoryMetadata; 
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：项目通用数据持久层注入接口。  
 */
public class ProjectRepositoryFactory extends JpaRepositoryFactory {   
  
    public ProjectRepositoryFactory(EntityManager entityManager) {   
        super(entityManager);   
    }   

    @SuppressWarnings("unchecked")   
    protected JpaRepository<?, ?> getTargetRepository(RepositoryMetadata metadata, EntityManager em) {   
  
        JpaEntityInformation<Object, Serializable> entityMetadata = mock(JpaEntityInformation.class);   
        when(entityMetadata.getJavaType()).thenReturn((Class<Object>) metadata.getDomainType());  
        when(entityMetadata.getEntityName()).thenReturn(metadata.getDomainType().getName()); 
        return new SimpleProjectRepository<Object, Serializable>(entityMetadata, em);   
    }   
  
    /*  
     * (non-Javadoc)  
     *   
     * @see  
     * org.springframework.data.repository.support.RepositoryFactorySupport# 
     * getRepositoryBaseClass() 
     */  
    @Override  
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {   
  
        return SimpleProjectRepository.class;   
    }   
}  
