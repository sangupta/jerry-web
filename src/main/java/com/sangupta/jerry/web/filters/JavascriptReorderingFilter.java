/**
 *
 * jerry - Common Java Functionality
 * Copyright (c) 2012, Sandeep Gupta
 * 
 * http://www.sangupta/projects/jerry
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sangupta.jerry.web.HttpServletResponseWrapperImpl;

/**
 * A Servlet {@link Filter} that reorganizes and pushes all JavaScript
 * files and tags to the end of the HTML. It uses JSoup as the document
 * parser to achieve the same.
 * 
 * @author sangupta
 *
 */
public class JavascriptReorderingFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptReorderingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// try and see if this request is for an HTML page
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        final boolean htmlPage = isHtmlPage(uri);

        // if this is an HTML page, get the entire contents of the page
        // in a byte-stream
        if (!htmlPage) {
            LOGGER.debug("Not an HTML page for javascript reordering: {}", uri);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // run through a wrapper response object
        HttpServletResponseWrapperImpl wrapper = new HttpServletResponseWrapperImpl((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, wrapper);

        // check if this is an HTML output
        if (!"text/html".equals(wrapper.getContentType())) {
            LOGGER.debug("Response content not HTML for javascript reordering: {}", uri);

            // just write the plain response
            // this is not HTML response
            wrapper.copyToResponse(servletResponse);
            return;
        }

        final long startTime = System.currentTimeMillis();
        LOGGER.debug("Javascript reordering candidate found: {}", uri);

        writeReorderedHtml(wrapper, servletResponse);
        final long endTime = System.currentTimeMillis();

        LOGGER.debug("Reordering javascript for url: {} took: {}ms", uri, (endTime - startTime));
	}

	private void writeReorderedHtml(HttpServletResponseWrapperImpl wrapper, ServletResponse servletResponse) throws IOException {
        // this is an HTML response
        // we must re-order javascript
        // flush the entire chunk that may need to be written
        wrapper.flushBuffer();

        final byte[] bytes = wrapper.getBytes();
        final String encoding = wrapper.getCharacterEncoding();

        String htmlCode = null;
        if (encoding != null) {
            try {
                htmlCode = new String(bytes, encoding);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Unable to encode byte response to string for encoding: " + encoding, e);
            }
        } else {
            htmlCode = new String(bytes);
        }

        if (htmlCode == null) {
            LOGGER.error("Unable to construct HTML code from byte-stream");
            wrapper.copyToResponse(servletResponse);
            return;
        }

        // parse this html code
        final Document document = Jsoup.parse(htmlCode);

        boolean jsModified = updateJavascripts(document);
        boolean cssModified = updateCSS(document);

        // has something been modified?
        if (!jsModified && !cssModified) {
            wrapper.copyToResponse(servletResponse);
            return;
        }

        // get the byte-array for this html
        byte[] alteredResponse = document.outerHtml().getBytes(encoding);

        // send back this HTML response
        ((HttpServletResponse) servletResponse).addHeader("X-Jerry-Reordered", "true");
        wrapper.copyToResponse(servletResponse, alteredResponse);
    }

    private boolean updateCSS(Document document) {
        // only switch over files inside the BODY tag
        // the ones in the HEAD are already at the right place
        Elements elements = document.body().getElementsByTag("link");
        if (elements == null || elements.size() == 0) {
            // no javascript tag found
            LOGGER.debug("No CSS-link tags found");
            return false;
        }

        final List<String> sourceFiles = new ArrayList<String>();

        for (Element element : elements) {
            String type = element.attr("type");
            if (!"text/css".equals(type)) {
                continue;
            }

            // add to our list of movement
            sourceFiles.add(element.attr("href"));

            // remove from source
            element.remove();
        }

        LOGGER.debug("Found {} CSS files to be reordered", sourceFiles.size());

        // reconstruct the HTML codebase again
        for (String sourceDocument : sourceFiles) {
            Element link = document.head().appendElement("link");
            link.attr("type", "text/css");
            link.attr("rel", "stylesheet");
            link.attr("href", sourceDocument);
        }

        return true;
    }

    private boolean updateJavascripts(Document document) {
        // reorder javascript
        Elements elements = document.getElementsByTag("script");
        if (elements == null || elements.size() == 0) {
            // no javascript tag found
            LOGGER.debug("No javascript tag found");
            return false;
        }

        final List<String> sourceFiles = new ArrayList<String>();
        final List<Boolean> sourceType = new ArrayList<Boolean>();

        for (Element element : elements) {
            // get the source attribute
            String source = element.attr("src");
            if (source != null && source.length() > 0) {
                sourceFiles.add(source);
                sourceType.add(true);
            } else {
                // not a source file
                // has code inside
                for (DataNode dn : element.dataNodes()) {
                    sourceFiles.add(dn.getWholeData());
                }
                sourceType.add(false);
            }

            // remove element from document
            element.remove();
        }

        // output debug messages
        LOGGER.debug("Found {} source files/code snippets", sourceFiles.size());

        // reconstruct the HTML codebase again
        for (int index = 0; index < sourceFiles.size(); index++) {
            String sourceDocument = sourceFiles.get(index);
            Boolean isSourcePath = sourceType.get(index);

            Element element = document.body().appendElement("script");
            if (isSourcePath) {
                element.attr("type", "text/javascript");
                element.attr("src", sourceDocument);
            } else {
                DataNode dn = new DataNode(sourceDocument, element.baseUri());
                element.appendChild(dn);
            }
        }

        return true;
    }

    private boolean isHtmlPage(String uri) {
        // find extension
        int index = uri.lastIndexOf('.');
        if (index == -1) {
            return false; // no extension found
        }

        String currentExtension = uri.substring(index);
        if (".html".equals(currentExtension)) {
            return true;
        }

        // nothing found
        return false;
    }
}
