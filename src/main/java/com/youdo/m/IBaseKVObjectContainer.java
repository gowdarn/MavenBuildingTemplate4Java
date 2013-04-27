package com.youdo.m;


/**
 * 
 * @author shsun
 * 
 * @param <T>
 */
public interface IBaseKVObjectContainer<T> extends IJSONArrayParsable {
	/**
	 * 
	 * @param id
	 * @param value
	 */
	public void add(T value);

	/**
	 * 
	 * @param id
	 */
	public void remove(T value);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean has(T value);

	/**
	 * 
	 * @return
	 */
	public int size();

	/**
	 * 
	 */
	public void empty();
}
