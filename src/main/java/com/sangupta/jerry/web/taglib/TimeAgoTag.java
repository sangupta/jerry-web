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

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.sangupta.jerry.util.TimeDurationUtils;

/**
 * @author sangupta
 *
 */
public class TimeAgoTag extends SimpleTagSupport {
	
	private Date time;
	
	private long millis = -1;
	
	private long elapsed = -1;
	
	/**
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		final JspWriter out = getJspContext().getOut();
		
		if(this.time != null) {
			out.write(TimeDurationUtils.ago(this.time));
			return;
		}
		
		if(this.elapsed > 0) {
			out.write(TimeDurationUtils.ago(System.currentTimeMillis() - this.elapsed));
			return;
		}
		
		if(this.millis == 0) {
			out.print("right now");
			return;
		}

		out.write(TimeDurationUtils.ago(this.millis));
	}
	
	// Usual accessors follow

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the millis
	 */
	public long getMillis() {
		return millis;
	}

	/**
	 * @param millis the millis to set
	 */
	public void setMillis(long millis) {
		this.millis = millis;
	}

	/**
	 * @return the elapsed
	 */
	public long getElapsed() {
		return elapsed;
	}

	/**
	 * @param elapsed the elapsed to set
	 */
	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

}
