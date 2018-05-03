package com.fff.FileManager;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.regex.Pattern;

import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.Linux;
import com.fff.tools.Linux.UidGid;
import com.fff.tools.Linux.linuxChmod;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileBrowser extends android.support.v4.app.Fragment {
	public static final int msg_viewMode_change=1001;
	public static final int msg_onItemLongClick=1002;
	public static final int msg_onItemClick=1003;
	public static final int msg_enableSelect_false=1004;
	
	public static final int msg_onWindownChange=1005;
	
	

	
	public  boolean formSearchResult=false;

	
	  
	
	static AbsListView absl;
	public  ListView listView;
	public  GridView gridView;
	public  ViewAdapter viewAdapter;
	
	int offset;

	android.support.v4.app.FragmentManager spgl;
	public TextView TextViewTitle;

public	Cursor data;
	public Handler handler;
	
	int count=0;
	
	View mainView;
	//_________________________________________________  
	public  SQLiteDatabase db;
	public   String parentPath;
	public  int clickPositon;
	private android.support.v4.app.FragmentManager fragmentManager;
	private boolean isInitOk;
	private int allUidGid;
	//_________________________________________________________________________________________


	//搜索结果 
	FileBrowser(android.support.v4.app.FragmentManager fragmentManager,SQLiteDatabase Searchdb) {
		this.fragmentManager = fragmentManager;
		viewAdapter = new ViewAdapter(this);
		viewchanged();
		this.db = Searchdb;

		data=db.rawQuery("select name,icon,modified,size,id,isFile,thumb,kind,canRead,format,parent  from files order by isFile desc,cn_char,"+ViewShow.getViewSortBy()+"", null);
		FieldIndex.init(data);	
		formSearchResult=true;
		parentPath="搜索结果 ";
		//disabledMulti();
		//notifyDataSetChanged();
		//setTitle("搜索结果");
	}

	
	
	
	FileBrowser(android.support.v4.app.FragmentManager fragmentManager, String dir, SQLiteDatabase db) {


		this.fragmentManager = fragmentManager;

		viewAdapter = new ViewAdapter(this);
		viewchanged();

		String t = null;
		// _____________________________
		// filesDBHelper=new MySQLiteOpenHelper(mContext,"fileDatabase","");//
		// TODO

		this.db = db;
		// db=filesDBHelper.getWritableDatabase();

		loadDir(dir);
	}

	
	public void viewchanged(){		
		if(viewAdapter!=null){
			int ab[]=ViewShow.getViewMode();	
			int layout=ab[ViewShow.ViewMode.Mode_Layout];
			
			if(ab[ViewShow.ViewMode.Mode_info]==ViewShow.ViewMode.INFO_INTS){
				viewAdapter.layout_info_IN=0;
				viewAdapter.layout_info_INTS=layout;
			}else{
				viewAdapter.layout_info_IN=layout;
				viewAdapter.layout_info_INTS=0;
				
			}
				
		}
		
	}
	
	/*
	public static int layout_icon_name;
	public static int  layout_icon_name_time_size;*/
	
	

public  String dbGetString(int position,int column){
	data.moveToPosition(position);
	return data.getString(column);
}	
public  int dbGetInt(int position,int column){
	data.moveToPosition(position);
	return data.getInt(column);
}	


         
//______________________________________________FieldIndex_____________________________________________


/*class chmodRWX extends  java.util.TimerTask{
	String path,rwx;
	     
	chmodRWX(String path,String rwx){
		this.path=path;this.rwx=rwx;
	};
	@Override
	public void run() {
		ff.log("恢复权限：chmod "+rwx +" path  "+path);
		su.exec("chmod "+rwx +" \""+ path+"\"");
	}
	
}*/
  
//________________________________________
	public void loadDir(String path) {
		if(path==null){
			return;
		}
		
		db.beginTransaction();
		db.execSQL("delete from files");	
		
		if(cfg.getBoolean("AppSettings-showHiddenFile")){		 
			ReadDir.readDir(path, db,true,null);
		}else{
			ReadDir.readDir(path, db,true,ReadDir.fileFilter_notShowHiddenFiles);
		}
		
	db.setTransactionSuccessful();		
	db.endTransaction();
	

	
	
	if(path.endsWith("/")==false){parentPath=path+"/";}else{parentPath=path;}
	data=db.rawQuery("select name,icon,modified,size,id,isFile,thumb,kind,canRead,format,parent  from files order by isFile desc,cn_char,"+ViewShow.getViewSortBy()+"", null);
	FieldIndex.init(data);
	
	
		if (isInitOk == true) {
			disabledMulti();
			notifyDataSetChanged();
			setTitle(path);
		}

	}

//______________________________________________________________________
	
void location() { 
/*	gridView.setSelection(clickPositon);
	gridView.smoothScrollBy(-offset, 0);
	*/
	//ff.toast(gridView.getChildCount());
	
	absl.postDelayed(new Runnable() {		
		@Override
		public void run() {  
			if(absl instanceof GridView){
				gridView.setSelection(clickPositon);
				
				//gridView.setItemChecked(clickPositon, true);
				//viewAdapter.notifyDataSetChanged();
			}else{
				listView.setSelection(clickPositon);
			}
		
		
		}
	}, 1);

	
/*	absl.postDelayed(new Runnable() {		
		@Override
		public void run() {  
			if(absl instanceof GridView){
		
				gridView.setItemChecked(clickPositon, true);
				viewAdapter.notifyDataSetChanged();
			}else{
				listView.setSelection(clickPositon);
			}
		
		
		}
	}, 100);
*/
	
	
	
}
// __________________________________
	void updateAbsListView() {
		if (ViewShow.getViewMode()[0] < 29) {
			if (listView != null) {
				absl = listView;
			}
		} else {
			if (gridView != null) {
				absl = gridView;
			}
		}
	}

	
	
	
	

public	int[] getCheckedItemDatas() {
	
		int j = absl.getCheckedItemCount();

		int[] count1 = new int[j];
		int n = 0;
		for (int i = 0; i < data.getCount(); i++) {
			if (absl.isItemChecked(i)) {
				count1[n] = i;
				n++;
			}
			;
		}

		return count1;
	}
	


/*	@Override
public void onResume() {
	super.onResume();
	disabledMulti();
	ff.sc("*--*-*--*-*-*-onResume");
}
*/






	
	
	
	// __________________________________________________________________________________
	public void setMultiplSelect() {
		absl.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		viewAdapter.enableSelect=true;
	}

	public void disabledMulti() {
	//	ff.sc("************	public void disabledMulti() {");
		absl.setChoiceMode(0);
		absl.clearChoices();
		viewAdapter.notifyDataSetInvalidated();
		handler.sendEmptyMessageDelayed(msg_enableSelect_false, 500);
	
	}

public boolean isEnabledMulti() {
	
	
		if (absl.getChoiceMode() == absl.CHOICE_MODE_MULTIPLE) {
			return true;
		} else {
				return false;
		}
}

	// __________________________________________________________________________________
void showMultiSelectToolbar(Boolean b) {
/*		View v = mainView.findViewById(R.id.files_explorer_layout_MultiSelectOperateToolbar);
		int kk = v.getVisibility();
		if (b == null) {
			if (kk == View.GONE) {
				v.setVisibility(View.VISIBLE);

			} else {
				v.setVisibility(View.GONE);
			}
			return;
		}
		
		if (b == true) {
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}
*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		mainView = inflater.inflate(R.layout.files_explorer_layout, null);
		listView=(ListView) mainView.findViewById(R.id.files_explorer_ListView);
		gridView=(GridView) mainView.findViewById(R.id.files_explorer_GridView);
		gridView.setOnItemClickListener(jj.impl_FileBrowser);
		listView.setOnItemClickListener(jj.impl_FileBrowser);
		listView.setOnItemLongClickListener(jj.impl_FileBrowser);
		gridView.setOnItemLongClickListener(jj.impl_FileBrowser);
	//	listView.setOnItemSelectedListener(jj.impl_FileBrowser);
		//gridView.setOnItemSelectedListener(jj.impl_FileBrowser);
		 
		handler=new MyHandler();
	//	gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);

		
		TextViewTitle = (TextView) mainView.findViewById(R.id.files_explorer_layout_dispPath);
		this.setTitle(parentPath);

		qieHuanView();
	

		notifyDataSetChanged();
		isInitOk=true;
		
		
		return mainView;
	}
	
	
	
/*@Override
	public void onResume() {
		super.onResume();
		ff.sc("onResume---",parentPath);
		handler=new MyHandler();
	}



@Override
	public void onPause() {
		super.onPause();
		ff.sc("onPause---",parentPath);
		handler=null;
	}

*/

	
	

	

	
	
	//_________________________________________onBackPressed

	
	void onBackPressed(){
	
		jj.handler.sendEmptyMessage(md.mainUImsg.browserOnBackPressed);
		
		
		if(isInitOk==false){
			return ;
		}
		
		
		if(isEnabledMulti()){
			absl.clearChoices();
			disabledMulti(); 
			notifyDataSetChanged();
			showMultiSelectToolbar(false);
			return;
		}

		
		//Toast.makeText(jj.mContext, md.sdcard+"\n"+parentPath, Toast.LENGTH_SHORT).show();
		if(parentPath.equals(md.sdcard+"/")){
		//	Toast.makeText(jj.mContext, "if(ttt.equals(ttt)){"+parentPath, Toast.LENGTH_SHORT).show();
			jj.showHomePage(0);
			//jj.backAgain();
			return;
		}
		
		
		
	String ttt=null;
	File f=new File(parentPath);
	if(parentPath.equals("/")){
		 ttt="/";
	}else{
		
		if(!md.isnull(f.getParent())){
			ttt=f.getParent().toString();
		}
		
	
	}
//Toast.makeText(jj.mContext, ttt, Toast.LENGTH_SHORT).show();

		loadDir(ttt);
		location();
	}

	/**
	 *
	 */
	void qieHuanView() {  
		
		if (ViewShow.getViewMode()[ViewShow.ViewMode.Mode_view] == ViewShow.ViewMode.VIEW_LIST) {
			absl=listView;
			gridView.setAdapter(null);
			gridView.setVisibility(View.GONE);
			
			if(listView.getAdapter()==null){
			listView.setAdapter(viewAdapter);  
			}	
			listView.setVisibility(View.VISIBLE);			
		return;	
		}	
		
		
		absl=gridView;
		listView.setVisibility(View.GONE);
		listView.setAdapter(null); 	
		
	
		int w=ViewShow.ViewMode.getGirdNumColunms();
		gridView.setNumColumns(w);
		gridView.setVisibility(View.VISIBLE);
		if(gridView.getAdapter()==null){
			gridView.setAdapter(viewAdapter);  
			}	
		

	}

	void setTitle(String s) {
		//s=s.replace(md.sdcard, "");
		TextViewTitle.setText(s);
	}

	void notifyDataSetChanged(boolean...reset) {		
		viewAdapter.cursor=data;
		viewAdapter.parentPath=this.parentPath;
		
		
		if(reset.length!=0){				
			absl.setAdapter(null);	
			absl.setAdapter(viewAdapter);		
			if(1>0){return;};
		}		
	
		viewAdapter.notifyDataSetInvalidated();
		viewAdapter.notifyDataSetChanged();	
	}

    
	public void Refresh() {
		//ff.log("Refresh  public void Refresh() {");
		loadDir(parentPath);
		notifyDataSetChanged();
	}
	
	
	
	


		// __________________________________________________

	// ____________________________________________________end class_____________________________________
   
	 
	
	
	

public class MyHandler extends Handler{
	@Override
	public void handleMessage(Message msg) {
	//	ff.sc(parentPath,"hashCode-"+handler.hashCode());
		switch(msg.what){
		
		case msg_viewMode_change:
		//	ff.sc("msg_viewMode_change,"+parentPath);
			viewMode_change();
			break;
		case msg_onItemLongClick:
		//	ff.sc("msg_onItemLongClick",parentPath);
			break;
			
		case msg_onItemClick:
		//	ff.sc("msg_onItemClick",parentPath);
			
			break;
		case msg_enableSelect_false:
			viewAdapter.enableSelect=false;
				break;
					
		case msg_onWindownChange:
			//ff.sc("******************		case msg_onWindownChange:");
			msg_onWindownChange();
				break;
			
				
			
		}
		
		
	}

	
	
	
	private void msg_onWindownChange() {
		disabledMulti();
	}




	private void viewMode_change() {
		viewchanged(); 					
		qieHuanView();				
		notifyDataSetChanged(true);
	}
	
	
		
	
}	
	
	//_______________________________________________________end class
}
/* 

   class FieldIndex {
	public static int id;
	public static int icon;
	public static int modified;
	public static int size;
	public static int name;
	public static int isFile;
	public static int thumb;
	public static int kind;
	public static int canRead;
	
							
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
		
		initialized=true;
	}*/












/*
 * tyAdapter=new TYAdapter(5,4){
 * 
 * @Override void setChildData(SavedView s, int position) {
 * s.setText(R.id.view_list_adapter_name, c); }};
 */