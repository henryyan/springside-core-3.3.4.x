/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: IgnorePrefixReverseEngineeringStrategy.java 1184 2010-08-29 03:17:10Z calvinxiu $
 */
package org.springside.modules.orm.hibernate;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * Hibernate Tools从数据库逆向生成Entity POJO时, Class名去除表名中的前缀的策略.
 * 
 * 如T_ACCT_USER默认的Class名为TAcctUser,忽略的前缀长度为5时,处理结果为User.
 * 
 * 注意必须继承子类以设定前缀的长度.
 * 
 * @author calvin
 */
public abstract class IgnorePrefixReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

	public IgnorePrefixReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String delegateResult = super.tableToClassName(tableIdentifier);
		return ignorePrefix(delegateResult);
	}

	protected String ignorePrefix(String delegateResult) {
		int lastDotIndex = delegateResult.lastIndexOf('.');

		String packageName = delegateResult.substring(0, lastDotIndex + 1);
		String className = delegateResult.substring(lastDotIndex + getPrefixLength() + 1);

		String fullClassName = packageName + className;
		return fullClassName;
	}

	/**
	 * 在子类重载设定表名的前缀长度.
	 */
	protected abstract int getPrefixLength();
}
