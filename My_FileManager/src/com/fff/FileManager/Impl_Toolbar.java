package com.fff.FileManager;

import java.io.File;

import com.fff.adapter.WindowListAdapter;
import com.fff.misc.ff;
import com.fff.tools.api;
import com.fff.tools.AppSettings;
import com.fff.tools.FileOperation;
import com.fff.tools.ssql;

import android.content.Intent;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Impl_Toolbar implements OnCheckedChangeListener {
	FileBrowser src;
	private View windowLists;

	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(group.getId()==R.id.dxgjl_RadioGroup || group.getId()==R.id.dxgjl_more_RadioGroup){	
			FileMenuOnclick.onCheckedChanged(group, checkedId);
			return;
		}
		
		
	/*	if(group.getId()==R.id.dxgjl_more_RadioGroup){
			dxgjl_more(group,checkedId);
			return;
		}*/
		getFE();
	/*	
		ff.log("checkedId:"+checkedId,"main_toolbar_viewSortBy:"+R.id.main_toolbar_viewSortBy);
		ff.log("main_toolbar_menu:"+R.id.main_toolbar_menu);
		 */
		
		switch (checkedId) {
		case R.id.main_toolbar_viewSortBy:
			jj.impl_FileBrowser.viewSortBy();
			break;
			
		case R.id.main_toolbar_menu:
			popMenu();
			break;
	
		case R.id.main_toolbar_home:
			AsyncLoad.stop();
			jj.showHomePage(0);
			break;	
		case R.id.main_toolbar_return:
			jj.onBackPressed2();
		//	ThumbnailUtils.extractThumbnail(source, width, height)
			//getFE().onBackPressed();
			break;
		case R.id.main_toolbar_multiple_back:	
			if(jj.vs.getCurrentView()!=jj.toolbar){
				jj.vs.showPrevious();
				jj.dxgjl.setVisibility(View.GONE);
				getFE().disabledMulti();
			}
			break;
			
		case R.id.main_toolbar_windows:
			showWindows();
			break;
		case R.id.main_toolbar_multiple_all:
			selectAll(true);
			break;
		case R.id.main_toolbar_search:
			String t="";
			if(getFE()!=null){
				t=getFE().parentPath;
			}
			if(jj.gridview.getVisibility()==View.VISIBLE){
				 t="";	
			}
			
			SearchFile.showSearchFileDialog(t);

			
				break;
		case R.id.main_toolbar_multiple_null:
			selectAll(false);
		
			break;	
				
		}
		
		RadioButton dd= (RadioButton) group.findViewById(checkedId);
		dd.setChecked(false);
		//ff.log(dd.getText(),dd.getId());

		
		
	}
	
	


void showWindows(){
	 int w=jj.arrFE.size() ;
	 if(w==0){
		 return;
	 }
	 
	 
	 WindowLists.show();
	
	 
	 
	if (1 > 0) {
		return;
	}
	; 
	String list[]=new String[w];
	String a=null;
	String b=null;
	String sd=ff.getsdpath();
	for(int i=0; i< w;i++){
		a=jj.arrFE.get(i).parentPath;
		a=a.replace(sd, "");
		if(a.equals("")){a="sdcard";}
		b=FileOperation.getwjm(a);
		list[i]="\n"+b+" - "+a.substring(0, a.length()-b.length()-1)+"\n";
	}

	 
	dhk.setUserBuilder(true);
	dhk.builder.setNegativeButton("所有",	 dhk.btnClick);
	dhk.builder.setNeutralButton("关闭当前",  dhk.btnClick);
	w=dhk.singleChoiceDialog(list, 	jj.viewPager.getCurrentItem(), null, "ok");
	//ff.log(w);
	int jump=0;
	if(w==dhk.Neutral_BTN){ 
		jj.removeFileExplorer(jj.viewPager.getCurrentItem());
		jump=1;
	}
	
	if(w==dhk.Negative_BTN && jump==0){ 
		int j=jj.arrFE.size() ;
		while(j!=0){
			j--;
			jj.removeFileExplorer(j);			
			//ff.log(j);
		}
		jump=1;
		
	}
	
	ff.sc(jump);
	if(jump==0 && w!=-1){
		jj.quanHuanWin(w);	
	}
	
	
	//ff.log("MainActivity.arrFE.isEmpty()){",MainActivity.arrFE.isEmpty());
	if(jump==1){
		if(jj.arrFE.isEmpty()){
			jj.showHomePage(0);
		}	
	}
	
	

	
}	


	//____________________________________
