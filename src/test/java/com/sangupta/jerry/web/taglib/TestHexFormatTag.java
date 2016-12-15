package com.sangupta.jerry.web.taglib;

import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;

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
		AmTagLibTestHelper.testTagOutput(HexFormatTag.class, "ff", new GenericConsumer<HexFormatTag>() {
			
			public boolean consume(HexFormatTag tag) {
				tag.setValue("255");
				return true;
			}
			
		});
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
