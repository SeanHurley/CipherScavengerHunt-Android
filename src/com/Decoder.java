package com;



public class Decoder {
	/**
	 * @param encoded
	 *            This is the encoded value of the string that you must decode
	 * @return The decoded value of the encoded string
	 */
	public static String decodeLevel1(String encoded) {
		return "";
	}

	/**
	 * @param encoded
	 *            This is the encoded value of the string that you must decode
	 * @return The decoded value of the encoded string
	 */
	public static String decodeLevel2(String encoded) {
		return "";
	}

	/**
	 * @param encoded
	 *            This is the encoded value of the string that you must decode
	 * @param parity
	 *            This is the Base64 encoded value of the parity data computed
	 *            from both the the messages
	 * @return The decoded value of the encoded string calculated from the parity
	 */
	public static String decodeLevel3(String encoded, String parity) {
		// Hint - You will also need to worry about the lengths of the strings.
		// For example, if the string we are trying to decode is shorter than
		// the parameter passed above, we must know when to stop adding on
		// characters from the parity. We can determine this if we see that the
		// encoded.charAt(i) is equal to the parity.charAt(i). If this is true,
		// then the parity data was only calculated using "encoded.charAt(i)",
		// so we can terminate since we have the other string

		return "";
	}
}
