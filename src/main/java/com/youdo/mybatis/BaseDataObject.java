package com.youdo.mybatis;

import java.lang.reflect.Field;

import com.youdo.helper.BeanHelper;
import com.youdo.util.lang.reflect.ClassUtils;
import com.youdo.util.lang.StringUtils;

/**
 * 
 */
public abstract class BaseDataObject implements java.io.Serializable {

	private static final long serialVersionUID = -157500188374535260L;

	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	protected static final String TIME_FORMAT = "HH:mm:ss";

	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private String pKFieldName;

	/**
	 * 
	 */
	public BaseDataObject() {
		this(null);
	}

	/**
	 * @param pkFieldName
	 *            primary key
	 */
	public BaseDataObject(String pkFieldName) {
		this.setPKFieldName(pkFieldName);
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasPKField() {
		return !StringUtils.isEmpty(this.getPKFieldName());
	}

	/**
	 */
	public Field findPKField() {
		String pkFieldName = this.getPKFieldName();
		return ClassUtils.getField(this, pkFieldName);
	}

	/**
	 */
	public Object getPKValue() {
		Field pkField = findPKField();
		return (pkField == null) ? null : BeanHelper.getProperty(this, pkField.getName());
	}

	/**
	 * 
	 * @param pkValue
	 */
	public void setPKValue(java.io.Serializable pkValue) {
		Field pkField = findPKField();
		if (pkField != null) {
			BeanHelper.setProperty(this, pkField.getName(), pkValue);
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getPKFieldName() {
		return pKFieldName;
	}

	/**
	 * 
	 * @param pkFieldName
	 */
	public void setPKFieldName(String pkFieldName) {
		this.pKFieldName = pkFieldName;
	}

}
