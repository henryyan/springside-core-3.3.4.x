package org.springside.modules.unit.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.springside.modules.utils.PropertiesUtils;

public class PropertiesUtilsTest {

	@Test
	public void multiPropertiy() throws IOException {
		Properties p = PropertiesUtils.loadProperties("classpath:/test1.properties", "classpath:/test2.properties");

		assertEquals("1", p.getProperty("p1"));
		assertEquals("10", p.getProperty("p2"));
		assertEquals("3", p.getProperty("p3"));
	}

	@Test
	public void notExistPropertiy() throws IOException {
		Properties p = PropertiesUtils.loadProperties("classpath:/notexist.properties");
		assertEquals(null, p.getProperty("notexist"));
	}
}
