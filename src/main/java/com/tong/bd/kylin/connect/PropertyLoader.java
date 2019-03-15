package com.tong.bd.kylin.connect;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Description:
 *
 * @Author: weicaijia
 * Date: 2017/12/8
 * Time: 下午 04:48
 */
public class PropertyLoader {
	private Properties props;
	private String propertyName;

	public PropertyLoader(String fileName) {
		this.propertyName = fileName;
		init();
	}

	public void init() {
		InputStream inputStream = null;
		try {
			inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(propertyName);
			props = new Properties();
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Properties getProps() {
		return props;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String get(String key) {
		return props.getProperty(key);
	}

	public void writeProperty(String key, String value) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(PropertyLoader.class.getClassLoader().getResource(propertyName).getFile());
			props.setProperty(key, value);
			props.store(outputStream, key);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
