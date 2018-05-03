package com.fff.FileManager;

import java.util.LinkedList;

import com.fff.misc.ff;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class RawTYAdapter<T> extends BaseAdapter{

	
	LinkedList<T> dataThis;
	int layouFormUser;
	public RawTYAdapter(LinkedList<T> dataThis, int layouFormUser) {
		super();
		this.dataThis = dataThis;
		this.layouFormUser = layouFormUser;
	}
	@Override
	public int getCount() {
		return dataThis.size();
	}
	@Override
	public Object getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	//-----------------------------------��getView��
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SavedView sv=SavedView.bind(position, convertView, parent, layouFormUser);
		setChildData(sv,dataThis.get(position));
		return sv.itemLayout;
	}
	
	
	
abstract void setChildData(SavedView s,T t);//T app/book/person...
	 //---------------------
static class SavedView{
		private int position;
		View itemLayout;
		SparseArray<View> children;
		Context context_savedView;
		
		
		
		
		public SavedView(View itemLayout) {
			this.itemLayout=itemLayout;
			children=new SparseArray<>();

		}
		
		
		public static SavedView bind(int position, View convertView, ViewGroup parent,int layou){
			SavedView sv;
			if (convertView==null) {
				
				convertView=LayoutInflater.from(parent.getContext()).inflate(layou, null);
				 sv=new SavedView(convertView);
				convertView.setTag(sv);
			}else{
	
				 sv=(SavedView) convertView.getTag();
			}

			return sv;
		}
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		public void setOnClickListener(int childViewID ,View.OnClickListener onClickListener){
			 View v=getChildView(childViewID);
			 v.setOnClickListener(onClickListener);
		}

	
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>
		public void setText(int childViewID ,CharSequence c){
		View v=	getChildView(childViewID);
		if(v==null){
			return;
		}
		
		if(v instanceof TextView){
			((TextView)v).setText(c);
		}else{
			ff.sc("[public  void setText] Exception");
		}

		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>
		public void setDrawable(int childViewID ,int drawableResId){
		View v=	getChildView(childViewID);
		if(v instanceof ImageView){
			((ImageView)v).setImageResource(drawableResId);
		}else{
			v.setBackgroundResource(drawableResId);
		}

		}
		
		//--------------------------
		private View getChildView(int childViewID) {
			View v =children.get(childViewID);
			if (v==null) {
				v=itemLayout.findViewById(childViewID);
				children.put(childViewID, v);
			}
			return v;
			
		}
		   //----------------------
		
		
		/*savedView β��*/
	}

	
	
	
}