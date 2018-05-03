package com.fff.adapter;

import com.fff.FileManager.R;
import com.fff.FileManager.md;
import com.fff.tools.api;

import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Adapter_DialogHomeChooseIcon extends BaseAdapter {
public static	int getResId(int w){
		return count[w];
	}
static int count[];
	ImageView iv;
	public Adapter_DialogHomeChooseIcon(){
		count=api.reflect.getIntFiled(R.drawable.class, null, null, "abc_").values;
	}
	@Override
	public int getCount() {
		return count.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		
		if(v==null){
		 v=md.activity.getLayoutInflater().inflate(R.layout.dialog_home_chooseicon_gridview_adapter, null);
			  iv=(ImageView) v.findViewById(R.id.imageView);
		}else{
			iv=(ImageView)v.findViewById(R.id.imageView);
		}
		 
		iv.setImageResource(count[arg0]);
		iv.setTag(count[arg0]);
		
		return v;
	}

}
