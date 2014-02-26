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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;
import com.sangupta.jerry.util.DateUtils;
import com.sangupta.jerry.util.ResponseUtils;
import com.sangupta.jerry.web.HttpServletResponseWrapperImpl;
import com.sangupta.jerry.web.ModifiedServletResponse;

/**
 * Servlet {@link Filter} that compresses all JS and CSS files
 * using Google closure compiler.
 * 
 * @author sangupta
 *
 */
public class JavascriptMinificationFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptMinificationFilter.class);

    private final Map<String, ModifiedServletResponse> CACHE = new HashMap<String, ModifiedServletResponse>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void destroy() {
		// do nothing
	}

	/**
	 * Compress JS and CSS files using the Google compiler.
	 * 
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        if(!uri.endsWith(".js")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // check if we have this in cache
        if(CACHE.containsKey(uri)) {
            ModifiedServletResponse cr = CACHE.get(uri);
            cr.copyToResponse(servletResponse);
            return;
        }

        // not in cache, obtain fresh response
        HttpServletResponseWrapperImpl wrapper = new HttpServletResponseWrapperImpl(servletResponse);
        filterChain.doFilter(servletRequest, wrapper);

        // flush buffer so that we can read everything
        wrapper.flushBuffer();

        // check for empty response
        byte[] bytes = wrapper.getBytes();
        if(bytes == null || bytes.length == 0) {
            wrapper.copyToResponse(servletResponse);
            return;
        }

        // check if output is of type text/javascript
        final String type = wrapper.getContentType();
        final String encoding = wrapper.getCharacterEncoding();

        LOGGER.info("Content encoding being sent to client: " + encoding);
        LOGGER.info("Content type being sent to client: " + type);

        // check if we are JS
        if(type != null && !isJavascript(type)) {
            // no JS
            // output as such
            wrapper.copyToResponse(servletResponse);
            return;
        }

        // add marker that we processed data
        wrapper.addHeader("X-Jerry-Minified", "true");

        // check if this is gzip-compressed bytes
        byte[] newBytes = bytes;
        if(isGZip(bytes)) {
            newBytes = unGZip(bytes);
        }

        // check if we have something here
        if(newBytes == null) {
            wrapper.copyToResponse(servletResponse);
            return;
        }

        // go ahead and compress this data
        String jsCode = null;
        if (encoding != null) {
            try {
                jsCode = new String(newBytes, encoding);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("Unable to encode byte response to string for encoding: " + encoding, e);
            }
        } else {
            jsCode = new String(newBytes);
        }

        // compress the code
        String compressedCode = null;
        try {
            compressedCode = compressJavascriptEmbedded(uri, jsCode);
        } catch(Exception e) {
            LOGGER.error("Unable to compress Javascript at URL: " + uri, e);
            wrapper.copyToResponse(servletResponse);
            return;
        }

        if(encoding != null) {
            bytes = compressedCode.getBytes(encoding);
        } else {
            bytes = compressedCode.getBytes();
        }

        // create CACHEABLE RESPONSE
        ResponseUtils.setCacheHeaders(wrapper, DateUtils.ONE_MONTH);

        ModifiedServletResponse cr = new ModifiedServletResponse(wrapper, bytes);
        CACHE.put(uri, cr);

        // write and move ahead
        cr.copyToResponse(servletResponse);
    }

	/**
	 * Check if the resource is javascript or not.
	 * 
	 * @param type
	 * @return
	 */
    private boolean isJavascript(String type) {
        if("text/javascript".equalsIgnoreCase(type)) {
            return true;
        }

        if("application/x-javascript".equalsIgnoreCase(type)) {
            return true;
        }

        return false;
    }

    /**
     * Compress the Javascript.
     * 
     * @param uri
     * @param code
     * @return
     */
    private String compressJavascriptEmbedded(final String uri, final String code) {
        if(code == null || code.isEmpty()) {
            return code;
        }

        int index = uri.lastIndexOf('/');
        String name = uri;
        if(index > 0) {
            name = uri.substring(index + 1);
        }

        List<SourceFile> externs = Collections.emptyList();
        List<SourceFile> inputs = Arrays.asList(SourceFile.fromCode(name, code));

        CompilerOptions options = new CompilerOptions();
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler();
        Result result = compiler.compile(externs, inputs, options);

        if (result.success) {
            return compiler.toSource();
        }

        throw new IllegalArgumentException("Unable to compress javascript");
    }

    /**
     * UnGZIP a given byte stream.
     * 
     * @param bytes
     * @return
     */
    private byte[] unGZip(byte[] bytes) {
        if(bytes == null || bytes.length == 0) {
            return bytes;
        }

        GZIPInputStream gzis = null;
        ByteArrayOutputStream baos = null;

        try {
            gzis = new GZIPInputStream(new ByteArrayInputStream(bytes));
            baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];

            while ((len = gzis.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }

            return baos.toByteArray();
        } catch(IOException e) {
            // unable to work up
            // send back null
        } finally {
            if(gzis != null) {
                try {
                    gzis.close();
                } catch(IOException e) {
                    // eat up
                }
            }

            if(baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    // eat up
                }
            }
        }

        return null;
    }

    /**
     * Check if a byte stream is GZIP compressed or not.
     * 
     * @param bytes
     * @return
     */
    private boolean isGZip(byte[] bytes) {
        if(bytes == null || bytes.length == 0) {
            return false;
        }

        // refer http://www.gzip.org/zlib/rfc-gzip.html#file-format for magic numbers
        if(bytes[0] == 31 && (bytes[1] == 0x8b || bytes[1] == -117)) {
            return true;
        }

        return false;
    }

}
