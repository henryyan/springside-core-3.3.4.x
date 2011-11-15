package org.springside.modules.unit.test;

import static org.junit.Assert.*;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springside.modules.test.utils.WebTestUtils;

public class WebTestUtilsTest {

	@Test
	public void initByPaths() {
		MockServletContext servletContext = new MockServletContext();
		WebTestUtils.initWebApplicationContext(servletContext, "classpath:/applicationContext-core-test.xml");
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNotNull(context);
		assertNotNull(context.getBean("springContextHolder"));
	}

	@Test
	public void initByApplicationConetxt() {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:/applicationContext-core-test.xml");
		MockServletContext servletContext = new MockServletContext();
		WebTestUtils.initWebApplicationContext(servletContext, ac);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNotNull(context);
		assertNotNull(context.getBean("springContextHolder"));
	}

	@Test
	public void closeApplicationContext() {
		MockServletContext servletContext = new MockServletContext();
		WebTestUtils.initWebApplicationContext(servletContext, "classpath:/applicationContext-core-test.xml");

		WebTestUtils.closeWebApplicationContext(servletContext);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNull(context);
	}

	@Test
	public void setToStruts2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebTestUtils.setRequestToStruts2(request);
		assertEquals(request, ServletActionContext.getRequest());

		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		assertEquals(request, ServletActionContext.getRequest());

	}
}
