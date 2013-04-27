package com.youdo.util.lang;

import java.util.List;

/**
 * 
 * @author shsun
 * 
 */
public class YouDoListUtil {
	/**
	 * 
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public static void removeDuplicateElements(List list) {
		if (list != null) {
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = list.size() - 1; j > i; j--) {
					if (list.get(j).equals(list.get(i))) {
						list.remove(j);
					}
				}
			}
		}
	}

}
