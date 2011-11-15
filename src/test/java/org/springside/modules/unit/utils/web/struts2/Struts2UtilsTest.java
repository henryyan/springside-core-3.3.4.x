package org.springside.modules.unit.utils.web.struts2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springside.modules.test.utils.WebTestUtils;
import org.springside.modules.utils.web.struts2.Struts2Utils;

import com.google.common.collect.Lists;

public class Struts2UtilsTest {

	@Test
	public void render() throws UnsupportedEncodingException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);

		Struts2Utils.render("text/plain", "hello");
		assertEquals("text/plain;charset=UTF-8", response.getContentType());
		assertEquals("no-cache, no-store, max-age=0", response.getHeader("Cache-Control"));
		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		Struts2Utils.render("text/plain", "hello", "encoding:GBK");
		assertEquals("text/plain;charset=GBK", response.getContentType());
		assertEquals("no-cache, no-store, max-age=0", response.getHeader("Cache-Control"));

		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		Struts2Utils.render("text/plain", "hello", "no-cache:false");
		assertEquals("text/plain;charset=UTF-8", response.getContentType());
		assertEquals(null, response.getHeader("Cache-Control"));
		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		Struts2Utils.render("text/plain", "hello", "encoding:GBK", "no-cache:false");
		assertEquals("text/plain;charset=GBK", response.getContentType());
		assertEquals(null, response.getHeader("Cache-Control"));
		assertEquals("hello", response.getContentAsString());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void renderJson() throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);

		//Map
		Map map = new LinkedHashMap();
		map.put("age", 10);
		map.put("name", "foo");
		Struts2Utils.renderJson(map);
		assertEquals("{\"age\":10,\"name\":\"foo\"}", response.getContentAsString());

		//Object
		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		Object object = new TestBean();
		Struts2Utils.renderJson(object);
		assertEquals("{\"age\":10,\"name\":\"foo\"}", response.getContentAsString());

		//Array
		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		TestBean[] array = { new TestBean(), new TestBean() };
		Struts2Utils.renderJson(array);
		assertEquals("[{\"age\":10,\"name\":\"foo\"},{\"age\":10,\"name\":\"foo\"}]", response.getContentAsString());

		//Collection
		response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		List<TestBean> list = Lists.newArrayList(new TestBean(), new TestBean());

		Struts2Utils.renderJson(list);
		assertEquals("[{\"age\":10,\"name\":\"foo\"},{\"age\":10,\"name\":\"foo\"}]", response.getContentAsString());
	}

	@Test
	public void renderJsonP() throws UnsupportedEncodingException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);

		Map<String, String> map = Collections.singletonMap("html", "<p>helloworld</p>");
		Struts2Utils.renderJsonp("callback", map, "no-cache:true");
		assertEquals("callback({\"html\":\"<p>helloworld</p>\"});", response.getContentAsString());

	}

	public class TestBean {
		private int age = 10;
		private String name = "foo";

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
