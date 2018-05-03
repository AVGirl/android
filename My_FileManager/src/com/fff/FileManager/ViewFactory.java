package com.fff.FileManager;

import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.misc.ViewShow.ViewMode;
import com.fff.tools.DensityTool;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewFactory {

	
void test(){

}
//________________________________View_Adapter_IN(icon,name)
static class View_Adapter_IN{
	View v;  
	ImageView view_icon; 
	TextView view_name;
	View_Adapter_IN(int layout){    

		v=jj.mActivity.getLayoutInflater().inflate(layout, null);
		v.setTag(layout);
		
		view_icon= (ImageView) v.findViewById(R.id.view_list_icon);
		view_name=(TextView) v.findViewById(R.id.view_list_name);
		
	}	                  
	         
}
//________________________________View_Adapter_INTS(icon,name,time,size)
static class View_Adapter_INTS extends View_Adapter_IN{
		TextView view_time;
		TextView view_size;	 
		View_Adapter_INTS(int layout){ 
			super(layout);
		view_time=(TextView) v.findViewById(R.id.view_list_time);
		view_size=(TextView) v.findViewById(R.id.view_list_size);
	}	                  
	      
}

//____________________________________________________________________________	



}
