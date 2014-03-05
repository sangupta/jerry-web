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

package com.sangupta.jerry.config.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sangupta.jerry.config.domain.Configuration;
import com.sangupta.jerry.config.service.ConfigurationService;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.StringUtils;

/**
 * Implementation of {@link ConfigurationService} that uses MongoDB as
 * the data store.
 * 
 * @author sangupta
 *
 */
public class MongoDBConfigurationServiceImpl implements ConfigurationService {
	
	/**
	 * The cache for configuration values.
	 * 
	 */
	private static final Map<String, Configuration> CACHE_STORE = new ConcurrentHashMap<String, Configuration>();
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Initialize the CACHE STORE with all values from the
	 * database 
	 */
	public void initialize() {
		loadAllConfiguration();
	}
	
	@Override
	public Configuration getConfiguration(String configurationID) {
		if(configurationID == null) {
			return null;
		}
		
		if(CACHE_STORE.containsKey(configurationID)) {
			return CACHE_STORE.get(configurationID);
		}
		
		Configuration configuration = this.mongoTemplate.findById(configurationID, Configuration.class);
		if(configuration != null) {
			CACHE_STORE.put(configurationID, configuration);
		}
		
		return configuration;
	}

	@Override
	public String getConfigurationValue(String key) {
		if(CACHE_STORE.containsKey(key)) {
			return CACHE_STORE.get(key).getValue();
		}
		
		Configuration configuration = this.mongoTemplate.findOne(new Query(Criteria.where("key").is(key)), Configuration.class);
		if(configuration == null) {
			return null;
		}
		
		CACHE_STORE.put(key, configuration);
		return configuration.getValue();
	}

	@Override
	public String createConfiguration(Configuration configuration) {
		if(configuration == null) {
			return null;
		}
		
		if(AssertUtils.isNotEmpty(configuration.getConfigID())) {
			return null;
		}
		
		// this is a must as an empty string is a valid key
		configuration.setConfigID(null);
		
		this.mongoTemplate.insert(configuration);

		CACHE_STORE.put(configuration.getKey(), configuration);
		return configuration.getConfigID();
	}

	@Override
	public boolean updateConfiguration(Configuration configuration) {
		if(configuration == null) {
			return false;
		}
		
		if(configuration.getConfigID() == null) {
			return false;
		}
		
		this.mongoTemplate.save(configuration);
		
		// remove old value
		CACHE_STORE.remove(configuration.getKey());
		
		// update new value
		CACHE_STORE.put(configuration.getKey(), configuration);
		
		// all done
		return true;
	}

	@Override
	public boolean deleteConfiguration(String configurationID) {
		Configuration configuration = getConfiguration(configurationID);
		if(configuration == null) {
			return false;
		}
		
		this.mongoTemplate.remove(configuration);
		CACHE_STORE.remove(configurationID);
		return true;
	}

	@Override
	public List<Configuration> loadAllConfiguration() {
		List<Configuration> configurations = this.mongoTemplate.findAll(Configuration.class);
		
		if(AssertUtils.isNotEmpty(configurations)) {
			for(Configuration configuration : configurations) {
				CACHE_STORE.put(configuration.getKey(), configuration);
			}
		}
		
		return configurations;
	}
	
	/**
	 * 
	 * @see com.boogle.config.service.ConfigurationService#getBoolean(java.lang.String, boolean)
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getConfigurationValue(key);
		if(AssertUtils.isEmpty(value)) {
			return defaultValue;
		}
		
		return StringUtils.getBoolean(value, defaultValue);
	}

	/**
	 * 
	 * @see com.boogle.config.service.ConfigurationService#getInteger(java.lang.String, int)
	 */
	public int getInteger(String key, int defaultValue) {
		String value = getConfigurationValue(key);
		if(AssertUtils.isEmpty(value)) {
			return defaultValue;
		}
		
		return StringUtils.getIntValue(value, defaultValue);
	}
	
	/**
	 * 
	 * @see com.boogle.config.service.ConfigurationService#getLong(java.lang.String, long)
	 */
	public long getLong(String key, long defaultValue) {
		String value = getConfigurationValue(key);
		if(AssertUtils.isEmpty(value)) {
			return defaultValue;
		}
		
		return StringUtils.getLongValue(value, defaultValue);
	}
	
	// Usual accessors follow

	/**
	 * @return the mongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * @param mongoTemplate the mongoTemplate to set
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
