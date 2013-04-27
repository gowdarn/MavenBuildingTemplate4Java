package com;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServlet;



import oracle.net.aso.i;

/**
 * Hello world!
 * 
 */
public class App {

	private static int key = 1;
	private static Map<String, int[]> map = new HashMap<String, int[]>();

	public static void main(String[] args) {
		
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		String oneLine = scanner.nextLine();

		// verify the input-string is correct.
		if (oneLine.length() < 3) {
			System.out.println("error");
			return;
		}

		// parse input
		String[] stringInput = oneLine.split(" ");

		int n = Integer.parseInt(stringInput[0]);
		int k = Integer.parseInt(stringInput[1]);
		if (k <= 0 || n <= 0) {
			System.out.println("error");
			return;
		}
		if (k == 1) {
			System.out.println("1");
			return;
		}

		//
		distribute(k, n, null);

		// remove duplicate
		Map<String, Integer> result = new TreeMap<String, Integer>();
		Set<Map.Entry<String, int[]>> set = map.entrySet();
		for (Iterator<Map.Entry<String, int[]>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, int[]> entry = (Map.Entry<String, int[]>) it.next();
			Arrays.sort(entry.getValue());
			StringBuilder builder = new StringBuilder("");
			for (int i = 0; i < entry.getValue().length; i++) {
				builder.append(entry.getValue()[i] + " ");
			}
			result.put(builder.toString().trim(), new Integer(entry.getValue()[0]));
		}
		
		Set<String> key = result.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			System.out.println((String) it.next());
		}
	}

	/**
	 * 
	 * @param k
	 *            box number
	 * @param n
	 *            item number
	 * @param preStrategy
	 */
	public static void distribute(int k, int n, String preStrategy) {
		if (k == 1) {
			//System.out.println(preStrategy + " " + n);
			String[] tmp = (preStrategy + " " + n).split("  ");
			int[] oneResult = new int[tmp.length];
			map.put("" + (++key), oneResult);
			for (int i = 0; i < tmp.length; i++) {
				oneResult[i] = Integer.parseInt(new String(tmp[i]).trim());
			}
			return;
		}
		//
		for (int itemNum = 0; itemNum <= n; itemNum++) {
			String currentStrategy = null;
			if (preStrategy == null || preStrategy.length() == 0) {
				currentStrategy = itemNum + " ";
			} else {
				currentStrategy = preStrategy + " " + String.valueOf(itemNum) + " ";
			}
			//System.out.println("currentStrategy-->"+currentStrategy);
			distribute(k - 1, n - itemNum, currentStrategy);
		}
	}

	static int getFinalAmount(int initialAmount, String betResults) {
		if (betResults == null || "".equals(betResults))
			return initialAmount;
		long bet = 1;
		for (int i = 0; i < betResults.length(); i++) {
			if (betResults.charAt(i) == 'W') {
				initialAmount += bet;
				bet = 1;
			}
			if (betResults.charAt(i) == 'L') {
				initialAmount -= bet;
				bet = bet * 2;
				if (initialAmount < bet)
					return initialAmount;
			}
		}

		return initialAmount;
	}
	
	
}