package org.springside.modules.unit.security.springsecurity;

import static org.junit.Assert.*;

import java.util.List;

import mockit.Mock;
import mockit.MockClass;
import mockit.Mockit;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

import com.google.common.collect.Lists;

public class SpringSecurityUtilsTest {
	private static final String USER_NAME = "foo";

	@MockClass(realClass = SecurityContextHolder.class)
	public static class MockSecurityContextHolder {
		@Mock
		public static SecurityContext getContext() {
			SecurityContext context = new SecurityContextImpl();

			MockHttpServletRequest request = new MockHttpServletRequest();
			request.setRemoteAddr("localhost");
			List<GrantedAuthority> list = Lists.newArrayList((GrantedAuthority) new GrantedAuthorityImpl("role_foo"));
			User user = new User(USER_NAME, "bar", false, false, false, false, list);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					list);
			authentication.setDetails(new WebAuthenticationDetails(request));
			context.setAuthentication(authentication);
			return context;
		}
	}

	@MockClass(realClass = SecurityContextHolder.class)
	public static class MockSecurityContextHolderReturnNull {
		@Mock
		public static SecurityContext getContext() {
			SecurityContext context = new SecurityContextImpl();
			return context;
		}
	}

	@Test
	public void getCurrentStatus() {
		Mockit.setUpMocks(MockSecurityContextHolder.class);
		User user = SpringSecurityUtils.getCurrentUser();
		assertEquals(USER_NAME, user.getUsername());

		String userName = SpringSecurityUtils.getCurrentUserName();
		assertEquals(USER_NAME, userName);

		String ip = SpringSecurityUtils.getCurrentUserIp();
		assertEquals("localhost", ip);
		Mockit.tearDownMocks();

	}

	@Test
	public void getCurrentStatusFail() {
		Mockit.setUpMocks(MockSecurityContextHolderReturnNull.class);
		User user = SpringSecurityUtils.getCurrentUser();
		assertEquals(null, user);

		String userName = SpringSecurityUtils.getCurrentUserName();
		assertEquals("", userName);

		String ip = SpringSecurityUtils.getCurrentUserIp();
		assertEquals("", ip);
		Mockit.tearDownMocks();
	}

	@Test
	public void hasAnyRole() {
		Mockit.setUpMocks(MockSecurityContextHolder.class);

		assertTrue(SpringSecurityUtils.hasAnyRole("role_foo"));
		assertTrue(SpringSecurityUtils.hasAnyRole("role_foo", "role_bar"));
		assertFalse(SpringSecurityUtils.hasAnyRole("role_bar"));
		Mockit.tearDownMocks();
	}

	@Test
	public void saveUserDetailsToContext() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("localhost");
		List<GrantedAuthority> list = Lists.newArrayList((GrantedAuthority) new GrantedAuthorityImpl("role_foo"));
		User user = new User(USER_NAME, "bar", false, false, false, false, list);

		SpringSecurityUtils.saveUserDetailsToContext(user, request);

		assertEquals(USER_NAME, SpringSecurityUtils.getCurrentUserName());
		assertEquals("localhost", SpringSecurityUtils.getCurrentUserIp());
	}
}
