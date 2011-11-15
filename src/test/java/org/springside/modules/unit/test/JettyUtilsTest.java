package org.springside.modules.unit.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springside.modules.test.utils.JettyUtils;

public class JettyUtilsTest {

	@Test
	public void buildNormalServer() {
		Server server = JettyUtils.buildNormalServer(1978, "core");

		assertEquals(1978, server.getConnectors()[0].getPort());
		assertEquals("core", ((WebAppContext) server.getHandler()).getContextPath());
		assertEquals("src/main/webapp", ((WebAppContext) server.getHandler()).getWar());
	}

	@Test
	public void buildTestServer() {
		Server server = JettyUtils.buildTestServer(1978, "core");

		assertEquals(1978, server.getConnectors()[0].getPort());
		assertEquals("core", ((WebAppContext) server.getHandler()).getContextPath());
		assertEquals("src/main/webapp", ((WebAppContext) server.getHandler()).getWar());
		assertEquals("src/test/resources/web.xml", ((WebAppContext) server.getHandler()).getDescriptor());
	}
}
