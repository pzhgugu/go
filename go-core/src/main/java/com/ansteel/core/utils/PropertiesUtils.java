package com.ansteel.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties getProperties(String name) {
		InputStream inputStream = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream(name);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p;
	}

}
