package com.fff.FileManager;
//getChildAt
import java.io.File;
import java.util.LinkedList;

import com.fff.FileManager.R.color;
import com.fff.entity.SortbyGridData;
import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.preview.PreparePreview;
import com.fff.misc.ViewShow.ViewMode;
import com.fff.tools.FileOperation;
import com.fff.tools.ssql;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
  
public class Impl_FileBrowser implements OnItemClickListener, OnItemLongClickListener{ 
	private  Cursor data;
	android.widget.AbsListView absListView;
	private FileBrowser src;

	public  FileBrowser getSrc(){
	return	src= jj.getCurrFileExplorer();	
	}
	

void viewSortBy(){
	AlertDialog.Builder b=new AlertDialog.Builder(jj.mContext);
	final AlertDialog win=b.show();;
	LinkedList<SortbyGridData> arr1=new LinkedList<>();
	LinkedList<SortbyGridData> arr2=new LinkedList<>(); 
	View v=jj.mActivity.getLayoutInflater().inflate(R.layout.toolbar_sortby, null);
	GridView gvA=(GridView) v.findViewById(R.id.toolbar_sortby_GridLayoutA);
	GridView gvB=(GridView) v.findViewById(R.id.toolbar_sortby_GridLayoutB);
	 arr1.add(new SortbyGridData("越大图标", R.drawable.toolbar_view_icon_l,ViewShow.ViewMode.grid_l));
	 arr1.add(new SortbyGridData("图标", R.drawable.toolbar_view_icon_m,ViewShow.ViewMode.grid_m));
	 arr1.add(new SortbyGridData("2列", R.drawable.toolbar_view_icon_s,ViewShow.ViewMode.grid_s));
	 arr1.add(new SortbyGridData("列表(大)", R.drawable.toolbar_view_list_l,ViewShow.ViewMode.list_l));
	 arr1.add(new SortbyGridData("列表 (中)", R.drawable.toolbar_view_list_m,ViewShow.ViewMode.list_m));
	 arr1.add(new SortbyGridData("列表(小)", R.drawable.toolbar_view_list_s,ViewShow.ViewMode.list_s));
	 arr1.add(new SortbyGridData("详情(列表)", R.drawable.toolbar_view_detail_l,ViewShow.ViewMode.Info_list));
	 arr1.add(new SortbyGridData("详情(图标)", R.drawable.toolbar_view_detail_m,ViewShow.ViewMode.Info_grid));
	 RawTYAdapter<SortbyGridData> Adaptera=new RawTYAdapter<SortbyGridData>(arr1,R.layout.toolbar_sortby_adapter){
		@Override
		void setChildData(com.fff.FileManager.RawTYAdapter.SavedView s, SortbyGridData t) {
			s.setText(R.id.toolbar_sortby_adapter_TextView, t.getName());
			s.setDrawable(R.id.toolbar_sortby_adapter_imageView, t.getIcon());
		}};

		//___________________________________________________________________________________
		
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_name_ascending,ViewShow.ViewSortBy.name));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_type_ascending,ViewShow.ViewSortBy.format));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_size_ascending,ViewShow.ViewSortBy.length));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_time_ascending,ViewShow.ViewSortBy.modified));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_name_descending,ViewShow.ViewSortBy.name_desc));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_type_descending,ViewShow.ViewSortBy.format_desc));
		arr2.add(new SortbyGridData(R.drawable.toolbar_sort_size_descending,ViewShow.ViewSortBy.lengthDesc));
		 arr2.add(new SortbyGridData(R.drawable.toolbar_sort_time_descending,ViewShow.ViewSortBy.modified_desc));
		 RawTYAdapter<SortbyGridData> AdapterB=new RawTYAdapter<SortbyGridData>(arr2,R.layout.toolbar_sortby_adapter2){
			@Override
			void setChildData(com.fff.FileManager.RawTYAdapter.SavedView s, SortbyGridData t) {
				s.setDrawable(R.id.toolbar_sortby_adapter_imageView2, t.getIcon());
			}};

			
			final LinkedList<SortbyGridData>  ViewMode=arr1;
			final LinkedList<SortbyGridData>  ViewSortBy=arr2;
			class onItemClickListener555 implements OnItemClickListener{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					getSrc();
					View vv= view.findViewById(R.id.toolbar_sortby_adapter_imageView2);
					if(vv!=null){//sortBy
						cfg.putString("ViewShow-ViewSortBy", ViewSortBy.get(position).getSortByMode());
						src.Refresh();
					}else{ 			
					      	
					cfg.putInt("ViewShow-ViewMode", ViewMode.get(position).getViewMode()[0]);    
					cfg.putInt("ViewShow-ViewMode-layout", ViewMode.get(position).getViewMode()[1]);   
					cfg.putInt("ViewShow-ViewMode-Islist", ViewMode.get(position).getViewMode()[2]); 
					cfg.putInt("ViewShow-ViewMode-INFO", ViewMode.get(position).getViewMode()[3]); 		
					
				ff.sc("cfg.putInt(ViewShow-ViewMode",src.parentPath);
					
					src.handler.sendEmptyMessage(FileBrowser.msg_viewMode_change);
					
					}						
					win.dismiss();
				}	
			}    	
			onItemClickListener555 listener=new onItemClickListener555();
			gvA.setAdapter(Adaptera);
			gvA.setOnItemClickListener(listener);
			gvB.setAdapter(AdapterB);      
			gvB.setOnItemClickListener(listener);
			win.setContentView(v);
			
}


