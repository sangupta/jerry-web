package com.sangupta.jerry.web.taglib;

import org.junit.Test;

import com.sangupta.am.servlet.AmPrincipal;
import com.sangupta.am.servlet.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;
import com.sangupta.jerry.security.SecurityContext;

public class TestUserNameTag {

	@Test
	public void testAnonymous() {
		AmTagLibTestHelper.testTagOutput(UserNameTag.class, "anonymous", new GenericConsumer<UserNameTag>() {
			
			public boolean consume(UserNameTag tag) {
				return true;
			}
			
		});
	}
	
	@Test
	public void testPrincipal() {
		SecurityContext.setContext(new AmPrincipal("hello"));
		AmTagLibTestHelper.testTagOutput(UserNameTag.class, "hello", new GenericConsumer<UserNameTag>() {
			
			public boolean consume(UserNameTag tag) {
				return true;
			}
			
		});
		
		SecurityContext.clearPrincipal();
	}
}
