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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sangupta.jerry.security.SecurityContext;

/**
 * @author sangupta
 * @since 1.0.0
 */
public class SignedInTag extends BodyTagSupport {

	/**
	 * Generated via Eclipse
	 */
	private static final long serialVersionUID = -3870001944636210504L;

	/**
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doStartTag() throws JspException {
		if(!SecurityContext.isAnonymousUser()) {
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
	}
}
