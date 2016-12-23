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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A wrapper class that encapsulates the response stream being generated
 * including the response stream, http headers, cookies etc, content type,
 * response code etc.
 * 
 * @author sangupta
 * @since 1.0.0
 */
public class HttpServletResponseWrapperImpl extends HttpServletResponseWrapper {

    final ByteArrayServletOutputStream outputStream = new ByteArrayServletOutputStream();

    final Map<String, Object> headers = new HashMap<String, Object>();

    Set<Cookie> cookies;

    int statusCode;

    String charset;

    String contentType;

    String characterEncoding;

    Locale locale;

    /**
	 * Create an instance of this wrapper from the given {@link ServletResponse}
	 * instance, if the actual incoming instance is {@link HttpServletResponse}
	 * 
	 * @param response
	 *            the {@link ServletResponse} instance
	 * 
	 */
    public HttpServletResponseWrapperImpl(ServletResponse response) {
        this((HttpServletResponse) response);
    }

    /**
	 * Create an instance of this wrapper from the given
	 * {@link HttpServletResponse} instance
	 * 
	 * @param response
	 *            the {@link HttpServletResponse} instance
	 */
    public HttpServletResponseWrapperImpl(HttpServletResponse response) {
        super(response);

        // copy current values
        this.locale = response.getLocale();
        this.characterEncoding = response.getCharacterEncoding();
        this.contentType = response.getContentType();
    }

    public byte[] getBytes() {
        return this.outputStream.getBytes();
    }

    /**
	 * Copy this response to the given {@link ServletResponse} instance.
	 * 
	 * @param response
	 *            the {@link ServletResponse} instance to copy this response to
	 * 
	 * @throws IOException
	 *             if anything fails
	 */
    public void copyToResponse(ServletResponse response) throws IOException {
        this.copyToResponse((HttpServletResponse) response);
    }

    /**
	 * Copy the altered response to the given {@link ServletResponse} instance.
	 * 
	 * @param response
	 *            the {@link ServletResponse} instance to copy this response to
	 * 
	 * @param alteredResponse
	 *            the altered response to write
	 * 
	 * @throws IOException
	 *             if anything fails
	 */
    public void copyToResponse(ServletResponse response, byte[] alteredResponse) throws IOException {
        this.copyToResponse((HttpServletResponse) response, alteredResponse);
    }

    /**
	 * Copy this response with the given {@link HttpServletResponse} instance.
	 * 
	 * @param response
	 *            the {@link HttpServletResponse} instance to write to
	 * 
	 * @throws IOException
	 *             if anything fails
	 */
    public void copyToResponse(HttpServletResponse response) throws IOException {
        this.copyToResponse(response, null);
    }

    /**
     * Copy the altered response to the given {@link HttpServletResponse} instance.
	 * 
	 * @param response
	 *            the {@link HttpServletResponse} instance to copy this response to
	 * 
	 * @param alteredResponse
	 *            the altered response to write
	 * 
	 * @throws IOException
	 *             if anything fails
     */
    public void copyToResponse(HttpServletResponse response, byte[] alteredResponse) throws IOException {
        // character encoding
        if(this.characterEncoding != null) {
            response.setCharacterEncoding(this.characterEncoding);
        }

        // charset
        if(this.charset != null) {
            response.setCharacterEncoding(charset);
        }

        // content type
        if(this.contentType != null) {
            response.setContentType(this.contentType);
        }

        // set the status
        if(this.statusCode > 0) {
            response.setStatus(this.statusCode);
        }

        // set the locale
        if(this.locale != null) {
            response.setLocale(this.locale);
        }

        // copy and add all cookies
        if(this.cookies != null) {
            for(Cookie cookie : this.cookies) {
                response.addCookie(cookie);
            }
        }

        // copy the byte array stream and also set its length
        final OutputStream finalOut = response.getOutputStream();
        if(alteredResponse == null) {
            int length = this.outputStream.getLength();
            response.setContentLength(length);

            // write the entire byte chunk
            this.outputStream.getByteArrayOutputStream().writeTo(finalOut);
        } else {
            response.setContentLength(alteredResponse.length);
            finalOut.write(alteredResponse);
        }

        finalOut.flush();
    }

    // ----------------------------------------------
    // Overridden methods for the wrapper start here
    // ----------------------------------------------

    /***
     * @see javax.servlet.http.HttpServletResponseWrapper#addCookie(javax.servlet.http.Cookie)
     */
    @Override
    public void addCookie(Cookie cookie) {
        super.addCookie(cookie);
        if(this.cookies == null) {
            this.cookies = new HashSet<Cookie>();
        }

        this.cookies.add(cookie);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
     */
    @Override
    public void setStatus(int sc) {
        super.setStatus(sc);
        this.statusCode = sc;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int, java.lang.String)
     */
    @Override
    public void setStatus(int sc, String sm) {
        super.setStatus(sc, sm);
        this.statusCode = sc;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int)
     */
    @Override
    public void sendError(int sc) throws IOException {
        super.sendError(sc);
        this.statusCode = sc;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int, java.lang.String)
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        super.sendError(sc, msg);
        this.statusCode = sc;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addDateHeader(java.lang.String, long)
     */
    @Override
    public void addDateHeader(String name, long date) {
        super.addDateHeader(name, date);
        this.headers.put(name, date);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        this.headers.put(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#addIntHeader(java.lang.String, int)
     */
    @Override
    public void addIntHeader(String name, int value) {
        super.addIntHeader(name, value);
        this.headers.put(name, value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#containsHeader(java.lang.String)
     */
    @Override
    public boolean containsHeader(String name) {
        return this.headers.containsKey(name);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setDateHeader(java.lang.String, long)
     */
    @Override
    public void setDateHeader(String name, long date) {
        super.setDateHeader(name, date);
        this.headers.put(name, date);
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#setCharacterEncoding(java.lang.String)
     */
    @Override
    public void setCharacterEncoding(String charset) {
        super.setCharacterEncoding(charset);
        this.charset = charset;
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#setContentType(java.lang.String)
     */
    @Override
    public void setContentType(String type) {
        super.setContentType(type);
        this.contentType = type;
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#getContentType()
     */
    @Override
    public String getContentType() {
        return this.contentType;
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
        this.headers.put(name,  value);
    }

    /**
     * @see javax.servlet.http.HttpServletResponseWrapper#setIntHeader(java.lang.String, int)
     */
    @Override
    public void setIntHeader(String name, int value) {
        super.setIntHeader(name, value);
        this.headers.put(name, value);
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#setLocale(java.util.Locale)
     */
    @Override
    public void setLocale(Locale loc) {
        super.setLocale(loc);
        this.locale = loc;
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#getLocale()
     */
    @Override
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#getOutputStream()
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#getWriter()
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        return this.outputStream.getWriter();
    }

    /**
     * @see javax.servlet.ServletResponseWrapper#flushBuffer()
     */
    @Override
    public void flushBuffer() throws IOException {
        this.outputStream.flush();
    }

}
