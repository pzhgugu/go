package com.ansteel.core.domain;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ansteel.core.context.ContextHolder;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql语句操作方法。 
 */

public class SqlOperate {
	private static HibernateTemplate hibernateTemplate;

	public static HibernateTemplate getHibernateTemplate() {
		if (hibernateTemplate == null) {
			setHibernateTemplate((HibernateTemplate) ContextHolder
					.getSpringBean("hibernateTemplate"));
		}
		return hibernateTemplate;
	}

	public static void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		SqlOperate.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 使用sql 语句进行操作
	 * 
	 * @param sql
	 * 
	 * @param offset
	 * 
	 * @param length
	 * 
	 * @return List
	 */
	public static  List getListForPage(
			final String sql, final int offset, final int length) {
		List list = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(org.hibernate.Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
						//Query query = session.createSQLQuery(sql);
						query.setFirstResult(offset);
						query.setMaxResults(length);
						List list = query.list();
						return list;
					}
				});
		return list;
	}

	/**
	 * 获得集合的总数（聚合查询）
	 * 
	 * @param sql
	 * 
	 * @param offset
	 * 
	 * @param length
	 * 
	 * @return List
	 */
	public long getSaleChanceListCount(final String sql) {
		//BigInteger   result = getHibernateTemplate().execute(
		//BigDecimal  result = getHibernateTemplate().execute(
		Number result = (Number) getHibernateTemplate().execute(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(org.hibernate.Session arg0)
							throws HibernateException, SQLException {
						Query query = arg0.createSQLQuery(sql);
						return query.uniqueResult();
					}

				});
		return result.longValue();
	}
	

}
