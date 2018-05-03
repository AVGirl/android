package com.fff.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper   {
	private  String sqlSstatement;
	public MySQLiteOpenHelper(Context context, String name,String sqlSstatement) {
		super(context, name, null, 5);
		this.sqlSstatement=sqlSstatement;
	}
     
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlSstatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("drop table files");
		db.execSQL(sqlSstatement);
	}

}
