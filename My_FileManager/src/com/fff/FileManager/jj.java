package com.fff.FileManager;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.xmlpull.v1.XmlPullParser;

import com.fff.adapter.Adapter_DialogHomeChooseIcon;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.Linux.linuxChmod;
import com.fff.tools.MySQLiteOpenHelper;
import com.fff.tools.api;
import com.fff.tools.ssql;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.print.PrintAttributes.Margins;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

//import android.support.v7.app.ActionBarActivity;
public class jj extends FragmentActivity {
public static  Context mContext;
public static  Activity mActivity;
private MySQLiteOpenHelper mainDB;
public static View mainSection;
public static  MyHandler handler;
public static AsyncLoad asyncLoad;
public static  LayoutInflater inflate;
public static ArrayList<FileBrowser> arrFE=new ArrayList<>();
public static  GridView gridview;
public static  MinView_adapter gridViewAdapter;
public static FragmentManager fragmentManager;
private static ViewPagerAdapter viewPagerAdapter;
public static MySQLiteOpenHelper filesDBHelper;
public static View dxgjl;
public static  Impl_Toolbar ToolbarListener;
public static ViewSwitcher vs;
public static View toolbar_multiple;
public static View toolbar;
public static  Impl_FileBrowser impl_FileBrowser= new Impl_FileBrowser();
public static ViewPager viewPager;
public static SQLiteDatabase db;





@Override
protected void onCreate(Bundle savedInstanceState)   {  
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	mContext=this;   
	mActivity=this;	 
	md.init(this, this);
	cfg.init(this);	
	//su.init();
	//su2.init();
	inflate= getLayoutInflater();
	
	
	//this.deleteDatabase(md.ft.wjm);
	handler =new MyHandler();
	filesDBHelper=new MySQLiteOpenHelper(jj.mContext,md.ft.wjm,md.ft.sql);	
	//mainDB=new MySQLiteOpenHelper(mContext,"main",md.mt.sql);
	ToolbarListener = new Impl_Toolbar();
	fragmentManager=getSupportFragmentManager();
	viewPagerAdapter=new ViewPagerAdapter(this.getSupportFragmentManager());
	//db= mainDB.getWritableDatabase();
	db=SQLiteDatabase.openOrCreateDatabase(md.sdcard+"/file.db", null);
	try {db.execSQL(md.mt.sql);} catch (Exception e) {}
	// 
	ssql.setDB(db);
 
	bindView();
	inithomeicon();
	handler.sendEmptyMessageDelayed(md.mainUImsg.startActivity, 100);
	//_________________________________________________
	//arrFE.add(new FileBrowser(getSupportFragmentManager(),md.getLoadDir(),filesDBHelper.getWritableDatabase()));
	viewPager.setAdapter(viewPagerAdapter); 
	File file=new File(md.pic_cacheDir+"/");
	if(!file.exists()){file.mkdirs();ff.sc("!file.exists()");}  

	String a=cfg.read.getString(md.cfgName.mainUI_background,"");
	if(!a.equals("")){
		findViewById(R.id.mainUi).setBackground(Drawable.createFromPath(md.backgroupDir+"/"+ a));
	}
			
      
       
	//SQLiteDatabase ee =SQLiteDatabase.openOrCreateDatabase(md.sdcard+"/file.db", null);
	
	
	//、、SearchFile.startSearch("mp3");
	
	
	//____________________________________________________________________________________

//________________________________________
	
	}    

