package com.fff.FileManager;

import java.io.File;

import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.api;
import com.fff.tools.ssql;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FileMenuOnclick {
	static FileBrowser src;

	
	 static  FileBrowser getSrc(){
		src=jj.getCurrFileExplorer();
		return jj.getCurrFileExplorer();
	}

	
	public static void onCheckedChanged(RadioGroup group, int checkedId) {	
		getSrc();
	
		dhk.popWinClose();
		
		switch (checkedId) {
		case R.id.dxmrgj_cut:
			FileOperation.cutFile(src);
			break;
		case R.id.dxgjl_more_paste:
			FileOperation.paste(src);
			break;
		case R.id.dxgjl_copy:
			FileOperation.copy(src);
			break;
		case R.id.dxgjl_rename:
			FileOperation.rename(src);
			break;
		case R.id.dxgjl_more_refresh:
			src.Refresh();
			break;
		case R.id.dxgjl_more:
			showMoreTools();
			break;
		case R.id.dxgjl_more_share:
			break;
		case R.id.dxgjl_more_favorites:
			break;
		case R.id.dxgjl_more_property:
			property();
			break;
		case R.id.dxgjl_delete:
			FileOperation.delete(src);
			break;
		case R.id.dxgjl_more_shortcuts:
			break;
		case R.id.dxgjl_more_addToHome:
			addtoHomepage();
			break;
		case R.id.dxgjl_more_copyPath:
			
		copyPath();
			
			break;
		
		case R.id.dxgjl_more_newWin:
			openInNewWindown();
			break;

			
		}

		RadioButton dd= (RadioButton) group.findViewById(checkedId);
		dd.setChecked(false);
		//ff.log(dd.getText(),dd.getId());
	}	
	


	
	private static void property_one(int i) {
		final String path=src.dbGetString(i, FieldIndex.parent)+"/"+src.dbGetString(i, FieldIndex.name);
		File a=new File(path);	String name=a.getName();
		String modified=src.dbGetString(i, FieldIndex.modified);
		String size=src.dbGetString(i, FieldIndex.size);
		String rr[]=new String[2];
		String type=FileOperation.getFileType(path);  
		FileOperation.getFileTypeIcon(type, rr);
		String canRead=a.canRead()+"";
		String canWrite=a.canWrite()+"";
		String hidden=a.isHidden()+"";
		
		View v=md.activity.getLayoutInflater().inflate(R.layout.dialog_file_menu_onclick, null);
		((TextView)v.findViewById(R.id.a1001_fileName)).setText(name);;
		((TextView)v.findViewById(R.id.a1001_size)).setText(size);;
		((TextView)v.findViewById(R.id.a1001_path)).setText(path);;
		((TextView)v.findViewById(R.id.a1001_modified)).setText(modified);;
		((TextView)v.findViewById(R.id.a1001_canRead)).setText(canRead);;
		((TextView)v.findViewById(R.id.a1001_canWrite)).setText(canWrite);;
		((TextView)v.findViewById(R.id.a1001_hidden)).setText(hidden);;
		
		
		((TextView)v.findViewById(R.id.a1001_type)).setText(rr[0]+"");;
		  if(type.equals("")){
			  type="0";
		  }
		
		((ImageView) v.findViewById(R.id.a1001_img)).setImageBitmap(BitmapFactory.decodeFile(md.file_format_dir+"/"+type));;
		
		
		class Onclick implements View.OnClickListener{
			@Override
			public void onClick(View v) {
				//dhk.alertDialog.dismiss();
				
				if(v.getId()==R.id.a1001_cancle){
					dhk.alertDialog.dismiss();		
					return;    
				}
				if(v.getId()==R.id.a1001_path){
					dhk.alertDialog.dismiss();	
					File a=new File(path);
					String u=null;
					if(a.isDirectory()){
						u=path;
					}else{
						u=a.getParent();
					}
					Message t2=Message.obtain(jj.handler, md.mainUImsg.openNewWindow, 0, 0, u);
					jj.handler.sendMessageDelayed(t2, 100);
					return;    
				}
				
				
				
				
				if(v.getId()==R.id.a1001_copyPath){
					File a=new File(path);
					String u=null;
					if(a.isDirectory()){
						u=path;
					}else{
						u=a.getParent();
					}
					
					api.setClipText(u);
				}
				if(v.getId()==R.id.a1001_copyWholePath){
					api.setClipText(path);
				}
				ff.toast(md.context, "已复制");
			//	ff.sc("ff.toast(md.context, 已复制);");
				
			}}
		Onclick dd=new Onclick();
		v.findViewById(R.id.a1001_copyPath).setOnClickListener(dd);
		v.findViewById(R.id.a1001_copyWholePath).setOnClickListener(dd);
		v.findViewById(R.id.a1001_cancle).setOnClickListener(dd);
		v.findViewById(R.id.a1001_path).setOnClickListener(dd);		
		dhk.setUserBuilder(true);
		dhk.builder.setView(v);
	
		dhk.showAlertDialog();
	/*	dhk.popWinInit();
		dhk.popWin_setAlpha();
		dhk.popWin.setContentView(v);
		dhk.popWin.setWidth(LayoutParams.WRAP_CONTENT);
		dhk.popWin.setHeight(LayoutParams.WRAP_CONTENT);
		dhk.popWin.setFocusable(false);  
		dhk.popWin.showAtLocation(v, Gravity.CENTER, 0, 0);
		*/
		
	}
	
	private static void property() {
		int []a=src.getCheckedItemDatas();
		if(a.length==0){
			return;
		}
		if(a.length==1){
			property_one(a[0]);
			return;
		}
		
		
		long size=0;
		int file=0;
		 int folder=0;
		 String y[]=FileOperation.getChoiceFilePath(src);
		for (int i = 0; i < y.length; i++) {
			//src.data.moveToPosition(a[i]);
	
			File f = new File(y[i]);
			size += f.length();
			if (f.isDirectory()) {
				folder++;
			} else {
				file++;
			}
		}
		 String q="合计："+FileOperation.decimalFormat.format((double)size/1024/1024)+"Mb";
		 String msg=q+"\n\n文件 : "+file+"\n\n文件夹 : "+folder;
		 dhk.dhk(msg, "属性", "行,我知道了", "");
		
		
	}


	static  void copyPath(){
		int ww[]=src.getCheckedItemDatas();
		if(ww.length==0){if(1>0){return;};}
		src.data.moveToPosition(ww[0]);
		String t=src.data.getString(FieldIndex.parent)+"/"+ src.data.getString(FieldIndex.name);
		api.setClipText(t);
	}
	
	
	
	static void openInNewWindown(){
		//TODO
		int ww[]=src.getCheckedItemDatas();
		if(ww.length==0){if(1>0){return;};}
		src.data.moveToPosition(ww[0]);
		String t=src.parentPath+ src.data.getString(FieldIndex.name);
		jj.openNewWindow(t);
	}
	
	
	public static void showMoreTools(){	
		View v=jj.mActivity.getLayoutInflater().inflate(R.layout.dxgjl_more, null);
		RadioGroup rad=(RadioGroup) v.findViewById(R.id.dxgjl_more_RadioGroup);	 
		
		
		dhk.popWinInit();
		dhk.popWin.setWidth(api.getActWidth()/10*8);
		dhk.popWin.setContentView(v);
		dhk.popWin.setHeight(-2);
		rad.setOnCheckedChangeListener(jj.ToolbarListener);
		v.setBackgroundColor(Color.WHITE);
		api.setWinAlpha(0.5f);
		dhk.popWin.showAtLocation(jj.mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
			
		  
	}

	
	
	
	
	

	static void addtoHomepage() {
		int[] e =src.getCheckedItemDatas();
		ff.sc(e.length, "e.length");
		for (int i = 0; i < e.length; i++) {
			src.data.moveToPosition(e[i]);
			
			String type = md.mt.type_dir;
			int isfile = src.data.getInt(FieldIndex.isFile);
			if (isfile == 1) {
				type = md.mt.type_file;
			}
			
			String name = src.data.getString(FieldIndex.name);
			String target = src.parentPath + name;
			int icon = src.data.getInt(FieldIndex.icon);
			  
			addtoHomepage2(name, type, target, icon);
		}
		jj.gridViewAdapter.refresh();

		// MainActivity.minView_adapter.notifyDataSetChanged();
	}
 
 public static void addtoHomepage2(String name,String type,String target,int icon){
	
	   String gg="insert into "+md.mt.tableName+"(name,type,target,icon)values(?,?,?,?)";
	   String exist= ssql.queryString("select target from "+md.mt.tableName+" where target='"+target+"'",null);
	          if(exist==null){
	          jj.db.execSQL(gg, new Object[]{name,type,target,icon});					   
	          }    

}

	
}
