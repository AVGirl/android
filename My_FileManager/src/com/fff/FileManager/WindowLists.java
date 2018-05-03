package com.fff.FileManager;

import com.fff.adapter.WindowListAdapter;
import com.fff.misc.ff;
import com.fff.tools.DensityTool;
import com.fff.tools.api;

import android.app.ActionBar.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;

public class WindowLists {
	
private static View windowLists;
public static QQ22 qq22;
private static WindowListAdapter windowListAdapter;
WindowLists(){
	
}



public  static void show(){
	if(windowLists==null){
		init();
	}
	 
	 dhk.popWinInit();
	 dhk.popWin.setHeight(api.getActHeight()/10*8);
	 dhk.popWin.setWidth(api.getActWidth()/10*8);
	 dhk.popWin.setContentView(windowLists);
	 windowLists.setLeft(0);
	 windowLists.setTop(0);
	  
	 dhk.popWin.setBackgroundDrawable(md.context.getResources().getDrawable(R.drawable.abc_spinner_ab_holo_dark));
	// dhk.popWin.showAtLocation(M, Gravity.LEFT, 0, DensityTool.dp2px(md.context, 20));
	 dhk.popWin.showAsDropDown(jj.toolbar, 0,0);
	  
}

   


private static void init() {
	  qq22=new QQ22();
	   windowListAdapter=new WindowListAdapter();
	windowLists=md.activity.getLayoutInflater().inflate(R.layout.windows_list, null);
	 ListView lv=(ListView) windowLists.findViewById(R.id.listView);
	 lv.setAdapter(windowListAdapter);
	// lv.setOnItemClickListener(new QQ11());
	 lv.setFocusable(true);

	 //,对应xml : android:focusable="true".                                
			 lv.setFocusableInTouchMode(true);
	 //,对应xml : android:focusableInTouchMode="true".
	 
/*	 ImageButton aa=(ImageButton) windowLists.findViewById(R.id.windows_list_adapter_close);
	 aa.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ff.log("ddfdfd");
			
		}
	});
	 */
	 
}	
 
/*


static class QQ11 implements OnItemClickListener{
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		dhk.popWin.dismiss();	
		
		ff.log(position);
	}
	
}*/


static void windows_list_adapter_close(View v){
	//ff.log("windows_list_adapter_close");
}

static void titleOnclick(View v){
	//ff.log("titleOnclick");
}

static class QQ22 implements View.OnClickListener{
	@Override
	public void onClick(View v) {

		int id=(int) v.getTag();
		
		if(v.getId()==R.id.windows_list_adapter_close){
	
		jj.removeFileExplorer(id);		
		
		if(jj.arrFE.isEmpty()){
		jj.showHomePage(0);
		dhk.popWinClose();
		}	
		
		
		windowListAdapter.notifyDataSetChanged();
		}
		
		if(v.getId()==R.id.textView){
			jj.quanHuanWin(id);	
			dhk.popWinClose();
		}
		
		
	}

}



}
