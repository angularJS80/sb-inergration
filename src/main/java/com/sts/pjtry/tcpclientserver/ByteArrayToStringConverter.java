package com.sts.pjtry.tcpclientserver;

import java.io.UnsupportedEncodingException;

import org.springframework.core.convert.converter.Converter;

/**
 * Converter used in XML configuration
 */
public class ByteArrayToStringConverter implements Converter<byte[], String> {

	private String charSet = "UTF-8";

	public String convert(byte[] bytes) {
		try {
			return new String(bytes, this.charSet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(bytes);
		}
	}

	/**
	 * @return the charSet
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

}
