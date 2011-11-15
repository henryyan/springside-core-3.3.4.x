/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JettyUtils.java 1185 2010-08-29 15:56:19Z calvinxiu $
 */
package org.springside.modules.test.utils;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * 创建Jetty Server的工具类.
 * 
 * @author calvin
 */
public class JettyUtils {

	/**
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server buildNormalServer(int port, String contextPath) {
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}

	/**
	 * 创建用于Functional Test的Jetty Server:
	 * 1.以src/main/webapp为Web应用目录.
	 * 2.以test/resources/web.xml指向applicationContext-test.xml创建测试环境.
	 */
	public static Server buildTestServer(int port, String contextPath) {
		Server server = buildNormalServer(port, contextPath);
		((WebAppContext) server.getHandler()).setDescriptor("src/test/resources/web.xml");
		return server;
	}
}
