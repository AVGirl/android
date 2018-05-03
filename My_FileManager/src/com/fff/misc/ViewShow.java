package com.fff.misc;



import java.util.Arrays;

import com.fff.FileManager.jj;
import com.fff.FileManager.R;
import com.fff.tools.DensityTool;
import com.fff.tools.api;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewShow {
public  static  View viewListAdapter;
static Context mContext;




      
 public static int[] getViewMode(){
	 int a=	cfg.read.getInt("ViewShow-ViewMode", ViewMode.list_m[0]);
	 int b=	cfg.read.getInt("ViewShow-ViewMode-layout", ViewMode.list_m[1]);
	 int c=	cfg.read.getInt("ViewShow-ViewMode-Islist", ViewMode.list_m[2]);
	 int d=	cfg.read.getInt("ViewShow-ViewMode-INFO", ViewMode.list_m[3]);
	 int ab[]={a,b,c,d}; 
	 
	// ab=ViewMode.list_m;
return ab;
 }            
public static class ViewMode {
public static final byte Mode_id=0;	
public static final byte Mode_Layout=1;	
public static final byte Mode_view=2;	
public static final byte Mode_info=3;	


	
public static   final byte INFO_IN=1;
public static   final byte INFO_INTS=2;
public static   final byte VIEW_LIST=1;
public static   final byte VIEW_GRID=2;

//1:id  2:layout 3:view  4:info  2130903077
 public static   final int[] list_m={11,R.layout.view_list_m,VIEW_LIST,INFO_IN};
 public static  final  int[] list_l={12,R.layout.view_list_l,VIEW_LIST,INFO_IN};
 public static  final  int[] list_s={13,R.layout.view_list_s,VIEW_LIST,INFO_IN};
 public static  final  int[] Info_list={21,R.layout.view_info_list,VIEW_LIST,INFO_INTS};
 public static   final int[] Info_grid={22,R.layout.view_info_grid,VIEW_GRID,INFO_INTS};
 public static  final  int[] grid_m={31,R.layout.view_grid_m,VIEW_GRID,INFO_IN};                                   
 public static  final  int[] grid_l={32,R.layout.view_grid_l,VIEW_GRID,INFO_IN};
 public static   final int[] grid_s={33,R.layout.view_grid_s,VIEW_GRID,INFO_IN};
 
 //______________________________________________
public static int getGirdNumColunms() {
				int []a=getViewMode();
			if (Arrays.equals(a, ViewMode.grid_m)) {
				return 5;
			}
			if (Arrays.equals(a, ViewMode.grid_l)) {
				return 2;
			}
			if (Arrays.equals(a, ViewMode.grid_s)) {
				return 2;
			}
			if (Arrays.equals(a, ViewMode.Info_grid)) {
				return 2;
			}
			return 6;
		}

	}

//___________________________________________________________________

public static String getViewSortBy(){
return cfg.read.getString("ViewShow-ViewSortBy",ViewSortBy.name );
}
public static class ViewSortBy {
	 public static  final String  format_desc=" format desc ";
	 public static  final String  format=" format asc ";
	 public static  final String  lengthDesc=" length desc ";
	 public static  final String  length=" length asc ";
	 public static  final String  modified=" modified asc ";
	 public static  final String  modified_desc=" modified desc ";
	 public static  final String  name_desc=" name COLLATE LOCALIZED  desc "; 
	 public static final String name=" name COLLATE LOCALIZED  asc "; 
}




 


public	static final class ThumbnailMode{ 
	
public	static int picture=cfg.read.getInt("ThumbnailMode-picture", ThumbnailMode.Picture.middle);


public	static final class Picture{
		public	static final int high=250;
		public	static final int middle=100;
		public	static final int low=50;
}	



	
/*	public	static final int high[]=new int[]{250,250};
	public	static final 	int middle[]=new int[]{100,100};
	public	static final 	int low[]=new int[]{50,50};*/
/*	public	static final int high[]=new int[]{250,250};
	public	static final 	int middle[]=new int[]{100,100};
	public	static final 	int low[]=new int[]{50,50};
	*/
	
}
	








//___________________________________________________________________
}
