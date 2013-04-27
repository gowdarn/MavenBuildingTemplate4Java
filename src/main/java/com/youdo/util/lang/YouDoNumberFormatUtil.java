package com.youdo.util.lang;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * 
 * @author shsun
 * 
 */
public class YouDoNumberFormatUtil {
	/**
	 * 
	 * @param number
	 * @param maximumFractionDigits
	 * @return
	 */
	public static String divideWithThousandsSeparator(double number, int maximumFractionDigits) {
		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
		formatter.setMaximumFractionDigits(maximumFractionDigits);
		return formatter.format(number);
	}

	/**
	 * divide with thousands-separator but no fraction-digit.
	 * 
	 * @param number
	 * @return
	 */
	public static String divideWithSimpleThousandsSeparator(double number) {
		return divideWithThousandsSeparator(number, 0);
	}
}
