package com.ansteel.dymodel.test;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

import com.ansteel.core.utils.FileUtils;

public class GroovyTest {
	
	public String generator() throws UnsupportedEncodingException, FileNotFoundException {
		return FileUtils.readFile("E:/myeclips/go/go-core/src/test/java/com/ansteel/dymodel/test/NewFile.xml");
    }

	@Test
	public void test()  {
		//ClassLoader parent = getClass().getClassLoader();
        //GroovyClassLoader loader = new GroovyClassLoader(parent);
        //String st = generator();
        //Class groovyClass = loader.parseClass(st);
       // System.out.println(groovyClass);
       // Configuration config = new Configuration().configure().setProperty(Environment.FORMAT_SQL, "true").addResource("com/bubulah/dao/TCustomer.hbm.xml");

	}

}
