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

package com.sangupta.jerry.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.GsonUtils;

/**
 * Logs details of the incoming request using a {@link Logger} instance. The
 * information is logged at INFO level.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class RequestCapturingFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestCapturingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	/**
	 * 
	 * @param request
	 *            the incoming {@link ServletRequest} instance
	 * 
	 * @param response
	 *            the outgoing {@link ServletResponse} instance
	 * 
	 * @param chain
	 *            the {@link FilterChain} being executed
	 * 
	 * @throws IOException
	 *             if something fails
	 * 
	 * @throws ServletException
	 *             if something fails
	 *             
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 *      
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(LOGGER.isInfoEnabled()) {
			if(!(request instanceof HttpServletRequest)) {
				chain.doFilter(request, response);
				return;
			}
	
			HttpServletRequest hsr = (HttpServletRequest) request;
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("Request received as: {}", GsonUtils.getGson().toJson(hsr));
			}
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		// do nothing
	}

}
