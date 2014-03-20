/**
 *
 * jerry-web - Common Java Functionality
 * Copyright (c) 2012-2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/jerry
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
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.StringUtils;

/**
 * Filter that catches all uncaught exceptions so that they are not
 * visible to the end-user, logs these errors and wraps it inside a
 * {@link RuntimeException}.
 * 
 * @author sangupta
 *
 */
public class ExceptionCatchingFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatchingFilter.class);
	
	/**
	 * Whether the error should be shown to the user or not.
	 * 
	 */
	private boolean showError = false;
	
	/**
	 * The message to be thrown inside {@link RuntimeException} when wrapping up.
	 * 
	 */
	private String errorMessage = "Sorry, but something went wrong. Details are available with errorID: ";

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String error = filterConfig.getInitParameter("show.error");
		this.showError = StringUtils.getBoolean(error, this.showError);
		
		String message = filterConfig.getInitParameter("error.message");
		if(AssertUtils.isNotEmpty(message)) {
			this.errorMessage = message;
		}
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch(Throwable t) {
			String uuid = UUID.randomUUID().toString();
			
			LOGGER.error("Uncaught exception found with UUID: " + uuid, t);
			
			if(this.showError) {
				throw new RuntimeException(t);
			}
			
			throw new RuntimeException(errorMessage + "uuid");
		}
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// do nothing
	}

}
