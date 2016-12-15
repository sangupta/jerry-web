package com.sangupta.jerry.web.taglib;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;
import com.sangupta.jerry.encoder.Base62Encoder;

public class TestHexFormatTag {

	@Test
	public void testZero() {
		AmTagLibTestHelper.testTagOutput(HexFormatTag.class, "", new GenericConsumer<HexFormatTag>() {
			
			public boolean consume(HexFormatTag tag) {
				return true;
			}
			
		});
	}
	
	@Test
	public void testValue() {
		HexFormatTag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(HexFormatTag.class);
		tag.setValue("255");
		AmTagLibTestHelper.doTag(tag);
		Assert.assertEquals("0xff", AmTagLibTestHelper.getJspWriter(tag).toString().toLowerCase());
	}
	
	@Test
	public void testString() {
		AmTagLibTestHelper.testTagOutput(HexFormatTag.class, "hello", new GenericConsumer<HexFormatTag>() {
			
			public boolean consume(HexFormatTag tag) {
				tag.setValue("hello");
				return true;
			}
			
		});
	}
	
}
