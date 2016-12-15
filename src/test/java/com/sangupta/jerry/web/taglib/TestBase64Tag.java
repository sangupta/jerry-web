package com.sangupta.jerry.web.taglib;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.encoder.Base64Encoder;
import com.sangupta.jerry.util.StringUtils;

public class TestBase64Tag {
	
	@Test
	public void testEncode() {
		for(int run = 0; run < 1000; run++) {
			String random = StringUtils.getRandomString(2048);

			Base64Tag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(Base64Tag.class);
			tag.setEncode(random);
			AmTagLibTestHelper.doTag(tag);
			
			Assert.assertEquals(Base64Encoder.encodeToString(random.getBytes(), false), AmTagLibTestHelper.getJspWriter(tag).toString());
		}
	}

	@Test
	public void testDecode() {
		for(int run = 0; run < 1000; run++) {
			String random = StringUtils.getRandomString(2048);
			String encoded = Base64Encoder.encodeToString(random.getBytes(), false);
			
			Base64Tag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(Base64Tag.class);
			tag.setDecode(encoded);
			AmTagLibTestHelper.doTag(tag);
			
			Assert.assertEquals(random, AmTagLibTestHelper.getJspWriter(tag).toString());
		}
	}
}
