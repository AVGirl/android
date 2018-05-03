package com.fff.FileManager;

import java.io.File;
import java.io.FileFilter;

import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReadDir {
	public   static final FileFilter_notShowHiddenFiles fileFilter_notShowHiddenFiles = new FileFilter_notShowHiddenFiles();

	//________________________________________
  public static   boolean readDir(String path,SQLiteDatabase db,boolean insertParent,FileFilter fileFilter){
		AsyncLoad.stop();
		
		if(path==null){ff.sc("f(path==null)"); return false ;}
	
		String a="";

		  
		  
		File fileList[]=null ;
		if(!path.startsWith(md.sdcard)){
			String q1=su2.exec("cd "+"'"+path+"'\n"+"ls");  
			String q2=su2.exec("cd "+"'"+path+"'\n"+"ls -d */ ");
			fileList=FileParse.parseSimple(q1,q2);		

		}  

		
		if(fileList!=null){
			
		}else{
			File f5= new File(path);
			if(f5.exists()==false){ff.sc("f5.exists()==false"); return false ;}		
		  
			if(md.isnull(fileFilter)){
				 fileList=f5.listFiles();
			}else{
				fileList=f5.listFiles(fileFilter);
			}
		
			if(fileList==null){ff.sc(jj.mContext, "null dir"); return false ;}	
			
		}
		
		
		if(insertParent==false){
			path=null;
		}
	
		
		//ff.sc("fileList.length",fileList.length);

		for(int i=0; i< fileList.length ;i++){
			File f =fileList[i];
		

			int canRead=0;
			int isFile=0;
			String size="";  
			String name= f.getName();	
			String format=FileOperation.getFileType2(name);		
			long rawModified=f.lastModified();
			String modified= FileOperation.getFileModefied(rawModified);
			long length=0;
			int icon=0;
			int cn_char=name.charAt(0)>255?1:0;
			int thumb=0;
			String kind[]=new String[1];
		//	String parent=null;
			
		
			
			if(f.canRead())canRead=1;
		//	if(insertParent)parent=path;
			
				
			if(f.isFile()){
				icon=FileOperation.getFileTypeIcon(format,kind);
				length=f.length();
				isFile=1;
				size=FileOperation.getFileSize(length);	
				thumb=icon==md.zcsltdgs.a?1:(icon==md.zcsltdgs.b)?1:(icon==md.zcsltdgs.c)?1:0;		

				if(icon==R.drawable.format_unkown){
					format="0";
				}
				  
			}else{
				icon=R.drawable.format_folder;
				kind[0]="dir";
				format="folder";
			}

			
		/*	if(format.equals("")){
				format="0";
			}
			*/
			
			
db.execSQL(md.ft.insert, new Object[]{name,isFile,format,modified,rawModified,length,size,icon,cn_char,thumb,kind[0],canRead,path});

	}
		
	
		//raw data=db.rawQuery("select name,icon,modified,size,id,isFile from files order by isFile desc,"+ViewShow.getViewSortBy(), null);
		//ff.log(ssql.queryTable("files", db));		
		return true;
		}


}


class FileFilter_notShowHiddenFiles implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		return !pathname.isHidden();
	}

}
