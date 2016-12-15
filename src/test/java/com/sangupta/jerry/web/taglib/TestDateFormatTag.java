package com.sangupta.jerry.web.taglib;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;

public class TestDateFormatTag {
	
	@Test
	public void testDate() {
		final Date date = new Date();
		
		AmTagLibTestHelper.testTagOutput(DateFormatTag.class, new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), new GenericConsumer<DateFormatTag>() {
			
			public boolean consume(DateFormatTag tag) {
				tag.setValue(date);
				return true;
			}
			
		});
	}
	
	@Test
	public void testFile() throws IOException {
		final File file = File.createTempFile("jerry-web-", ".tmp");
		Date date = new Date(file.lastModified());
		
		AmTagLibTestHelper.testTagOutput(DateFormatTag.class, new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), new GenericConsumer<DateFormatTag>() {
			
			public boolean consume(DateFormatTag tag) {
				tag.setValue(file);
				return true;
			}
			
		});
	}
	
	@Test
	public void testLong() {
		final Long time = System.currentTimeMillis();
		Date date = new Date(time);
		
		AmTagLibTestHelper.testTagOutput(DateFormatTag.class, new SimpleDateFormat("EEE, MMM dd yyyy, HH:mm:ss z").format(date), new GenericConsumer<DateFormatTag>() {
			
			public boolean consume(DateFormatTag tag) {
				tag.setValue(time);
				return true;
			}
			
		});
	}
	
	@Test
	public void testNull() {
		AmTagLibTestHelper.testTagOutput(DateFormatTag.class, "", new GenericConsumer<DateFormatTag>() {
			
			public boolean consume(DateFormatTag tag) {
				tag.setValue((Date) null);
				return true;
			}
			
		});
	}

}
