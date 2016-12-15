/**
 *
 * jerry-web: Common Servlet/JSP Functionality
 * Copyright (c) 2012-2016, Sandeep Gupta
 * 
 * https://sangupta.com/projects/jerry-web
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.jerry.web.taglib;

import javax.servlet.jsp.tagext.Tag;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.AmPrincipal;
import com.sangupta.am.servlet2.helper.AmBodyTagEvaluationResult;
import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.security.SecurityContext;

public class TestAnonymousTag {

	@Test
	public void testAnonymous() {
		AnonymousTag tag = AmTagLibTestHelper.getBodyTagForUnitTesting(AnonymousTag.class);
		AmBodyTagEvaluationResult result = AmTagLibTestHelper.doTag(tag);
		
		Assert.assertEquals(Tag.EVAL_BODY_INCLUDE, result.startTagResult);
	}
	
	@Test
	public void testSignedIn() {
		// check for signed in user
		SecurityContext.setContext(new AmPrincipal());
		
		AnonymousTag tag = AmTagLibTestHelper.getBodyTagForUnitTesting(AnonymousTag.class);
		AmBodyTagEvaluationResult result = AmTagLibTestHelper.doTag(tag);
		
		Assert.assertEquals(Tag.SKIP_BODY, result.startTagResult);
		
		SecurityContext.clearPrincipal();
	}
}
