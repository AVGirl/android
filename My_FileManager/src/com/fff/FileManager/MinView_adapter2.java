package com.fff.FileManager;

import java.util.ArrayList;

import com.fff.entity.HomeIcon;
import com.fff.tools.ssql;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MinView_adapter2 extends BaseAdapter{
	private TextView view_name = null;
	private ImageView view_icon = null;
	public int id_index=0,name_index=1,icon_index=2;
	public Cursor data;
	MinView_adapter2(){
		refresh();
	}  
	void refresh(){  
		data=ssql.db.rawQuery("select id,name,icon from "+md.mt.tableName,null);
		this.notifyDataSetChanged();
	}  
    
	@Override
	public int getCount() {
	return data.getCount();
	}

	
	@Override
	public Object getItem(int position) {
		return position;
	}
	
	@Override
	public long getItemId(int position) {   
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		data.moveToFirst();
		data.moveToPosition(position);
		if(v==null){
			v=jj.mActivity.getLayoutInflater().inflate(R.layout.mainview_gridview_adapter, null);  
		}	
		view_icon=(ImageView) v.findViewById(R.id.view_list_icon);
		view_name=(TextView) v.findViewById(R.id.view_list_name);
		view_name.setText(data.getString(name_index));
		view_icon.setImageResource(data.getInt(icon_index));
		return v;
	}

}
