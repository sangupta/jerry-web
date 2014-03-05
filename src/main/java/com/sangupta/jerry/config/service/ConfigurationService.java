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

package com.sangupta.jerry.config.service;

import java.util.List;

import com.sangupta.jerry.config.domain.Configuration;

/**
 * Handles the configuration of the application.
 * 
 * @author sangupta
 * @since 0.6.0
 */
public interface ConfigurationService {
	
	/**
	 * Fetch the configuration object from the data store for the given key
	 *  
	 * @param configurationID
	 * @return
	 */
	public Configuration getConfiguration(String configurationID);

	/**
	 * Fetch the config value for the given key
	 * 
	 * @param key
	 * @return
	 */
	public String getConfigurationValue(String key);
	
	/**
	 * Store the configuration pair in the data store
	 * 
	 * @param configuration
	 * @return
	 */
	public String createConfiguration(Configuration configuration);

	/**
	 * Update the configuration pair in the data store
	 * 
	 * @param configuration
	 * @return
	 */
	public boolean updateConfiguration(Configuration configuration);
	
	/**
	 * Delete the given configuration from the data store
	 * 
	 * @param configurationID
	 * @return
	 */
	public boolean deleteConfiguration(String configurationID);

	/**
	 * Get all configuration pairs from the data store
	 * 
	 * @return
	 */
	public List<Configuration> loadAllConfiguration();
	
	//-----------------------------
	// Helper methods
	//-----------------------------
	
	public boolean getBoolean(String key, boolean defaultValue);

	public int getInteger(String key, int defaultValue);
	
	public long getLong(String key, long defaultValue);
	
}
