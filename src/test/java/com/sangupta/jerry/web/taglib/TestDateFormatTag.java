package com.sangupta.jerry.web.taglib;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;

public class TestDateFormatTag {
	
	@Test
	public void testDate() {
		DateFormatTag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(DateFormatTag.class);
		Date date = new Date();
		tag.setValue(date);
		
		AmTagLibTestHelper.doTag(tag);
		String out = AmTagLibTestHelper.getJspWriter(tag).toString();
		
		Assert.assertEquals(new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), out);
	}
	
	@Test
	public void testFile() throws IOException {
		DateFormatTag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(DateFormatTag.class);
		File file = File.createTempFile("jerry-web-", ".tmp");
		tag.setValue(file);
		
		AmTagLibTestHelper.doTag(tag);
		String out = AmTagLibTestHelper.getJspWriter(tag).toString();
		
		Date date = new Date(file.lastModified());
		Assert.assertEquals(new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), out);
	}
	
	@Test
	public void testLong() {
		DateFormatTag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(DateFormatTag.class);
		Long time = System.currentTimeMillis();
		Date date = new Date(time);
		tag.setValue(date);
		
		AmTagLibTestHelper.doTag(tag);
		String out = AmTagLibTestHelper.getJspWriter(tag).toString();
		
		Assert.assertEquals(new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), out);
	}
	
	@Test
	public void testNull() {
		DateFormatTag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(DateFormatTag.class);
		tag.setValue((Date) null);
		
		AmTagLibTestHelper.doTag(tag);
		String out = AmTagLibTestHelper.getJspWriter(tag).toString();
		
		Assert.assertEquals("", out);
	}

}
