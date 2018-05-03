package com.fff.FileManager;

import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.Linux;
import com.fff.tools.api;

import android.app.Activity;
import android.content.Context;

public class md {
//public final static  String sdcard="/sdcard";
public final static  String sdcard=ff.getsdpath();
public  static  String pic_cacheDir=null;

public final  static String CurrId=Linux.getCurrId();
public final static  int selectedBg=0xffC5C5C5;
public  static  Context context;  
public  static  Activity activity;
public static String dataDir;
public static String file_format_dir;
private static String cacheDir;

public static String backgroupDir;  



 public static boolean isnull(Object o){
	 return o==null;
 }



public  static  void init(Context mcontext,Activity mactivity){
	context=mcontext;
	activity=mactivity;
	dataDir = mcontext.getFilesDir().getParent();
	file_format_dir=dataDir+"/"+"file_format"; 
	
	backgroupDir=activity.getExternalCacheDir().toString()+"/backgroupDir";
	pic_cacheDir=activity.getExternalCacheDir().toString()+"/pic_cache";
}



public	static final class cfgName{ 
public	static final String mainUI_background="mainUI-background";

public	static final String search_file_contet="search_file_contet";



}

public	static final class home{ 
	public	static final String menu_del="删除";
	public	static final String menu_chooseIcon="选择图标...";
	public	static final String menu_rename="重命名";
	public	static final String menu_add="添加...";
	
	public	static String [] getmenu(){
		return api.reflect.getClsFileds(home.class, "menu_",String.class);	
	}
	
}


	//file table
public	static final class ft{
	public	static final String wjm="fileDatabase";//�ļ���
	public	static final String table_name="files";//����
	public	static final String sql="create table files(id integer primary key autoincrement"
			+ ",name text,isFile int,format text"
			+ ",modified int,rawModified text"
			+ ",length int,size text"
			+ ",icon int"
			+ ",cn_char int"
			+ ",thumb int"
			+ ",kind text"
			+ ",canRead int"
			+ ",parent text);";

	
	
	public	static final String insert="insert into files("
			+ "name,isFile,format,modified,rawModified,length,size,icon,cn_char,thumb,kind,canRead,parent"
			+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?)" 			
		;
	
	
}	

public  static	final class  mainUImsg{
public final static int browserOnItemLongClick=1001;	
public final static int browserOnBackPressed=1002;	
public final static int startActivity=1003;	
public final static int browserOnItemClick=1004;	
public final static int end_search=1005;	
public final static int openNewWindow=1006;	
}

	
	//main table
public  static final	class  mt{
	public final static String sql="create table main(id integer primary key autoincrement,name text,type text,icon int,target text)";
	//public final static String sql2="create table main(id integer primary key autoincrement,name text,type text,icon int,target text)";
	public final static String tableName="main"; 
	public final static String type_dir="dir";    
	public final static String type_file="file";    
	
	public final static class Field{ 
		public final static String target="target";  
		public final static String icon="icon";  
		public final static String name="name"; 
		
	}
	
	
	}


/**
 * ֧������ͼ�ĸ�ʽ
 * @author Administrator
 *
 */
public  static	class zcsltdgs{
	public	static final int a=R.drawable.format_apk;
	public	static final int b=R.drawable.format_image;
	public	static final int c=R.drawable.format_video;

}


public static String getLoadDir(){
	String	t=cfg.getString("mainPath");
	if(t==null){t=ff.getsdpath();};
	return t;	
}

}
