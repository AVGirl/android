package com.fff.entity;

public class SortbyGridData {
	/**viewMode
	 * 
	 * @param name
	 * @param icon
	 * @param viewMode
	 */
public SortbyGridData(String name, int icon,int viewMode[]) {
		this.name = name;
		this.icon = icon;
		this.viewMode=viewMode;
	}


/**
 * sortByMode
 * @param icon
 * @param sortByMode
 */

public SortbyGridData(int icon,String sortByMode) {
	this.icon = icon;
	this.sortByMode=sortByMode;
}
String name;int icon;int[] viewMode;String sortByMode;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getIcon() {
	return icon;
}
public void setIcon(int icon) {
	this.icon = icon;
}


public int[] getViewMode() {
	return viewMode;
}


public void setViewMode(int[] viewMode) {
	this.viewMode = viewMode;
}


public String getSortByMode() {
	return sortByMode;
}


public void setSortByMode(String sortByMode) {
	this.sortByMode = sortByMode;
}
}
