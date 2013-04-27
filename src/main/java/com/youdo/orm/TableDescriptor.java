package com.youdo.orm;

/**
 * 
 * @author shsun
 * 
 */
public class TableDescriptor {

	private String table;
	private String alias;

	/**
	 * 
	 * @param table
	 * @param alias
	 */
	public TableDescriptor(String table, String alias) {
		this.setTable(table);
		this.setAlias(alias);
	}

	public String getTable() {
		return table;
	}

	public void setTable( String table ) {
		this.table = table;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias( String alias ) {
		this.alias = alias;
	}

}
