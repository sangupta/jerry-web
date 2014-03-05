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

package com.sangupta.jerry.config.domain;

import org.springframework.data.annotation.Id;

import com.sangupta.jerry.util.AssertUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Holds a pair of configuration values.
 * 
 * @author sangupta
 * @since 0.6.0
 */
@XStreamAlias("configuration")
public class Configuration {
	
	/**
	 * Primary key for the database
	 */
	@Id
	private String configID;
	
	/**
	 * The key for this configuration pair
	 */
	private String key;
	
	/**
	 * The value of the configuration pair
	 */
	private String value;
	
	/**
	 * Whether the value can be edited or not
	 */
	private boolean readOnly;
	
	/**
	 * Default constructor
	 */
	public Configuration() {
		
	}
	
	/**
	 * Convenience constructor
	 * 
	 * @param key
	 * @param value
	 */
	public Configuration(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Configuration)) {
			return false;
		}
		
		Configuration c = (Configuration) obj;
		return this.key.equals(c.key);
	}
	
	@Override
	public int hashCode() {
		return this.key.hashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + this.key + "::" + this.value + "]";
	}
	
	// Usual accessors follow

	/**
	 * @return the configID
	 */
	public String getConfigID() {
		return configID;
	}

	/**
	 * @param configID the configID to set
	 */
	public void setConfigID(String configID) {
		// this check is needed because an empty string is a valid ID
		if(AssertUtils.isEmpty(configID)) {
			configID = null;
		}
		
		this.configID = configID;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

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
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

}
