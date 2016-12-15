package com.sangupta.jerry.web.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.AmPrincipal;
import com.sangupta.am.servlet2.helper.AmBodyTagEvaluationResult;
import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.security.SecurityContext;

public class TestSignedInTag {

	@Test
	public void testAnonymous() {
		SignedInTag tag = AmTagLibTestHelper.getBodyTagForUnitTesting(SignedInTag.class);
		AmBodyTagEvaluationResult result = AmTagLibTestHelper.doTag(tag);
		
		Assert.assertEquals(Tag.SKIP_BODY, result.startTagResult);
	}
	
	@Test
	public void testSignedIn() {
		// check for signed in user
		SecurityContext.setContext(new AmPrincipal());
		
		SignedInTag tag = AmTagLibTestHelper.getBodyTagForUnitTesting(SignedInTag.class);
		AmBodyTagEvaluationResult result = AmTagLibTestHelper.doTag(tag);
		
		Assert.assertEquals(Tag.EVAL_BODY_INCLUDE, result.startTagResult);
	}
}
