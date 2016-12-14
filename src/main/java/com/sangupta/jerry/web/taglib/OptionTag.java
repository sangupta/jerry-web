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

/**
 * 
 * @author sangupta
 *
 */
public class OptionTag extends SimpleTagSupport {
	
	private String value;
	
	private String text;
	
	private String current;
	
	/**
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		out.write("<option ");
		
		out.write("value=\"");
		out.write(this.value);
		out.write("\" ");
		
		if(this.value.equals(this.current)) {
			out.write("selected=\"selected\" ");
		}
		
		out.write(">");
		
		out.write(this.text);
		
		out.write("</option>");
	}
	
	// Usual accessors follow

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the current
	 */
	public String getCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(String current) {
		this.current = current;
	}

}