	void bindView() {		
		vs = (ViewSwitcher) findViewById(R.id.ViewSwitcher);
		
		
		
		toolbar_multiple = vs.findViewById(R.id.vs_toolbar_multiple);
		toolbar = vs.findViewById(R.id.vs_toolbar);
		
		((RadioGroup) toolbar.findViewById(R.id.main_toolbar_RadioGroup)).setOnCheckedChangeListener(ToolbarListener);
		;
		((RadioGroup) toolbar_multiple.findViewById(R.id.main_toolbar_multiple_RadioGroup))
				.setOnCheckedChangeListener(ToolbarListener);
		;
		
		mainSection=this.findViewById(R.id.activity_main_mainSection);
		gridview = (GridView) this.findViewById(R.id.mainView_GridView);
		viewPager = (ViewPager) this.findViewById(R.id.mainView_ViewPager);
		dxgjl = findViewById(R.id.dxgjl_view);
		((RadioGroup) dxgjl.findViewById(R.id.dxgjl_RadioGroup)).setOnCheckedChangeListener(ToolbarListener);
		
		
		
		SDuseState();
		
	}
	void SDuseState(){

		File t5 = new File(md.sdcard);
		long total = t5.getTotalSpace();
		long usable = t5.getUsableSpace();

		DecimalFormat e1 = new DecimalFormat("#.00");
		int r4 = 1073741824;

		String tt = e1.format((double) (total - usable) / r4) + "G/" + e1.format((double) total / r4) + "G";
		;
		double dd = (double) (total - usable) / total * 100;
		String jg= tt + "  " + Math.round(dd) + "%";
		View v=this.findViewById(R.id.activity_main_spaceLable_bg);
		
		
		int width=this.getWindowManager().getDefaultDisplay().getWidth();
		int w=(int) Math.round(dd);
		int u=width/100;
		v.getLayoutParams().width=w*u;
		
		
		
	
((TextView) findViewById(R.id.activity_main_spaceLable)).setText(api.getSDuseState());;	
		
	}
	
	

public static void removeFileExplorer(int w){
	 fragmentManager.beginTransaction().remove(arrFE.get(w)).commit();
	 arrFE.remove(w);
	 viewPagerAdapter.notifyDataSetChanged();
	 if(w-1>=0){
		 viewPager.setCurrentItem(w-1);

	 }
}




public static FileBrowser getCurrFileExplorer(){
	if(arrFE.isEmpty()){
		return null;
	}
	
	return arrFE.get(viewPager.getCurrentItem());
}

 


public static void  quanHuanWin(int w){
	 showHomePage(8);
	 viewPager.setCurrentItem(w);
	 
	 for (int i = 0; i < arrFE.size(); i++) {
		 arrFE.get(i).handler.sendEmptyMessage(FileBrowser.msg_onWindownChange);
		
	}
}


public static void openNewSearchWindow(){
	 arrFE.add(new FileBrowser( fragmentManager,filesDBHelper.getWritableDatabase()));
	 viewPagerAdapter.notifyDataSetChanged();
	 viewPager.setCurrentItem(arrFE.size());
	 showHomePage(8);
	 
	 
	
}



  public static void openNewWindow(String dir){
	 arrFE.add(new FileBrowser( fragmentManager,dir,filesDBHelper.getWritableDatabase()));
	 viewPagerAdapter.notifyDataSetChanged();
	 viewPager.setCurrentItem(arrFE.size());
	showHomePage(8);
	
 }
  
  
	public void onClick(View v) {
		
		ff.sc(77777);
ff.sc(v.getId(),v.getTag());
		
	}
	
void  inithomeicon(){
	
	String q1= ssql.queryString("select "+md.mt.Field.target + " from "+md.mt.tableName +" where target =\""+md.mt.type_dir+"\"");
	if(q1==null){
		FileMenuOnclick.addtoHomepage2("SD",md.mt.type_dir,md.sdcard,R.drawable.sdcard_large);
	}
	gridViewAdapter=new MinView_adapter();	
	gridview.setAdapter(gridViewAdapter);
	Impl_HomePage gg = new Impl_HomePage();
	gridview.setOnItemClickListener(gg);
	gridview.setOnItemLongClickListener(gg);
	
}
		      
	
	    
	
@Override  
public void onBackPressed() {onBackPressed2();}
static void onBackPressed2(){
  
	int a=viewPager.getCurrentItem();
	
	
	//Toast.makeText(mContext, "VISIBLE",Toast.LENGTH_SHORT).show();
//	if( viewPager.getVisibility()==View.VISIBLE){
//		Toast.makeText(mContext, "VISIBLE",Toast.LENGTH_SHORT).show();
//	}
	  
		if (arrFE.size() == 0 || mainSection.getVisibility()==View.VISIBLE) {
			backAgain();
			return;
		}
		
	//viewPager.get
	//ff.log(a,"Pager.getCurrentItem();");
	arrFE.get(a).onBackPressed();
	//fileExplorer.onBackPressed();
}

static void backAgain(){
	//int a=23;

	long dqsj=System.currentTimeMillis();
	long saveTime=cfg.read.getLong("backAgain",0);
	if((dqsj-saveTime)<2000){
		mActivity.finish();
	}else{
		cfg.putLong("backAgain",dqsj);
		Toast.makeText(mContext, "再按一次退出",Toast.LENGTH_SHORT).show();
	}
	
	
}
 
