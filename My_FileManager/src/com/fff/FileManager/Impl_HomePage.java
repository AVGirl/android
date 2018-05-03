package com.fff.FileManager;

import org.xmlpull.v1.XmlPullParser;

import com.fff.adapter.Adapter_DialogHomeChooseIcon;
import com.fff.entity.ClassIntFiled;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.api;
import com.fff.tools.ssql;

import android.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Impl_HomePage implements android.widget.AdapterView.OnItemClickListener, OnLongClickListener, OnItemLongClickListener{
	class ItemInfo {
		int id;
		String type,name,target;
		
	}

	ItemInfo getItemInfo(int position) {
		jj.gridViewAdapter.data.moveToPosition(position);
		int id = jj.gridViewAdapter.data.getInt(jj.gridViewAdapter.id_index);
		ItemInfo d = new ItemInfo();
		d.id = id;
		d.name = ssql.tjcx("name", md.mt.tableName, "id=" + id);
		d.id = id;
		d.type = ssql.tjcx("type", md.mt.tableName, "id=" + id);
		d.id = id;
		d.target = ssql.tjcx("target", md.mt.tableName, "id=" + id);
		return d;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	  
		ItemInfo a=getItemInfo(position);   
		
		if(a.type.equals(md.mt.type_file)){
		FileOperation.openFile(a.target,jj.mActivity);
		}	
		//ff.log("a.type.e",a.type);
		if(a.type.equals(md.mt.type_dir)){
		jj.openNewWindow(a.target);
		}
	
	
}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
	
		ItemInfo a=getItemInfo(position);  

		    int w[]=dhk.listDialog(md.home.getmenu(), null, null);
		
		    if(w[0]==-1){return true;};
		    String c=md.home.getmenu()[w[0]];
		
		    if(c.equals(md.home.menu_del)){
		    	 int q=dhk.dhk("点击确认删除", null, null, null);
		    	 if(q!=dhk.Positive_BTN){
		    		 return true;
		    	 }
		    	ssql.db.execSQL("delete from "+md.mt.tableName+" where id="+a.id);
		    	jj.gridViewAdapter.refresh();
		    }
	
		    if(c.equals(md.home.menu_chooseIcon)){
		    	menu_chooseIcon(a);
		    }
		    
		    if(c.equals(md.home.menu_rename)){
		    	menu_rename(a);
		    }
		    
		    
		
		return true;
	}
	
	
	
	private void menu_rename(ItemInfo a) {
	 
		String jj1= dhk.inputDialog(a.name, -1, "rename", null, null);
	 if(jj1==null){return;}
		jj.db.execSQL("update "+md.mt.tableName+" set "+md.mt.Field.name+" = \""+jj1+"\" where id="+a.id);  			
		//这里要用post 要在dhk 完全关闭后 才能更新ui       
		jj.gridview.postDelayed(new Runnable() {
			@Override
			public void run() {
				jj.gridViewAdapter.refresh();
				
			}
		}, 100);
		
	}

	void menu_chooseIcon(final ItemInfo itemInfo) {

		ViewGroup vg = (ViewGroup) md.activity.getLayoutInflater().inflate(R.layout.dialog_home_chooseicon, null);	
		GridView gr = (GridView) vg.findViewWithTag("gridView");
	
		
		//int[] a =api.reflect.getIntFiled(R.drawable.class, null, null, "abc_").values;

		gr.setAdapter(new Adapter_DialogHomeChooseIcon());
		gr.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
			String newicon=	String.valueOf(Adapter_DialogHomeChooseIcon.getResId(arg2));
			dhk.alertDialog.dismiss();
			jj.db.execSQL("update "+md.mt.tableName+" set "+md.mt.Field.icon+" ="+newicon+" where id="+itemInfo.id);  			
			//这里要用post 要在dhk 完全关闭后 才能更新ui
			jj.gridview.postDelayed(new Runnable() {
					@Override
					public void run() {
						jj.gridViewAdapter.refresh();
						
					}
				}, 100);
						
			}
                                                                        
		});

		dhk.setUserBuilder(true);
		//dhk.builder = new AlertDialog.Builder(md.context);	
		dhk.builder.setView(vg);
		dhk.showAlertDialog();

	}
	
	
	
	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	

}
