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
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.DateUtils;

/**
 * This filter will add cache-control response headers to static resources
 * so that they are cached on the browser side, and the server receives lesser
 * number of calls.
 *
 * @author sangupta
 * @since 1.0.0
 */
public class LeverageBrowserCacheFilter implements Filter {

    private static final Set<String> STATIC_RESOURCE_EXTENSIONS = new HashSet<>();
    
    static {
    	STATIC_RESOURCE_EXTENSIONS.add(".css");
    	STATIC_RESOURCE_EXTENSIONS.add(".png");
    	STATIC_RESOURCE_EXTENSIONS.add(".js");
    	STATIC_RESOURCE_EXTENSIONS.add(".git");
    	STATIC_RESOURCE_EXTENSIONS.add(".jpg");
    	STATIC_RESOURCE_EXTENSIONS.add(".jpeg");
    	STATIC_RESOURCE_EXTENSIONS.add(".woff");
    	STATIC_RESOURCE_EXTENSIONS.add(".bmp");
    	STATIC_RESOURCE_EXTENSIONS.add(".tiff");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LeverageBrowserCacheFilter.class);
    
    private static String ONE_YEAR_AS_SECONDS = String.valueOf(((long) DateUtils.ONE_YEAR / 1000l));
    
    /**
	 * Add the given extension to the list of static resources.
	 * 
	 * @param extension
	 *            the extension to add
	 * 
	 * @return <code>true</code> if extension was added, <code>false</code>
	 *         otherwise
	 */
    public static boolean addStaticExtension(String extension) {
    	if(AssertUtils.isEmpty(extension)) {
    		return false;
    	}
    	
    	if(!extension.startsWith(".")) {
    		extension = "." + extension;
    	}
    	
    	extension = extension.toLowerCase();
    	
    	return STATIC_RESOURCE_EXTENSIONS.add(extension);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }

    /**
	 * Execute the filter by appending the <b>Expires</b> and
	 * <b>Cache-Control</b> response headers.
	 * 
	 * @param servletRequest
	 *            the incoming {@link ServletRequest} instance
	 * 
	 * @param servletResponse
	 *            the outgoing {@link ServletResponse} instance
	 * 
	 * @param filterChain
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
	 */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();

        // check if this is a servletRequest to a static resource, like images/css/js
        // if yes, add the servletResponse header
        boolean staticResource = isStaticResource(uri);

        filterChain.doFilter(servletRequest, servletResponse);

        // if static resources
        if(staticResource) {
            LOGGER.debug("Marking URI: {} as a static resource", uri);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addDateHeader("Expires", System.currentTimeMillis() + DateUtils.ONE_YEAR);
            response.addHeader("Cache-Control", "public, max-age=" + ONE_YEAR_AS_SECONDS);

            // turn the line below to check if a resource was cached due to this filter
            // response.addHeader("X-Filter", "LeverageBrowserCache");
        }
    }

    /**
     * Method that given a URL checks if the resources is static or not - depending on
     * the request extension like .css, .js, .png etc.
     *
     * @param uri
     * @return
     */
    private boolean isStaticResource(String uri) {
        // find extension
        int index = uri.lastIndexOf('.');
        if(index == -1) {
            return false; // no extension found
        }

        String currentExtension = uri.substring(index);
        
        return STATIC_RESOURCE_EXTENSIONS.contains(currentExtension);
    }
}
