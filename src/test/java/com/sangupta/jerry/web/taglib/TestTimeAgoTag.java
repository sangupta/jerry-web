package com.sangupta.jerry.web.taglib;

import java.util.Date;

import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.consume.GenericConsumer;

public class TestTimeAgoTag {

	@Test
	public void testElapsed() {
		AmTagLibTestHelper.testTagOutput(TimeAgoTag.class, "less than a minute ago", new GenericConsumer<TimeAgoTag>() {
			
			public boolean consume(TimeAgoTag tag) {
				tag.setElapsed(1);
				return true;
			}
			
		});
	}

	@Test
	public void testMillis() {
		AmTagLibTestHelper.testTagOutput(TimeAgoTag.class, "less than a minute ago", new GenericConsumer<TimeAgoTag>() {
			
			public boolean consume(TimeAgoTag tag) {
				tag.setMillis(System.currentTimeMillis());
				return true;
			}
			
		});
		
		AmTagLibTestHelper.testTagOutput(TimeAgoTag.class, "right now", new GenericConsumer<TimeAgoTag>() {
			
			public boolean consume(TimeAgoTag tag) {
				tag.setMillis(0);
				return true;
			}
			
		});
	}

	@Test
	public void testDate() {
		AmTagLibTestHelper.testTagOutput(TimeAgoTag.class, "less than a minute ago", new GenericConsumer<TimeAgoTag>() {
			
			public boolean consume(TimeAgoTag tag) {
				tag.setTime(new Date());
				return true;
			}
			
		});
	}

}
