package com.youdo.orm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author shsun
 * 
 */
public class SelectedPropertiesDescriptor {

	/**
	 * 
	 */
	private TableDescriptor tableDescriptor;
	/**
	 * 
	 */
	private List< String > properties = new ArrayList< String >();

	/**
	 * 
	 */
	private List< String > aliasedProperties = new ArrayList< String >();

	/**
	 * 
	 * @param table
	 * @param alias
	 */
	public SelectedPropertiesDescriptor(String table, String alias) {
		this.setTableDescriptor(new TableDescriptor(table, alias));
	}

	/**
	 * 
	 * @param tableDescriptor
	 */
	public SelectedPropertiesDescriptor(TableDescriptor tableDescriptor) {
		this.setTableDescriptor(tableDescriptor);
	}

	/**
	 * 
	 * @param property
	 * @return
	 */
	public boolean addProperty( String property ) {
		boolean success = false;
		if (!this.hasProperty(property)) {
			success = true;
			this.getProperties().add(property);
			this.aliasedProperties.add(this.tableDescriptor.getAlias() + "." + property);
		}
		return success;
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.getProperties().size();
	}

	/**
	 * 
	 * @param property
	 * @return
	 */
	public boolean hasProperty( String property ) {
		return this.getProperties().contains(property);
	}

	public TableDescriptor getTableDescriptor() {
		return tableDescriptor;
	}

	public void setTableDescriptor( TableDescriptor tableDescriptor ) {
		this.tableDescriptor = tableDescriptor;
	}

	public List< String > getProperties() {
		return properties;
	}

	public void setProperties( List< String > properties ) {
		this.properties = properties;
	}

	/**
	 * Get the properties which with alias. like [alias.propertyA,
	 * alias.propertyB]
	 * 
	 * @return
	 * @throws Exception
	 */

	public List< String > getAliasedProperties() {
		return this.aliasedProperties;
	}
}
