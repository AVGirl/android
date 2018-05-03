package com.fff.adapter;

import java.io.File;

import com.fff.FileManager.jj;
import com.fff.FileManager.R;
import com.fff.FileManager.WindowLists;
import com.fff.FileManager.md;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WindowListAdapter extends BaseAdapter {

	private View close;
	private Button title;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jj.arrFE.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		if(v==null){
			v=md.activity.getLayoutInflater().inflate(R.layout.windows_list_adapter	, null);
		}
		  title= (Button) v.findViewById(R.id.textView);
		
		  title.setText(new File(jj.arrFE.get(position).parentPath).getName());
		
		  title.setTag(position);
		  title.setOnClickListener(WindowLists.qq22);
		  
		  
		   View qq=   v.findViewById(R.id.windows_list_adapter_close);
		   qq.setTag(position);
		   qq.setOnClickListener(WindowLists.qq22);
		  
		  return v;
	}

}
