package com.youdo.mybatis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.youdo.mybatis.query.Compare;
import com.youdo.mybatis.query.Condition;
import com.youdo.mybatis.query.Conditions;
import com.youdo.mybatis.query.Orders;
import com.youdo.util.lang.reflect.ReflectUtils;

/**
 * 鏌ヨ璇锋眰灏佽
 */
public class QueryRequest<T extends BaseDataObject> implements Serializable {

	private static final long serialVersionUID = -3423314447748128137L;


	public static final String PAGE_NUMBER = "page";
	public static final String PAGE_SIZE = "rows";
	public static final String PAGE_SORT_FIELD = "sort";
	public static final String PAGE_ORDER_TYPE = "order";

	public static final int PAGE_DEFAULT_NUMBER = 1;
	public static final int PAGE_DEFAULT_SIZE = 10;
	public static final int PAGE_MAX_SIZE = 1000;
	public static final String ORDER_ASC = " ASC";
	public static final String ORDER_DESC = " DESC";

	public static final String POJO_FIELD_MAP = "FIELD_MAP";

	public Map<String, String> sortParam = new HashMap<String, String>();
	private Object filters;
	private int pageNumber;
	private int pageSize;
	private Class<T> filtersClass;
	private String sortColumns;
	private Map<String, String> fieldMap;
	private List<String> OrdersList = new ArrayList<String>();
	private Map<String, Conditions> conditions = new HashMap<String, Conditions>();

	public QueryRequest(Map<?, ?> filters) {
		this(PAGE_DEFAULT_NUMBER, PAGE_DEFAULT_SIZE, filters);
	}

	@SuppressWarnings("unchecked")
	public QueryRequest(Class<T> filtersClass) {
		this.pageNumber = PAGE_DEFAULT_NUMBER;
		this.pageSize = PAGE_DEFAULT_SIZE;
		setFieldMap((Map<String, String>) ReflectUtils.getProperty(filtersClass, POJO_FIELD_MAP));
		setSortColumns("");
		this.filtersClass = filtersClass;
	}

	@SuppressWarnings("unchecked")
	public QueryRequest(Object filters) {
		this(PAGE_DEFAULT_NUMBER, PAGE_DEFAULT_SIZE, filters);
		try {
			setFieldMap((Map<String, String>) ReflectUtils.getProperty(filters.getClass(), POJO_FIELD_MAP));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public QueryRequest(Object filters, String sortColumns) {
		this(PAGE_DEFAULT_NUMBER, PAGE_DEFAULT_SIZE, filters, sortColumns);
		try {
			setFieldMap((Map<String, String>) ReflectUtils.getProperty(filters.getClass(), POJO_FIELD_MAP));
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public QueryRequest(Map filters, Map<String, String> fieldMap) {
		this(PAGE_DEFAULT_NUMBER, PAGE_DEFAULT_SIZE, filters, fieldMap);
	}

	public QueryRequest(int pageNumber, int pageSize, Object filters) {
		this(pageNumber, pageSize, filters, "");
	}

	public QueryRequest(int pageNumber, int pageSize, Object filters, String sortColumns) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		setFilters(filters);
		setSortColumns(sortColumns);
	}

	public QueryRequest(int pageNumber, int pageSize, Object filters, Map<String, String> fieldMap) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		setFilters(filters);
		setFieldMap(fieldMap);
	}

	public Object getFilters() {
		return filters;
	}

	public void setFilters(Object filters) {
		this.filters = filters;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	/**
	 * 
	 * @return
	 */
	public void setSortColumns(String sortColumns) {
		checkSortColumnsSqlInjection(sortColumns);
		if (sortColumns != null && sortColumns.length() > 200) {
			throw new IllegalArgumentException("sortColumns.length() <= 200 must be true");
		}
		this.sortColumns = sortColumns;
	}

	/**
	 * 
	 * @return
	 */
	public void setSortColumns(String... sortColumnsArr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sortColumnsArr.length; i++) {
			sb.append(sortColumnsArr[i]);
			if (sortColumnsArr.length - 1 > i)
				sb.append(",");
		}
		String sortColumns = sb.toString();
		checkSortColumnsSqlInjection(sb.toString());
		if (sortColumns != null && sortColumns.length() > 200) {
			throw new IllegalArgumentException("sortColumns.length() <= 200 must be true");
		}
		this.sortColumns = sortColumns;
	}

	private void checkSortColumnsSqlInjection(String sortColumns) {
		if (sortColumns == null)
			return;
		if (sortColumns.indexOf("'") >= 0 || sortColumns.indexOf("\\") >= 0) {
			throw new IllegalArgumentException("sortColumns:" + sortColumns + " has SQL Injection risk");
		}
	}

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap) {
		this.fieldMap = fieldMap;
		setDefaultConditions();
	}

	public void addOrder(Orders order) {
		order.setPropertyName(fieldMap.get(order.getPropertyName()));
		OrdersList.add(order.toString());
		String oa[] = new String[OrdersList.size()];
		this.setSortColumns(OrdersList.toArray(oa));
	}

	public Map<String, Conditions> getConditions() {
		return conditions;
	}

	/**
	 * @param propertyName
	 *            Property name for condiction
	 * @param condition
	 *            AND / OR / NOT
	 * @param compare
	 *            = > < like ...
	 * @param value
	 *            Property value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCondition(Conditions condition) {
		conditions.put(condition.getPropertyName(), condition);
		if (filters instanceof Map) {
			if (condition.getValue() != null)
				((Map) filters).put(condition.getPropertyName(), condition.getValue());
		} else {
			if (filters == null) {
				try {
					filters = filtersClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (condition.getValue() != null)
				ReflectUtils.setFieldValue(filters, condition.getPropertyName(), condition.getValue());
		}
	}

	/**
	 * @param propertyName
	 *            Property name for condiction
	 * @param condition
	 *            AND / OR / NOT
	 * @param compare
	 *            = > < like ...
	 * @param value
	 *            Property value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setCondition(String propertyName, Condition condition, Compare compare, Object value) {
		Conditions c = new Conditions(propertyName, condition, compare, value);
		conditions.put(propertyName, c);
		if (filters instanceof Map) {
			((Map) filters).put(propertyName, value);
		} else {
			if (filters == null) {
				try {
					filters = filtersClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			ReflectUtils.setFieldValue(filters, propertyName, value);
		}
	}

	private void setDefaultConditions() {
		Set<Map.Entry<String, String>> fieldMap = this.fieldMap.entrySet();
		for (Iterator<Map.Entry<String, String>> it = fieldMap.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			conditions.put(entry.getKey(), Conditions.and(entry.getValue()));
		}
	}

}
