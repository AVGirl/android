package com.fff.FileManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedList;

import com.fff.misc.cfg;
import com.fff.misc.ff;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SearchFile {
	
private static CheckBox checkBoxfile;
private static CheckBox checkBoxFolder;
private static View ui ;
private static String parentPath;

 
static boolean search_folder;


static FileFilter_notShowFile justShowDir=new FileFilter_notShowFile();
private static String what;
private static EditText ev;
private static Handler handler;
private static ProgressDialog prog;

final static int msg_startSearch=1;
final static int msg_endSearch=2;
final static int msg_DelayedSearch=3;



//ProgressDialog
public static void init(){
	
	handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			
			case msg_startSearch:
				prog.show();
				break;
		case msg_endSearch:
			prog.dismiss();;
			break;
		case msg_DelayedSearch:
		//new Thread(new startSearchThread()).start();
		break;
		}
			
			
		
			
			
		}
		
	};
	
	 prog=new ProgressDialog(md.context,ProgressDialog.THEME_HOLO_DARK);
	 prog.setIndeterminate(true);
	 prog.setMessage("搜索中...");
	 prog.setCancelable(false);
	 
	 
	ui = md.activity.getLayoutInflater().inflate(R.layout.search_file, null);
	ev = (EditText) ui.findViewById(R.id.editText);
	ev.setText("mp3");

	checkBoxFolder = (CheckBox) ui.findViewById(R.id.search_file_start_checkBox_folder);
	ui.findViewById(R.id.search_file_start_search).setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			handler.sendEmptyMessage(msg_startSearch);
			what = ev.getText().toString();
			search_folder = checkBoxFolder.isChecked();
			dhk.alertDialog.dismiss();
			
			new Thread(new startSearchThread()).start();
			//handler.sendEmptyMessageDelayed(500, msg_DelayedSearch);
	
		}
	});

}

public static void showSearchFileDialog(String path ){
		if (ui == null) {
			init();		
		}
		
		ev.setText(cfg.read.getString(md.cfgName.search_file_contet, ""));
	ev.setSelection(0, ev.getText().length());	
	

	if(path!=null && path.equals("")){
		parentPath=md.sdcard;
	}else{
		parentPath=path;	
	}
	

	
if((ViewGroup)ui.getParent()!=null){
	((ViewGroup)ui.getParent()).removeView(ui);
}



dhk.setUserBuilder(true);
dhk.alertDialog=null;
dhk.builder.setView(ui);
dhk.builderSettings.openInputMethod=true;
dhk.showAlertDialog();

 
	
}

public static void end_search(){
	handler.sendEmptyMessage(msg_endSearch);
	ff.sc("sendsearch","search_folder:"+search_folder);
jj.handler.sendEmptyMessageDelayed(md.mainUImsg.end_search, 200);
}

static class startSearchThread implements Runnable {
	@Override
	public void run() {
		startSearch();   
	}
	
	

public static void startSearch(){
	if(!new File(parentPath).exists()){
		//handler.sendEmptyMessage(msg_endSearch);
		//return;
		parentPath=md.sdcard;
	}
	
	LinkedList<String> arr=new LinkedList<>();
	SQLiteDatabase db=jj.filesDBHelper.getWritableDatabase();
	
	
	cfg.putString(md.cfgName.search_file_contet, what);
	
	
	db.beginTransaction();
	db.execSQL("delete from files");
	
	

	//search_folder=false;
	MyFileFilter myFileFilter =new MyFileFilter(search_folder,what);
	 

	
	ReadDir.readDir(parentPath, db, true, myFileFilter);
	
	File folders[]=new File(parentPath).listFiles(justShowDir);
	 
	if(folders==null)return;
	
	for (int i = 0; i < folders.length; i++) {
			arr.add(folders[i].getPath());
	}
	
	String folder=null;
	while(!arr.isEmpty()){
 
		folder=arr.removeFirst();
		
		ReadDir.readDir(folder, db, true, myFileFilter);
		
		 folders=new File(folder).listFiles(justShowDir);
		 if(!md.isnull(folders)){
			 for (int i = 0; i < folders.length; i++) {
				arr.add(folders[i].getPath());
			}
		 }
		 
		
	}
	
	 db.setTransactionSuccessful();
	 db.endTransaction();
	 end_search();
	
}



}



	
}












class MyFileFilter implements FileFilter{
 boolean search_folder = false;
private String something;
MyFileFilter(boolean searchFolder,String Whattt){
	 search_folder=searchFolder;
	this. something=Whattt.toLowerCase();
 }
	
		@Override
		public boolean accept(File a) {
			if(search_folder==false){
				if(a.isDirectory()==true){
					return false;
				}
				
			}
			

			if(a.getName().toLowerCase().contains(something)){				
				return true;
			}else{
				
				return false;
			}
			
			
			
			
			
		
		};
	
}








class FileFilter_notShowDir implements FileFilter{
	@Override
	public boolean accept(File pathname) {
		return !pathname.isDirectory();
	}}

class FileFilter_notShowFile implements FileFilter{
	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory();
	}}




















/*
@Override
public boolean accept(File a) {
	if(search_folder==false){
		if(a.isDirectory()==true){
			return false;
		}else{
			//ff.sc("  something,,"+this.something);
			if(a.getName().contains(this.something)){
			
				return true;
			}else{
				return false;
			}	
			
		}
		
		
	}else{
		
		if(a.getName().contains(this.something)){				
			return true;
		}else{
			
			return false;
		}
		
		
	}
	
	
*/
	
