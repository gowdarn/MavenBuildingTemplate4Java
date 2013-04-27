package com.youdo.util.lang.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oracle.net.aso.l;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * @author shsun
 * 
 */
public class YouDoReflectorUtil {
	/**
	 * 
	 * @param source
	 * @param target
	 */
	public static void merge(Object source, Object target) {
		if (source != null && target != null) {
			PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(source);
			for (int i = 0; i < list.length; i++) {
				try {
					String name = list[i].getName();
					Object value = PropertyUtils.getProperty(source, name);
					PropertyUtils.setProperty(target, name, value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// e.printStackTrace();
				}
			}
		}
	}

	public static PropertyDescriptor[] getPropertyDescriptorsWithoutClass(Object object) {
		Map<String, PropertyDescriptor> map = getPropertyDescriptorMapWithoutClass(object);
		PropertyDescriptor[] result = new PropertyDescriptor[map.size()];
		//
		int i = 0;
		Set<Map.Entry<String, PropertyDescriptor>> set = map.entrySet();
		for (Iterator<Map.Entry<String, PropertyDescriptor>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, PropertyDescriptor> entry = (Map.Entry<String, PropertyDescriptor>) it.next();
			result[i++] = entry.getValue();
		}
		//
		return result;
	}

	public static Map<String, PropertyDescriptor> getPropertyDescriptorMapWithoutClass(Object object) {
		String CLASS = "class";
		Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(object);
		//
		for (int i = 0; i < list.length; i++) {
			if (!CLASS.equals(list[i].getName())) {
				map.put(list[i].getName(), list[i]);
			}
		}
		return map;
	}

}
