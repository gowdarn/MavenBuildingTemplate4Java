package com.youdo.m;

/**
 * 
 * @author shsun
 * 
 */
public class BaseKVObject {
	private Object key;
	private Object value;

	public BaseKVObject(Object key, Object value) {
		this.setKey(key);
		this.setValue(value);
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
