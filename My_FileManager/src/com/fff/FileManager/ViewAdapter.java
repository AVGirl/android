package com.fff.FileManager;

import java.io.File;
import java.util.HashMap;

import com.fff.FileManager.ViewFactory.View_Adapter_IN;
import com.fff.FileManager.ViewFactory.View_Adapter_INTS;

import com.fff.entity.AsynData3;
import com.fff.misc.ViewShow;
import com.fff.misc.cfg;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.ssql;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAdapter extends BaseAdapter {
	private TextView view_name = null;
	private TextView view_time = null;
	private ImageView view_icon = null;
	private TextView view_size = null;
	String whatKind;

	static  HashMap<String, Bitmap> hashMap= new HashMap<>();

	
	boolean enableSelect=false;
	
	private int Gposition;
	private static View Gv;
	private static ViewGroup Gparent;
public static boolean show_thumbnail;
	
	public  Cursor cursor; 
	
	static final String cacheDir = md.pic_cacheDir + "/";

	int layout_info_IN;
	int layout_info_INTS;

	
	String parentPath="";
	private Drawable drawable;
	private FileBrowser fileBrowser;
	private String format;
	
	
	
	ViewAdapter(FileBrowser fileBrowser){
		this.fileBrowser=fileBrowser;
	}
	
	void refreshView() {
		if (layout_info_IN != 0) {
			// ff.log("-------------layout_IN");
			layout_IN(layout_info_IN);
			return;
		}

		// ff.log("-------------layout_info_INTS");
		layout_INTS(layout_info_INTS);

	}

//_______________________________________________________________________	
private void layout_IN(int layout){
	int currView=0;
	

		if(Gv==null){		  	
		
			if (layout == ViewShow.ViewMode.grid_l[1]) {

				if (whatKind.equals(FileOperation.FileType.type.video.name())) {
					layout = R.layout.view_gird_border_video_l;

				}

			}	

			layout_IN(new View_Adapter_IN(layout),false);
			
	
			return;
		}
//____________________________________________________________not null
		currView=(int)Gv.getTag();	
		
		if (layout == ViewShow.ViewMode.grid_l[1]) {
			if (!whatKind.equals(FileOperation.FileType.type.video.name())) {
				if (currView == R.layout.view_gird_border_video_l) {
					layout_IN(new View_Adapter_IN(layout), false);
					return;

				}

			}

		}
		
		
		
		layout_IN(null,true);
		// 
	//	ff.log("whatKind",whatKind,"layout:"+layout,"view_gird_border_video_l"+R.layout.view_gird_border_video_l);
}

private  void layout_IN(View_Adapter_IN in,boolean find){   
	//ff.log("uuuuu");
	if(find==true){
		view_name=(TextView) Gv.findViewById(R.id.view_list_name);
		view_icon=(ImageView) Gv.findViewById(R.id.view_list_icon);	   
	}else{
		Gv = in.v;
		view_name=in.view_name;		
		view_icon=in.view_icon;	
	}	        
	
	view_name.setText(cursor.getString(FieldIndex.name)); 	

	
	view_icon.setTag(1);
	if(drawable!=null){
		view_icon.setImageDrawable(drawable);
		return;
	}
	
	view_icon.setImageBitmap(getFileTypeIcon(format));
	//view_icon.setImageResource(cursor.getInt(FieldIndex.icon));//TODO 

	
}	
//_______________________________________________________________________________________________	
private  void layout_INTS(int layout) {
		if (Gv == null) {
			ViewFactory.View_Adapter_INTS vv = new View_Adapter_INTS(layout);
			layout_IN(vv, false);
			view_size = vv.view_size;
			view_time = vv.view_time;
		} else {
			layout_IN(null, true);
			view_size = (TextView) Gv.findViewById(R.id.view_list_size);
			view_time = (TextView) Gv.findViewById(R.id.view_list_time);
		}
		
		view_time.setText(cursor.getString(FieldIndex.modified));
		view_size.setText(cursor.getString(FieldIndex.size));
	}
	




	  void view_info_gird(){
		  layout_IN(R.layout.view_info_grid);
	}
	 void view_info_list(){
		 layout_INTS(R.layout.view_info_list);
	}
	
	
	
	
 
/*ViewAdapter(FileExplorer fileExplorer){
	 this.fb=fileExplorer;
}*/
 

	@Override
	public int getCount() {	
		if(cursor==null){return 0;}
		return cursor.getCount();
	}

	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public long getItemId(int position) {   
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		cursor.moveToFirst();
		cursor.move(position);
		Gposition = position;
		Gv = v;
		Gparent = parent;

		whatKind = cursor.getString(FieldIndex.kind);
		format = cursor.getString(FieldIndex.format);
		String cacheFile = md.pic_cacheDir + "/" + cursor.getString(FieldIndex.modified).hashCode()
				+ cursor.getString(FieldIndex.name) + "..";

		
		if (show_thumbnail) {

			drawable = null;
			if (new File(cacheFile).exists()) {
				drawable = Drawable.createFromPath(cacheFile);
			}

		}
		
		refreshView();
//ff.sc(show_thumbnail);		
		if (show_thumbnail && cursor.getInt(FieldIndex.thumb) == 1) {
			
			if(cursor.getInt(FieldIndex.canRead)==1 && drawable==null){
				
				view_icon.setTag(0);//在图片较多的情况下，避免重复刷新图片

				AsyncLoad.asynData.addFirst(new AsynData3(view_icon, position,cacheFile));
				
				if (AsyncLoad.isRunning == null) {				
					new AsyncLoad(parentPath,cursor).execute("");
					
				}
				
			}			
			
		}

		

		if(enableSelect){
			
			if(fileBrowser.absl.isItemChecked(position)){			
				Gv.setBackgroundColor(md.selectedBg);
			}else{
				Gv.setBackgroundColor(Color.TRANSPARENT);
			}		
		}
		

		return Gv;
	}

	
	
	
	
	
	
Bitmap getFileTypeIcon(String s){
	s=s.toLowerCase();
		Bitmap b = hashMap.get(s);
		if (b == null) {
		//	ff.sc(44, 44);
			b = BitmapFactory.decodeFile(md.file_format_dir + "/" + s);
		} else {
			return b;
		}
		
		
		if(b==null){
			b=hashMap.get("0");
			if(b==null){
				b=BitmapFactory.decodeFile(md.file_format_dir+"/0");
				hashMap.put("0", b);
			}
			
		}
		
		
		hashMap.put(s, b);
		return b;

	}
	
	
}











