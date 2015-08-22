package com.ansteel.cms.news.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.ansteel.cms.news.core.NewsType;
import com.ansteel.cms.news.domain.News;
import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.cms.news.repository.NewsCategoryRepository;
import com.ansteel.cms.news.repository.NewsRepository;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.CriteriaUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.StringUtils;

@Service
@Transactional(readOnly=true)
public class NewsServiceBean implements NewsService {
	
	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	NewsCategoryService newsCategoryService;

	@Autowired
	NewsItemsService newsItemsService;

	@Override
	public Page findByCategoryIdAndNewType(String categoryId, NewsType type, Pageable pageable) {
		Page page = newsRepository.findByCategoryIdAndNewType(categoryId, type.getValue(), pageable);
		return page;
	}

	@Override
	public Page find(final Integer type, final String pid, final String order,
			final List<QueryMapping> queryList, Pageable pageable) {
		Specification<News> spec = new Specification<News>() {  			
		    public Predicate toPredicate(Root<News> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {

		    	if(StringUtils.hasText(order)){
		    		query.orderBy(cb.asc(root.get(order)));
				}else{
					query.orderBy(cb.asc(root.get("created")));
				}
		    	List<Predicate> pList =new ArrayList<Predicate>();
		    	pList.add(cb.equal(root.get("newType"), type));
		    	pList.add(cb.equal(root.get("categoryId"), pid));
		    	pList.addAll(CriteriaUtils.getQueryMappingPredicate(root, query, cb, News.class, queryList));
		    	Predicate[] pArray = pList.toArray(new Predicate[pList.size()]);
		    	query.where(cb.and(pArray));
		    	return query.getRestriction(); 		    	
		    }
		};
		return newsRepository.find(spec,pageable);
	}

	@Override
	public List treeQuery(String pid) {
		Specification spec = CriteriaUtils.getTreeSpecification(NewsCategory.class,pid);
		return newsCategoryService.findTree(spec);
	}

	public enum OperationEnum {
		publish {//发布箱
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.approve(ids,this.value());			
			}
			@Override
			public int value() {
				return 1;
			}
		}, 
		pending {//待审箱
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.approve(ids,this.value());	
			}
			@Override
			public int value() {
				return 2;
			}
		}, 
		delect {//直接删除
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.delect(ids);
			}
			@Override
			public int value() {
				return 0;
			}
		}, 
		junk {//垃圾箱
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.approve(ids,this.value());	
			}
			@Override
			public int value() {
				return 3;
			}
		},
		cream0{//精华;
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.cream(ids,this.value());
			}
			@Override
			public int value() {
				return -1;
			}
		},
		cream1{//精华;
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.cream(ids,this.value());
			}
			@Override
			public int value() {
				return 1;
			}
		},
		cream2{//精华;
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.cream(ids,this.value());
			}	
			@Override
			public int value() {
				return 2;
			}
		},
		cream3{//精华;
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.cream(ids,this.value());
			}
			@Override
			public int value() {
				return 3;
			}
		},
		top0{//非置顶
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.top(ids,this.value());
			}	
			@Override
			public int value() {
				return 0;
			}
		},
		top1{//置顶1
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.top(ids,this.value());
			}
			@Override
			public int value() {
				return 1;
			}
		},
		top2{//置顶2
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.top(ids,this.value());
			}
			@Override
			public int value() {
				return 2;
			}
		},
		top3{//置顶3
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.top(ids,this.value());
			}	
			@Override
			public int value() {
				return 3;
			}
		},
		commentN{//不允许评论
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.comment(ids,this.value());
			}	
			@Override
			public int value() {
				return 0;
			}
		},
		commentY{//允许评论
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.comment(ids,this.value());
			}
			@Override
			public int value() {
				return 1;
			}
		},
		grade0{//普通状态
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return -1;
			}
		},
		grade1{//一等级
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return 1;
			}
		},
		grade2{//2等级
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return 2;
			}
		},
		grade3{//3等级
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return 3;
			}
		},
		grade4{//4等级
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return 4;
			}
		},
		grade5{//5等级
			@Override
			public void oper(String[] ids, NewsService newsService) {
				newsService.grade(ids,this.value());
			}	
			@Override
			public int value() {
				return 5;
			}
		};
		public abstract void oper(String[] ids, NewsService newsService);
		public abstract int value();
	}

	@Override
	public void operation(String type, String[] ids) {
		OperationEnum oe = OperationEnum.valueOf(type);
		Assert.notNull(type, type+",没有此操作类型！");
		oe.oper(ids,this);
	}

	@Override
	@Transactional
	public void approve(String[] ids, int value) {
		for(String id:ids){
			newsRepository.approve(id,value);
		}		
	}

	@Override
	@Transactional
	public void delect(String[] ids) {
		for(String id:ids){
			newsItemsService.delect(id);
		}
	}

	@Override
	@Transactional
	public void cream(String[] ids, int value) {
		for(String id:ids){
			newsItemsService.cream(id,value);
		}
	}

	@Override
	@Transactional
	public void top(String[] ids, int value) {
		for(String id:ids){
			newsItemsService.top(id,value);
		}
	}

	@Override
	@Transactional
	public void comment(String[] ids, int value) {
		for(String id:ids){
			newsItemsService.comment(id,value);
		}
	}

	@Override
	@Transactional
	public void grade(String[] ids, int value) {
		for(String id:ids){
			newsItemsService.grade(id,value);
		}
	}

	@Override
	public void category(String type, String[] ids) {
		NewsCategory newsCategory = newsCategoryService.findOneByName(type);
		Assert.notNull(newsCategory, type+",没有找到此类型！");
		for(String id:ids){
			newsRepository.category(id,newsCategory.getId());
		}
	}

}
