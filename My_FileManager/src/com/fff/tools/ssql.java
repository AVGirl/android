package com.fff.tools;

import com.fff.misc.ff;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ssql {
 public static SQLiteDatabase db;
private static Cursor c;

  
public static void setDB(SQLiteDatabase db2132){
	db=db2132;
}

  
public static String queryString(String sql,String defRetrun,SQLiteDatabase...db2){
	if(db2!=null && db2.length!=0){
		  c=db2[0].rawQuery(sql,null);
	}else{
		c=db.rawQuery(sql,null);
	}
	c.moveToFirst();
	String jg=null;
	if(c.getCount()>0){
	
	jg	=c.getString(0);
		
	}

	
	
	
	if(jg==null){
		return defRetrun;
	}
	
	
	
	return jg;
}

  
//������ѯ
public static String tjcx(String field,String table,String where){
	String sql="select "+field+" from "+table+" where "+where;
	c=db.rawQuery(sql,null);
	c.moveToFirst();
	String jg=null;
	if(c.getCount()>0){
	jg	=c.getString(0);
		
	}

	return jg;
}

public static String queryString(String sql,SQLiteDatabase...db2){
	if(db2!=null && db2.length!=0){
		  c=db2[0].rawQuery(sql,null);
	}else{
		c=db.rawQuery(sql,null);
	}
	c.moveToFirst();
	String jg=null;
	if(c.getCount()>0){
	jg	=c.getString(0);
		
	}

	return jg;
}


public static int queryInt(String sql,SQLiteDatabase...db2){
	if(db2!= null && db2.length!=0){
		  c=db2[0].rawQuery(sql,null);
	}else{
		c=db.rawQuery(sql,null);
	}
	c.moveToFirst();
	int jg = 0;
	if(c.getCount()>0){
	jg	=c.getInt(0);
		
	}	
	return jg;
}



public static String queryTable(String sql,SQLiteDatabase...db2){
sql="select * from "+sql;
	if(db2!=null && db2.length!=0){

		  c=db2[0].rawQuery(sql,null);
	}else{
		c=db.rawQuery(sql,null);
	}
	
	 StringBuilder sb=new StringBuilder();
		int ls=c.getColumnCount();//����

	 while (c.moveToNext()){
		 
		for(int i=0; i< ls ;i++){
			sb.append(c.getString(i)+" - ");
		}
		 sb.append("\n");
	 }
	 
	 return sb.toString();
	
}
	
 
public static String query(String sql,SQLiteDatabase...db2){
	
//ff.log("dsf");
	if(db2!=null && db2.length!=0){
		  c=db2[0].rawQuery(sql,null);
	}else{

		c=db.rawQuery(sql,null);
	}
	
	 StringBuilder sb=new StringBuilder();
		int ls=c.getColumnCount();//����
	 while (c.moveToNext()){
		 
		for(int i=0; i< ls ;i++){
			sb.append(c.getString(i)+" - ");
		}
		 sb.append("\n");
	 }
	 
	 return sb.toString();
	
}
	
	
}
