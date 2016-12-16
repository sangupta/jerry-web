package com.sangupta.jerry.web.taglib;

import org.junit.Test;

import com.sangupta.am.servlet.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;
import com.sangupta.jerry.encoder.Base64Encoder;
import com.sangupta.jerry.util.StringUtils;

public class TestBase64Tag {
	
	@Test
	public void testEncode() {
		for(int run = 0; run < 1000; run++) {
			final String random = StringUtils.getRandomString(2048);

			AmTagLibTestHelper.testTagOutput(Base64Tag.class, Base64Encoder.encodeToString(random.getBytes(), false), new GenericConsumer<Base64Tag>() {
				
				public boolean consume(Base64Tag tag) {
					tag.setEncode(random);
					return true;
				}
				
			});
		}
	}

	@Test
	public void testDecode() {
		for(int run = 0; run < 1000; run++) {
			String random = StringUtils.getRandomString(2048);
			final String encoded = Base64Encoder.encodeToString(random.getBytes(), false);
			
			AmTagLibTestHelper.testTagOutput(Base64Tag.class, random, new GenericConsumer<Base64Tag>() {
				
				public boolean consume(Base64Tag tag) {
					tag.setDecode(encoded);
					return true;
				}
				
			});
		}
	}
}
