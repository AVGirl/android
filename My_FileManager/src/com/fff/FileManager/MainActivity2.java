
package com.fff.FileManager;
/*package com.fff.FileManager;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.MySQLiteOpenHelper;
import com.fff.tools.ssql;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewSwitcher;

//import android.support.v7.app.ActionBarActivity;
public class MainActivity extends FragmentActivity {
public static  Context mContext;
public static  Activity mActivity;
private Looper Looper;
private MySQLiteOpenHelper mainDB;
private ArrayList<FileExplorer> arrFragmen;
private FileExplorer fileExplorer2;
public static  GridView gridview;
private MinView_adapter minView_adapter;
private FragmentManager fragmentManager;
private ViewPagerAdapter viewPagerAdapter;
public static ViewPager viewPager;
public static SQLiteDatabase db;


public static void uio(){
	ff.log("uio");
}
 

class homeiconOnclick implements android.widget.AdapterView.OnItemClickListener{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	  
		minView_adapter.data.moveToPosition(position);
		int id1=minView_adapter.data.getInt(minView_adapter.id_index);
		String type=ssql.tjcx("type", md.mt.name	, "id="+id1);
		String target=ssql.tjcx("target", md.mt.name	, "id="+id1);
		ff.log(target);
		
		if(type.equals(md.mt.type_file)){
		FileOperation.openFile(target,MainActivity.mActivity);
		}
		
		ff.log();
		if(type.equals(md.mt.type_dir)){
			openNewWindow(target);
		}
		
		
		
	}
	
}
  void openNewWindow(String dir){
	 arrFragmen.add(new FileExplorer( fragmentManager,dir));
	 viewPagerAdapter.notifyDataSetChanged();
	 
	 viewPager.setCurrentItem(arrFragmen.size());
	 gridview.setVisibility(8);
	 viewPager.setVisibility(0);
 }
 
void  inithomeicon(){
	minView_adapter=new MinView_adapter();
	gridview.setAdapter(minView_adapter);
	gridview.setOnItemClickListener(new homeiconOnclick());	
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridview=(GridView) this.findViewById(R.id.mainView_GridView);		
	
		cfg.init(this);	
		mContext=this;   
		mActivity=this;	   	
	
		this.deleteDatabase("MainDB");	
		mainDB=new MySQLiteOpenHelper(mContext,"main",md.mt.sql);
		db= mainDB.getWritableDatabase();
		ssql.setDB(db); 
	    
		inithomeicon();
		fragmentManager=getSupportFragmentManager();
		arrFragmen=new ArrayList<>();  
		
	String	t=cfg.getString("mainPath");
		if(t==null){t=ff.getsdpath();};	
		arrFragmen.add(new FileExplorer(getSupportFragmentManager(),t));
		//arrFragmen.add(fileExplorer2);
		t=cfg.getString("mainPath");
		if(t==null){t=ff.getsdpath();};		
		
		
		 viewPagerAdapter=new ViewPagerAdapter(this.getSupportFragmentManager(),arrFragmen);
		 viewPager=(ViewPager) this.findViewById(R.id.mainView_ViewPager);
		 viewPager.setAdapter(viewPagerAdapter);
		
		 	viewPager.setVisibility(0);
			gridview.setVisibility(View.GONE);



	
		}    
		     
	
	
	    
	
@Override  
public void onBackPressed() {
	ViewSwitcher.ViewFactory d=new ViewSwitcher.ViewFactory(){

		@Override
		public View makeView() {
			
			return null;
		}
		
	};
	int a=viewPager.getCurrentItem();
	arrFragmen.get(a).onBackPressed();

	
	//fileExplorer.onBackPressed();
}


  





}

package com.fff.FileManager;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.MySQLiteOpenHelper;
import com.fff.tools.ssql;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ViewSwitcher;

//import android.support.v7.app.ActionBarActivity;
public class MainActivity extends FragmentActivity {
public static  Context mContext;
public static  Activity mActivity;
private Looper Looper;
private MySQLiteOpenHelper mainDB;
private ArrayList<FileExplorer> arrFragmen;
private FileExplorer fileExplorer2;
public static  GridView gridview;
private MinView_adapter minView_adapter;
private FragmentManager fragmentManager;
private ViewPagerAdapter viewPagerAdapter;
public static ViewPager viewPager;
public static SQLiteDatabase db;


public static void uio(){
	ff.log("uio");
}
 

class homeiconOnclick implements android.widget.AdapterView.OnItemClickListener{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	  
		minView_adapter.data.moveToPosition(position);
		int id1=minView_adapter.data.getInt(minView_adapter.id_index);
		String type=ssql.tjcx("type", md.mt.name	, "id="+id1);
		String target=ssql.tjcx("target", md.mt.name	, "id="+id1);
		ff.log(target);
		
		if(type.equals(md.mt.type_file)){
		FileOperation.openFile(target,MainActivity.mActivity);
		}
		
		ff.log();
		if(type.equals(md.mt.type_dir)){
			openNewWindow(target);
		}
		
		
		
	}
	
}
  void openNewWindow(String dir){
	 arrFragmen.add(new FileExplorer( fragmentManager,dir));
	 viewPagerAdapter.notifyDataSetChanged();
	 
	 viewPager.setCurrentItem(arrFragmen.size());
	 gridview.setVisibility(8);
	 viewPager.setVisibility(0);
 }
 
void  inithomeicon(){
	minView_adapter=new MinView_adapter();
	gridview.setAdapter(minView_adapter);
	gridview.setOnItemClickListener(new homeiconOnclick());	
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridview=(GridView) this.findViewById(R.id.mainView_GridView);		
	
		cfg.init(this);	
		mContext=this;   
		mActivity=this;	   	
	
		this.deleteDatabase("MainDB");	
		mainDB=new MySQLiteOpenHelper(mContext,"main",md.mt.sql);
		db= mainDB.getWritableDatabase();
		ssql.setDB(db); 
	    
		inithomeicon();
		fragmentManager=getSupportFragmentManager();
		arrFragmen=new ArrayList<>();  
		
	String	t=cfg.getString("mainPath");
		if(t==null){t=ff.getsdpath();};	
		arrFragmen.add(new FileExplorer(getSupportFragmentManager(),t));
	
		
		
		 viewPagerAdapter=new ViewPagerAdapter(this.getSupportFragmentManager(),arrFragmen);
		 viewPager=(ViewPager) this.findViewById(R.id.mainView_ViewPager);
		 viewPager.setAdapter(viewPagerAdapter);
		
		 	viewPager.setVisibility(0);
			gridview.setVisibility(View.GONE);



	
		}    
		     
	
	
	    
	
@Override  
public void onBackPressed() {
	ViewSwitcher.ViewFactory d=new ViewSwitcher.ViewFactory(){

		@Override
		public View makeView() {
			
			return null;
		}
		
	};
	int a=viewPager.getCurrentItem();
	arrFragmen.get(a).onBackPressed();

	
	//fileExplorer.onBackPressed();
}


  





}
}*/