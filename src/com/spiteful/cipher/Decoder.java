package com.spiteful.cipher;

import java.lang.StringBuilder;

import android.util.Base64;

public class Decoder {
	public static String decodeLevel1(String encoded) {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<encoded.length(); i++) {
			if(i%2 == 0) {
				builder.append(encoded.charAt(i));
			}
		}
		return builder.toString();
	}

	public static String decodeLevel2(String encoded) {
		return new String(Base64.decode(encoded, Base64.DEFAULT));
	}

	public static String decodeLevel3(String encoded, String parity) {
		parity = decodeLevel2(parity);

		StringBuilder builder = new StringBuilder();
		for(int i=0; i<parity.length(); i++) {
			char char1 = i >= encoded.length() ? 0 : encoded.charAt(i);
			char char2 = parity.charAt(i);
			builder.append((char)(char1 ^ char2));
		}

		return builder.toString();
	}
}
