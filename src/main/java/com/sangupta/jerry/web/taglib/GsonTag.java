package com.sangupta.jerry.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.sangupta.jerry.util.GsonUtils;

/**
* @author sangupta
*
*/
public class GsonTag extends SimpleTagSupport {
	
	private Object object;

	/**
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		if(this.object == null) {
			// do nothing and exit
			this.getJspContext().getOut().write("{}");
			return;
		}
		
		String json = GsonUtils.getGson().toJson(this.object);
		this.getJspContext().getOut().write(json);
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(Object object) {
		this.object = object;
	}

}