//_________________________________________________________________________
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		getSrc();	

		//father.data.moveToFirst();
		src.data.moveToPosition(position);
		
		src.handler.sendEmptyMessage(src.msg_onItemClick);
		jj.handler.sendEmptyMessage(md.mainUImsg.browserOnItemClick);

		if(src.isEnabledMulti()){
	
			
		//	ff.sc("father.isEnabledMulti()",father.data.getString(FieldIndex.name),father.parentPath);
		//ff.log(view.getClass().getName());
		
			boolean b=src.absl.isItemChecked(position);
			boolean newV=b;
			
			// 尚不原因？？？？？？？？？？？？？？？？？？？
			//CHOICE_MODE_MULTIPLE ==2
			//CHOICE_MODE_NONE == 0
			int mode= src.absl.getChoiceMode();
		//ff.sc(AbsListView.CHOICE_MODE_NONE, father.absl.getChoiceMode());			
		//ff.sc("当前："+b,"新:"+newV,src.data.getString(FieldIndex.name));
		//
		
	/*	if(mode==0){
			 newV=!b;
		}else{
			 newV=b;
		}*/
		
		src.absl.setItemChecked(position, newV);
		
		
			if(newV==true){
				view.setBackgroundColor(md.selectedBg);
			}else{
				view.setBackgroundColor(Color.TRANSPARENT);
			}				
			
			src.notifyDataSetChanged();		
			
			
			jj.ToolbarListener.refreshToolBarTitle();	
			return;
		}	

		
		
		//father.ScrollY=father.gridView.getScrollY();	
	//	ff.toast("father.ScrollY:"+father.gridView.getVerticalScrollbarPosition());
		int getTop=	view.getTop();
		//ff.toast(getTop+"  getTop");
	//	father.offset=getTop;
		
		
		src.clickPositon=position;
		data=src.data;
		data.moveToFirst();
		data.moveToPosition(position);
		int isFile=src.dbGetInt(position, FieldIndex.isFile);
		
		String file=null;
		String path1= data.getString(FieldIndex.parent);
		if(path1!=null){
			file=path1+"/"+data.getString(0);	
		}else{
			file=src.parentPath+data.getString(0);	
		}
		
			
		
		
		
		if(isFile==0){
	//	ff.log("******************onItemClick");
			src.loadDir(file);	 
		}else{
			boolean bb=PreparePreview.preparePreview(file,src.data,position);
			if(bb==true){
				return;
			}
			
			FileOperation.openFile(file, jj.mActivity);
		}
		
		
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		getSrc();
		
		
		//src.disabledMulti();
		src.setMultiplSelect();
		
		src.absl.setItemChecked(position, true);
		
		src.notifyDataSetChanged();
		
		//father.showMultiSelectToolbar(true);
		
		src.handler.sendEmptyMessage(src.msg_onItemLongClick);
		
		jj.handler.sendEmptyMessage(md.mainUImsg.browserOnItemLongClick);
		
		return true;
	}



	



}
