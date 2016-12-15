package com.sangupta.jerry.web.taglib;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.am.servlet2.helper.AmTagLibTestHelper;
import com.sangupta.jerry.encoder.Base62Encoder;

public class TestBase62Tag {
	
	@Test
	public void testEncode() {
		Random random = new Random();
		for(int run = 0; run < 1000; run++) {
			long value = random.nextLong();

			Base62Tag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(Base62Tag.class);
			tag.setEncode(value);
			AmTagLibTestHelper.doTag(tag);
			Assert.assertEquals(Base62Encoder.encode(value), AmTagLibTestHelper.getJspWriter(tag).toString());
		}
	}
	
	@Test
	public void testDecode() {
//		Random random = new Random();
//		for(int run = 0; run < 1000; run++) {
//			long value = random.nextLong();
//			
//			Base62Tag tag = AmTagLibTestHelper.getSimpleTagForUnitTesting(Base62Tag.class);
//			tag.setDecode(Base62Encoder.encode(value));
//			AmTagLibTestHelper.doTag(tag);
//			
//			Assert.assertEquals(String.valueOf(value), AmTagLibTestHelper.getJspWriter(tag).toString());
//		}
	}

}
