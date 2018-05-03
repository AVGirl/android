package com.fff.tools;

import java.io.File;
import java.util.ArrayList;

import com.fff.FileManager.jj;
import com.fff.FileManager.md;
import com.fff.FileManager.R;
import com.fff.FileManager.R.drawable;
import com.fff.FileManager.ViewAdapter;
import com.fff.FileManager.dhk;
import com.fff.misc.ff;
import com.fff.misc.ViewShow;
import com.fff.misc.cfg;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
         
public class AppSettings extends Activity implements OnClickListener{
ArrayList<View> arr=new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.settings_layout);
		dhk.setContext(this);
/*		TextView tv=(TextView) findViewById(R.id.ttrr);
tv.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
	ff.log("fdfdsfsdfs");
		
	}
});		*/

		View v;
		arr.add(findViewById(R.id.AppSettings_homeDir)) ;
		arr.add(findViewById(R.id.AppSettings_showHiddenFile)) ;
		arr.add(findViewById(R.id.AppSettings_thumbnail)) ;
		arr.add(findViewById(R.id.AppSettings_show_thumbnail)) ;
		arr.add(findViewById(R.id.AppSettings_setbackgroup)) ;
		for(int i=0; i< arr.size() ;i++){
			arr.get(i).setOnClickListener(this);
		}
		    
	
		setCheckState(R.id.AppSettings_showHiddenFile, cfg.getBoolean("AppSettings-showHiddenFile"));
		//setCheckState(R.id.AppSettings_homeDir, cfg.getBoolean("AppSettings-homeDir"));
		
		
	
		  
	}  

void setCheckState(int id,boolean b){
		for(int i=0; i< arr.size() ;i++){
			if(arr.get(i).getId()==id){
				 RadioButton r=(RadioButton) arr.get(i);
				 int res;
				 if(b){
					 res=R.drawable.settings_check_ok;
				 }else{
					 res=R.drawable.settings_check_no;
				 }		 
				 Drawable no=this.getResources().getDrawable(res);
				 no.setBounds(0, 0, no.getIntrinsicWidth(), no.getIntrinsicHeight());
				 r.setCompoundDrawables(null, null, no, null);
			}
		}          
		      
	}        
	 
	 void AppSettings_thumbnail(){
		 dhk.setContext(this);
		 
		 int w[]=dhk.listDialog(new String[]{"250x250","100x100","50x50"}, String.valueOf(cfg.getInt("ThumbnailMode-picture")), "");
		   
		 if( w[0]==-1){
			 return;
		 }
		 
		 if (w[0] == 0 ) {
cfg.putInt("ThumbnailMode-picture", ViewShow.ThumbnailMode.Picture.high);
		}
		if (w[0] == 1) {
			cfg.putInt("ThumbnailMode-picture", ViewShow.ThumbnailMode.Picture.middle);
		}
		if (w[0] == 2) {
			cfg.putInt("ThumbnailMode-picture", ViewShow.ThumbnailMode.Picture.low);
		}
		
		ViewShow.ThumbnailMode.picture=cfg.getInt("ThumbnailMode-picture");
		ff.toast("一切都设置好了 -> "+cfg.getInt("ThumbnailMode-picture")); 
		jj.clearCache();
		
		 // 
		 
		 
	  }

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.AppSettings_homeDir:
			
			break;
		case R.id.AppSettings_thumbnail:
			AppSettings_thumbnail();
			break;
			
		case R.id.AppSettings_show_thumbnail:
			AppSettings_show_thumbnail();
			break;
		case R.id.AppSettings_setbackgroup:
			AppSettings_setbackgroup();
			break;
			
			
		case R.id.AppSettings_showHiddenFile:
			AppSettings_showHiddenFile();
			break;
				
		}
	}

	private void AppSettings_showHiddenFile() {
		boolean b=cfg.getBoolean("AppSettings-showHiddenFile");
		String aa[]=new String[]{"是","否"};
		int i=0;
		if(!b){
			i=1;
		}
		
		int w=dhk.singleChoiceDialog(aa, i, null, null);
		if(w==-1){
			return;
		}
		b=true;
		if(w==1){
			b=false;
		}
		ff.toast(this, "请手动刷新");
				cfg.putBoolean("AppSettings-showHiddenFile", b);
		
	}

	private void AppSettings_setbackgroup() {
		File[] f =new File(md.backgroupDir).listFiles();
		
		String[] a=new String[f.length+1];
		for (int i = 0; i < f.length; i++) {
			a[i]=f[i].getName();
		}
		a[a.length-1]="无";

	int w=	dhk.singleChoiceDialog(a, 0, null, null);
	if(w==-1){
		return;
	}
	
	a[a.length-1]="";
	
	Drawable dr=null;
	if(w==a.length-1){
		
	}else{
		dr=Drawable.createFromPath(md.backgroupDir+"/"+a[w]);
	}
	
	
	cfg.putString("mainUI-background", a[w]);	
	md.activity.findViewById(R.id.mainUi).setBackground(dr);
	 
		
	}

	private void AppSettings_show_thumbnail() {
		boolean b=cfg.read.getBoolean("AppSettings-show_thumbnail",	 true);
		int j=1;
		if(b){
			j=0;
		}
		//ff.sc(b);
		
		int w=dhk.singleChoiceDialog(new String[]{"是","否"}, j, null, null);
		if(w==-1){
			return;
		}
		
	//	ff.sc(w,"appsettings98888");
		if(w==0){
			b=true;
		}else{
			b=false;
		}
		
	//	ff.sc(b);
		
		cfg.putBoolean("AppSettings-show_thumbnail", b);		
		
		ViewAdapter.show_thumbnail=b;
	}

	
	
}
