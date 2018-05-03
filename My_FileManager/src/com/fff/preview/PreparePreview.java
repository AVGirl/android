package com.fff.preview;

import com.fff.misc.ff;
import com.fff.tools.FileOperation;

import android.database.Cursor;
  
public class PreparePreview {
	 
	 
public static boolean preparePreview(String f,Cursor cursor, int position){
	
	//ff.log("preparePreview(String f){",f);
  String f1 = "-"+FileOperation.getFileType(f).toLowerCase()+"-";
  
  if(FileOperation.FileType.image.indexOf(f1)!=-1){
	 PicturePreview.show(f,cursor,position);                                  	  
	  return true;
  }
  return false;
}

}
