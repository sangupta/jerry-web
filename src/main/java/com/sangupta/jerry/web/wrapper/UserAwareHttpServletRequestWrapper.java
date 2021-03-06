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

package com.sangupta.jerry.web.wrapper;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class UserAwareHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	/**
	 * Internal variable to hold the principal.
	 * 
	 */
	protected Principal principal;

	/**
	 * Convenience constructor
	 * 
	 * @param request
	 *            the {@link HttpServletRequest} instance to wrap around
	 */
	public UserAwareHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * Convenience constructor
	 * 
	 * @param request
	 *            the {@link HttpServletRequest} instance to wrap around
	 * 
	 * @param principal
	 *            the user {@link Principal} to use
	 */
	public UserAwareHttpServletRequestWrapper(HttpServletRequest request, Principal principal) {
		super(request);
		this.principal = principal;
	}
	
	/**
	 * Retrieve the currently signed-in principal.
	 * 
	 */
	@Override
	public Principal getUserPrincipal() {
		return this.principal;
	}

	/**
	 * Sign-out the currently signed-in principal.
	 * 
	 */
	public void signOutPrincipal() {
		this.principal = null;
	}
}