public  void popMenu(){
	View v=jj.inflate.inflate(R.layout.popmenu_layout, null);
	RadioGroup rad=(RadioGroup) v.findViewById(R.id.popmenu_layout_RadioGroup);
	
	// dhk.popWin=new PopupWindow(v,,true); 
	 dhk.popWinInit();
	 dhk.popWin.setWidth(api.getActWidth()/10*6);
	 dhk.popWin.setHeight(LayoutParams.WRAP_CONTENT);
	 dhk.popWin.setContentView(v);
	rad.setOnCheckedChangeListener(new popMenuListener());
	v.setBackgroundColor(Color.WHITE);
	View anchor=jj.toolbar.findViewById(R.id.main_toolbar_menu);
	
	 dhk.popWin.showAsDropDown(anchor);	
}	
//_________________________

public  FileBrowser getFE(){
	this.src=jj.getCurrFileExplorer();
	return jj.getCurrFileExplorer();
}
//____________________________________________________________________
class popMenuListener implements OnCheckedChangeListener{
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		 //ff.log("checkedId",checkedId);
		 dhk.popWinClose();
		switch(checkedId){
		case R.id.popmenu_layout_new:
			FileOperation.createFileDialog(getFE());
			break;
		case R.id.popmenu_layout_clearCache:
		jj.clearCache();
	ff.sc("popmenu_layout_clearCache");	
			break;
		case R.id.popmenu_layout_addToHomePage:
			addtoHomepage001();
			break;
		case R.id.popmenu_layout_gohome:
			jj.showHomePage(0);
			break;
		case R.id.popmenu_layout_linux:
			jj.mActivity.startActivity(new Intent(jj.mContext,ADBShell.class));
			break;

		case R.id.popmenu_layout_refresh:
			getFE().Refresh();
			break;
		case R.id.popmenu_layout_tools:
			getFE().showMultiSelectToolbar(null);
			break;
		case R.id.popmenu_layout_setting_setting:
			Intent iii=new Intent(jj.mContext,AppSettings.class);
			jj.mActivity.startActivity(iii);
			break;
		case R.id.popmenu_layout_selectAll:
			selectAll(null);			
			    
			break;
		case R.id.popmenu_layout_exit:
			if(new File(md.pic_cacheDir).listFiles()!=null){
				if(new File(md.pic_cacheDir).listFiles().length>=1000){
					jj.clearCache();
				}
				
			}
			md.activity.finish();				
			break;

		case R.id.popmenu_layout_paste:
		FileOperation.paste(getFE());
			break;	
		}
	}

	
	private void addtoHomepage001() {
		String target=src.parentPath;
		int icon=R.drawable.format_folder;
		 String name=src.TextViewTitle.getText().toString();
		 String type="dir";
		FileMenuOnclick.addtoHomepage2(name,type,target,icon);
			jj.gridViewAdapter.refresh();
	}

}



  
//____________________________________________<<<<
public	int[] getCheckedItemDatas() {
		int j = getFE().absl.getCheckedItemCount();

		int[] count1 = new int[j];
		int n = 0;
		for (int i = 0; i < getFE().data.getCount(); i++) {
			if (getFE().absl.isItemChecked(i)) {
				count1[n] = i;
				n++;
			}
			;
		}

		return count1;
	}

	boolean verify(Object o) {
		if (o == null) {
			return false;
		}
		return true;
	}


//___________________________________________


void selectAll(Boolean b){
	int a=getFE().data.getCount();
	if(a==0){return;}
	getFE().showMultiSelectToolbar(true);
	
	if(b==null){
		b=!getFE().absl.isItemChecked(0);
	}
	getFE().setMultiplSelect();
	
	for(int i=0; i<a ;i++){
		getFE().absl.setItemChecked(i, b);
	}		
	getFE().notifyDataSetChanged();
	refreshToolBarTitle();
	if(b==true){
		jj.dxgjl.setVisibility(0);
	}else{
		jj.dxgjl.setVisibility(8);
	}

}




void showMultiSelectToolbar(Boolean b) {
	View v =null;
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

}




void refreshToolBarTitle(){
	getFE();
	String t=src.absl.getCheckedItemCount()+"/"+src.data.getCount()+"";
	( (TextView)jj.toolbar_multiple.findViewById(R.id.main_toolbar_multiple_TextView)).setText(t);;
}


}
