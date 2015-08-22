package com.ansteel.core.utils;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ansteel.common.view.domain.View;
import com.ansteel.common.view.domain.ViewTree;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.common.view.service.ViewService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/spring-mvc.xml","classpath*:/spring-sec.xml" })
public class OnSessionTest extends AbstractTransactionalJUnit4SpringContextTests {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	protected EntityManagerFactory entityManagerFactory;

	protected SessionFactory sessionFactory;

	//@Before
	public void setup() {
		new ContextHolder(applicationContext);
		HibernateEntityManagerFactory em = (HibernateEntityManagerFactory) entityManagerFactory;
		sessionFactory = em.getSessionFactory();
		Session session = getSession(this.sessionFactory);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
		logger.debug("Hibernate session is bound");
	}

	protected FlushMode flushMode = FlushMode.MANUAL;

	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = sessionFactory.openSession();
		FlushMode flushMode = this.flushMode;
		if (flushMode != null) {
			session.setFlushMode(flushMode);
		}
		return session;
	}

	//@After
	public void tearDown() throws Exception {
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
				.unbindResource(sessionFactory);
		closeSession(sessionHolder.getSession(), sessionFactory);
		logger.debug("Hibernate session is closed");
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.closeSession(session);
	}

	@Autowired
	ViewService viewService;
	@Test
	public void testOnSession() {
		View view = viewService.getNameToView("Attachment");
		ViewTree tree = view.getViewTree();
		Assert.assertNotNull(tree);
	}
}
