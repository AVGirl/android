package com.fff.FileManager;

import android.database.Cursor;

public class FieldIndex {
	public static int id;
	public static int icon;
	public static int modified;
	public static int size;
	public static int name;
	public static int isFile;
	public static int thumb;
	public static int kind;
	public static int canRead;
	public static int format;
	public static int parent;

	
							
	private static boolean  initialized=false;
	
	public static void init(Cursor data) {		
		if(data==null){	return;}
		
		if(initialized==true){
			return;
		}
		
		name = data.getColumnIndex("name");
		id = data.getColumnIndex("id");
		icon = data.getColumnIndex("icon");
		modified = data.getColumnIndex("modified");
		size = data.getColumnIndex("size");
		isFile=data.getColumnIndex("isFile");
		thumb=data.getColumnIndex("thumb");
		kind=data.getColumnIndex("kind");
		canRead=data.getColumnIndex("canRead");
		format=data.getColumnIndex("format");
		parent=data.getColumnIndex("parent");
		
		
		initialized=true;
	}
	

}
