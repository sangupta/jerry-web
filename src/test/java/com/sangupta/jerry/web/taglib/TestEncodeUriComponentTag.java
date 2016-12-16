package com.sangupta.jerry.web.taglib;

import org.junit.Test;

import com.sangupta.am.servlet.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;
import com.sangupta.jerry.util.UriUtils;

public class TestEncodeUriComponentTag {

	@Test
	public void testValue() {
		final String value = "a=1&b=2";
		
		AmTagLibTestHelper.testTagOutput(EncodeUriComponentTag.class, UriUtils.encodeURIComponent(value), new GenericConsumer<EncodeUriComponentTag>() {
			
			public boolean consume(EncodeUriComponentTag tag) {
				tag.setValue(value);
				return true;
			}
			
		});
	}
}
