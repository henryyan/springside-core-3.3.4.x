package org.springside.modules.unit.utils.encode;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springside.modules.utils.encode.EncodeUtils;

public class EncodeUtilsTest {

	@Test
	public void hexEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.hexEncode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.hexDecode(result)));
	}

	@Test
	public void base64Encode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.base64Encode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.base64Decode(result)));
	}

	@Test
	public void base64UrlSafeEncode() {
		String input = "haha,i am a very long message";
		String result = EncodeUtils.base64UrlSafeEncode(input.getBytes());
		assertEquals(input, new String(EncodeUtils.base64Decode(result)));
	}

	@Test
	public void urlEncode() {
		String input = "http://locahost/?q=中文";
		String result = EncodeUtils.urlEncode(input);
		System.out.println(result);

		assertEquals(input, EncodeUtils.urlDecode(result));
	}

	@Test
	public void xmlEncode() {
		String input = "1>2";
		String result = EncodeUtils.xmlEscape(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtils.xmlUnescape(result));
	}

	@Test
	public void html() {
		String input = "1>2";
		String result = EncodeUtils.htmlEscape(input);
		assertEquals("1&gt;2", result);
		assertEquals(input, EncodeUtils.htmlUnescape(result));
	}
}
