package com.sangupta.jerry.web.wrapper;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Test;

public class TestByteArrayServletOutputStream {
	
	@Test
	public void testPrint() throws IOException {
		ByteArrayServletOutputStream stream = new ByteArrayServletOutputStream();
		
		stream.print(true);
		stream.print('-');
		stream.print(123.23d);
		stream.print('-');
		stream.print(234.24f);
		stream.print('-');
		stream.print(678);
		stream.print('-');
		stream.print(93l);
		stream.print('-');
		stream.print("hello");
		stream.flush();
		Assert.assertEquals("true-123.23-234.24-678-93-hello", stream.getByteArrayOutputStream().toString());
		Assert.assertEquals(31, stream.getLength());

		// other random checks
		
		Assert.assertNotNull(stream.getBytes());
		Assert.assertEquals(31, stream.getBytes().length);
		Assert.assertEquals(stream.hashCode(), stream.getByteArrayOutputStream().hashCode());
		Assert.assertFalse(stream.equals(null));
		Assert.assertTrue(stream.equals(stream));
		Assert.assertFalse(stream.equals(stream.getByteArrayOutputStream()));
		
		stream.close();
	}
	
	@Test
	public void testPrintln() throws IOException {
		ByteArrayServletOutputStream stream = new ByteArrayServletOutputStream();
		
		stream.println(true);
		stream.println('-');
		stream.println(123.23d);
		stream.println('-');
		stream.println(234.24f);
		stream.println('-');
		stream.println(678);
		stream.println('-');
		stream.println(93l);
		stream.println('-');
		stream.println("hello");
		stream.println();
		stream.flush();
		Assert.assertEquals("true\n-\n123.23\n-\n234.24\n-\n678\n-\n93\n-\nhello\n\n", stream.getByteArrayOutputStream().toString());
		Assert.assertEquals(43, stream.getLength());
		
		stream.close();
	}
	
	@Test
	public void testPrintWithWriter() throws IOException {
		ByteArrayServletOutputStream stream = new ByteArrayServletOutputStream();
		PrintWriter writer = stream.getWriter();
		
		writer.print(true);
		writer.print('-');
		writer.print(123.23d);
		writer.print('-');
		writer.print(234.24f);
		writer.print('-');
		writer.print(678);
		writer.print('-');
		writer.print(93l);
		writer.print('-');
		writer.print("hello");
		writer.flush();
		Assert.assertEquals("true-123.23-234.24-678-93-hello", stream.getByteArrayOutputStream().toString());
		Assert.assertEquals(31, stream.getLength());
		
		stream.close();
	}
	
	@Test
	public void testPrintlnWithWriter() throws IOException {
		ByteArrayServletOutputStream stream = new ByteArrayServletOutputStream();
		PrintWriter writer = stream.getWriter();
		
		writer.println(true);
		writer.println('-');
		writer.println(123.23d);
		writer.println('-');
		writer.println(234.24f);
		writer.println('-');
		writer.println(678);
		writer.println('-');
		writer.println(93l);
		writer.println('-');
		writer.println("hello");
		writer.println();
		writer.flush();
		Assert.assertEquals("true\n-\n123.23\n-\n234.24\n-\n678\n-\n93\n-\nhello\n\n", stream.getByteArrayOutputStream().toString());
		Assert.assertEquals(43, stream.getLength());
		
		stream.close();
	}
	
	@Test
	public void testWrite() throws IOException {
		ByteArrayServletOutputStream stream = new ByteArrayServletOutputStream();
		
		stream.write("hello".getBytes());
		stream.write(65);
		stream.write("hello world".getBytes(), 2, 5);
		
		Assert.assertEquals("helloAllo w", stream.getByteArrayOutputStream().toString());
		Assert.assertEquals(11, stream.getLength());
		
		stream.close();
	}
	
}
