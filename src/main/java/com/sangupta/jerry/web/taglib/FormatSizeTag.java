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

package com.sangupta.jerry.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.sangupta.jerry.util.ReadableUtils;

/**
 * @author sangupta
 *
 */
public class FormatSizeTag extends SimpleTagSupport {
	
	private long bytes;
	
	/**
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if(this.bytes < 0) {
			return;
		}
		
		final JspWriter out = getJspContext().getOut();
		out.write(ReadableUtils.getReadableByteCount(bytes));
	}
	
	// Usual accessors follow

	/**
	 * @return the bytes
	 */
	public long getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

}
