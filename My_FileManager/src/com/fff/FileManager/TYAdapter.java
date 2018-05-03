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

public abstract class TYAdapter extends BaseAdapter{
	int layouFormUser,count;
	public TYAdapter(int layouFormUser,int count) {
		this.layouFormUser = layouFormUser;
		this.count=count;
	}
	        
	  
	@Override
	public int getCount() {
		return count;
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
		setChildData(sv,position);
		return sv.itemLayout;
	}
	
	
	
abstract void setChildData(SavedView s,int position);//T app/book/person...
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
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>
		public void setResId(int childViewID, int ResId) {
			View v = getChildView(childViewID);
			if (v instanceof ImageView) {
				((ImageView) v).setImageResource(ResId);
			} else {
				v.setBackgroundResource(ResId);
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