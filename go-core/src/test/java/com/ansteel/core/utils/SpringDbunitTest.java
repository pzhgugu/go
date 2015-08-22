package com.ansteel.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.ansteel.core.context.ContextHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Transactional(readOnly=true)
public class SpringDbunitTest extends SpringBaseTest {
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	protected IDatabaseConnection conn;
	
	@Value("${go_pro.dbtest}")
	private String dbTestPath;
	
	private File file = null;// 数据表备份文件  

	@Before
	public void initDbunit() throws Exception {
		//conn = getDatabaseConnection();        
	}
	
	/**
	 * 备份
	 * @param fileName
	 * @throws DataSetException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void back(String dbName,String fileName) throws DataSetException, FileNotFoundException, IOException{
		// 测试前做数据备份  
       QueryDataSet queryDataSet = getQueryDataSet(conn,dbName, null);  
       //file = File.createTempFile(BACK_FILE_NAME, BACK_FILE_PREFIX);  
       file = new File(dbTestPath+fileName);
       // 备份文件    
       FlatXmlDataSet.write(queryDataSet, new FileOutputStream(file));  
	}
	
	/**
	 * 数据还原
	 * @param fileName
	 * @throws DataSetException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 * @throws MalformedURLException 
	 */
	public void recover(String fileName) throws DataSetException, DatabaseUnitException, SQLException, MalformedURLException{
		File file = new File(dbTestPath+fileName);
		this.recover(file);
	}
	
	public void recover(File file) throws DataSetException, DatabaseUnitException, SQLException, MalformedURLException{
		/*InputSource xmlSource = null;  
        try {  
            xmlSource = new InputSource(new FileInputStream(file));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
        // 解析一个文件，产生一个数据集  
        FlatXmlProducer flatXmlProducer = new FlatXmlProducer(xmlSource);  
        flatXmlProducer.setColumnSensing(true);   
        // 清楚数据库数据并插入备份数据  
       DatabaseOperation.CLEAN_INSERT.execute(conn, new FlatXmlDataSet(  
                    flatXmlProducer));*/ 
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
    	builder.setColumnSensing(true);
    	IDataSet dataSet = builder.build(file); 
    	DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
    	HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) em.getSessionFactory();
		
    	sessionFactory.getCurrentSession().flush();
	}
	
	 /** 
     * 通过dbunit获取数据查询的结果 
     * <p> 
     * 通过dbunit获取数据查询的结果,将结果封装到DataSet中，便于和我们预期的值（自定义xml中数据）比较 
     *  
     * @param conn 
     *            dbunit数据库连接对象 
     * @param resultName 
     *            结果集名，要和自定义的预期xml中的标签名一致 
     * @param querySql 
     *            你想要的查询的sql，可以为空,为空的时候是查的整个表，把整个表数据查出放到数据集中 
     * @return 
     */  
    private QueryDataSet getQueryDataSet(IDatabaseConnection conn,  
            String resultName, String querySql) {  
        if (resultName == null || resultName == "") {  
            return null;  
        }  
        QueryDataSet actual = new QueryDataSet(conn);  
        try {  
            if (querySql != null && querySql != "") {  
                actual.addTable(resultName, querySql);  
            } else {  
                actual.addTable(resultName);  
            }  
            return actual;  
        } catch (AmbiguousTableNameException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
    /** 
     * 获取dbunit数据库连接对象 
     *  
     * @param dataBaseName 
     *            数据库名称(schema) 
     * @return 
     */  
    private IDatabaseConnection getDatabaseConnection() { 
    	Connection connection = entityManager.unwrap(java.sql.Connection.class);
        try {  
            IDatabaseConnection dbUnitCon  = new DatabaseConnection(connection);  
            return dbUnitCon ;  
        } catch (CannotGetJdbcConnectionException e) {  
            e.printStackTrace();  
        } catch (DatabaseUnitException e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
    
    /** 
     * dbunit数据库连接关闭 
     */  
    private void closeConnection() {  
        if (conn != null) {  
            try {  
                conn.close();  
                conn = null;  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    /**
     * 导入测试数据
     * @param fileName
     * @throws IOException
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    public void importTestDb(String fileName) throws IOException, DatabaseUnitException, SQLException{
    	//"dbtest/user_back1.xml"
    	//File fileBack = new ClassPathResource(fileName).getFile();   
    	File fileBack = new File(dbTestPath+fileName);
    	FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
    	builder.setColumnSensing(true);
    	IDataSet dataSet = builder.build(fileBack);  
        DatabaseOperation.INSERT.execute(conn, dataSet);
        HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) em.getSessionFactory();
		
    	sessionFactory.getCurrentSession().flush();
    }
}
