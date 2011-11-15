package org.springside.modules.unit.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.model.InitializationError;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.utils.reflection.ReflectionUtils;

public class GroupsTestRunnerTest {

	@Before
	public void before() {
		//设置系统变量值, 为DAILY,NIGHTLY 		
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "DAILY,NIGHTLY");

	}

	@Test
	@SuppressWarnings("unchecked")
	public void groupsInit() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		//Get DAILY,NIGHTLY from system properties.
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("DAILY", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));
		assertEquals("NIGHTLY", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(1));

		//Get all while system property is ""
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("all", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));
	}

	@Test
	public void isTestClassShouldRun() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);

		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class));
		assertEquals(false, GroupsTestRunner.shouldRun(TestClassBean3.class));

	}

	@Test
	public void isTestMethodShouldRun() throws InitializationError, SecurityException, NoSuchMethodException {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);

		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class.getMethod("shouldRun", new Class[] {})));
		assertEquals(false, GroupsTestRunner
				.shouldRun(TestClassBean1.class.getMethod("shouldNeverRun", new Class[] {})));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class.getMethod("shouldRun", new Class[] {})));
		assertEquals(false, GroupsTestRunner
				.shouldRun(TestClassBean3.class.getMethod("shouldNeverRun", new Class[] {})));

	}

	@Ignore
	public static class TestClassBean1 {
		@Test
		@Groups("DAILY")
		public void shouldRun() {
		}

		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}

	@Ignore
	public static class TestClassBean2 {
		@Test
		public void shouldRun() {
		}
	}

	@Ignore
	public static class TestClassBean3 {
		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}
}