	public static void showHomePage(int i) {
		viewPager.setVisibility(i==0?8:0);
		mainSection.setVisibility(i);

	}
	@Override
	protected void onResume() {
		super.onResume();
		
		if(this.getBaseContext(     )!=null){
			dhk.setContext(this);
		}

	}
	
	
	
	public static void clearCache(){
		File f[]=new File(md.pic_cacheDir).listFiles();
		if(f!=null){
			for (int r = 0; r < f.length; r++) {
				f[r].delete();				
			}
		}
		
		
		String aa="/sdcard/android/data/"+md.context.getPackageName()+"/cache/pic_cache";
		//new File(aa).delete();
		//new File(md.pic_cacheDir).delete();
		new File(aa).mkdirs();
		new File(md.pic_cacheDir).mkdirs();
		
		
		
		if (1 > 0) {
			return;
		}
		;
		/*try {
			//ff.log("rm -r \""+md.pic_cacheDir+"\"");
			ff.log(md.context.getPackageName());
	
		ff.log(aa);
			Runtime.getRuntime().exec("rm -r \""+aa+"\"");
			Runtime.getRuntime().exec("mkdir -p \""+aa+"\"");
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  
		}
		  
		
		ff.toast(md.context,"clearCache ok");
		if (1 > 0) {
			return;
		}
		;
		
	gridview.postDelayed(new Runnable() {

		
		@Override
		public void run() {
			try {
				Runtime.getRuntime().exec("rm -r \""+md.pic_cacheDir+"\"");
				Runtime.getRuntime().exec("mkdir -p \""+md.pic_cacheDir+"\"");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ff.toast(md.context,"clearCache ok");		
			if (1 > 0) {
				return;
			}
			;
			for (int i = 0; i < 1; i++) {
				//new File(md.pic_cacheDir).
				File f[]=new File(md.pic_cacheDir).listFiles();
				//ff.log(f);
				if(f!=null){
					for (int r = 0; r < f.length; r++) {
						f[r].delete();				
					}
				}
				
			}
		//	md.context.getCacheDir();
			//ff.log(md.pic_cacheDir+"/");
			ff.log(new File(md.pic_cacheDir).delete());
		ff.log(new File(md.pic_cacheDir+"/").delete());
			ff.log(new File(md.pic_cacheDir+"/").mkdirs());
			ff.toast(md.context,"clearCache ok");			
		}
		
	}, 200);  
*/
	}
	
	
	
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case md.mainUImsg.browserOnItemLongClick:
				browserOnItemLongClick();
				break;
			case md.mainUImsg.browserOnItemClick:
				browserOnItemClick();
			break;
			case md.mainUImsg.openNewWindow:
				browserOnBackPressed();
				openNewWindow(String.valueOf(msg.obj));
			break;
				
				
			case md.mainUImsg.browserOnBackPressed:
				browserOnBackPressed();
			break;
			
			case md.mainUImsg.end_search:
			
				openNewSearchWindow();
			break;
			
			case md.mainUImsg.startActivity:
				startActivity_1();
			break;
		}
		
			
		}

		
		
		
		
		private void browserOnItemClick() {
			
		}





		private void startActivity_1() {
		ViewAdapter.show_thumbnail=	cfg.read.getBoolean("AppSettings-show_thumbnail",	 true);
			
				if(!new File(md.file_format_dir+"/mp3").exists()){
					try {
						api.unZip(getAssets().open("file_format.zip"), md.file_format_dir);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
				if(!new File(md.backgroupDir+"/bj.jpg").exists()){
					try {
						api.unZip(getAssets().open("bj.zip"), md.backgroupDir);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
				
			
		}





		private void browserOnBackPressed() {
			if(dxgjl.getVisibility()==View.VISIBLE){
				dxgjl.setVisibility(View.GONE);
			}
			
			if(toolbar_multiple.getVisibility()==View.VISIBLE){
				toolbar_multiple.setVisibility(View.GONE);
				toolbar.setVisibility(View.VISIBLE);
			}
			
		}




		private void browserOnItemLongClick() {
			dxgjl.setVisibility(0);
			ToolbarListener.refreshToolBarTitle();
			if (vs.getCurrentView() != toolbar_multiple) {
				vs.showNext();
			}

		}

		
		
		
	}
	
	
	
}
//grid dialg  search

/*
android.os.Process.killProcess(android.os.Process.myPid());
System.exit(0);

ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
manager.killBackgroundProcesses(getPackageName())

*/

