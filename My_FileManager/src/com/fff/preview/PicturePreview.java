package com.fff.preview;

import java.io.File;

import com.fff.FileManager.FieldIndex;
import com.fff.FileManager.R;
import com.fff.FileManager.dhk;
import com.fff.FileManager.md;
import com.fff.misc.ff;
import com.fff.tools.api;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class PicturePreview {

	private static View ui;
	private static ImageView iv;
	private static Bitmap pic;
	private static int position;
	private static Cursor cursor;
	private static String parent;

	static class onclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.preview_pic_tool_prev:
				showPrev();
				break;
			case R.id.preview_pic_tool_next:
				showNext();
				break;

			case R.id.preview_pic_tool_close:
				dhk.popWinClose();
				break;
		}


		}
	}

	public static void load(String path) {
		pic = BitmapFactory.decodeFile(path);
		if(pic==null){
			pic=BitmapFactory.decodeResource(md.context.getResources(), R.drawable.format_picture_broken);					
		}		
		
		
		
		iv.setImageBitmap(pic); 
		pic = null;
		System.gc();	  
	}
	
	
	public static void showNext() {
		if (position + 1 > cursor.getCount() - 1) {
			return;
		}

		position++;	
		cursor.moveToPosition(position);
		load(parent + "/" + cursor.getString(FieldIndex.name));
		

	}

	public static void showPrev() {
		if (position - 1 < 0) {
			return;
		}

		position--;
		cursor.moveToPosition(position);
		
		load(parent + "/" + cursor.getString(FieldIndex.name));	

	}

	public static void setOnclick(View v,String whatName,View.OnClickListener onclick){
		int []a=api.reflect.getIntFiled(R.id.class, null, whatName, null).values;
		for (int i = 0; i < a.length; i++) {
			v.findViewById(a[i]).setOnClickListener(onclick);;
		}
	}
	
	
	public static void show(String file, Cursor cursor1, int position1) {
		parent = new File(file).getParent();
		// ff.log("parent:"+parent);

		cursor = cursor1;
		position = position1;

	//	pic = BitmapFactory.decodeFile(file);
		if (ui == null) {
			ui = md.activity.getLayoutInflater().inflate(R.layout.preview_pic, null);
			iv = (ImageView) ui.findViewById(R.id.imageView);
			setOnclick(ui,"preview_pic_tool",new onclick());

		}
		
		load(file);


		dhk.popWinInit();
		dhk.popWin.setWidth(api.getActWidth() / 10 * 9);
		dhk.popWin.setHeight(api.getActHeight() / 10 * 5);

		dhk.popWin_setAlpha();
		dhk.popWin.setContentView(ui);
		/*
		 * dhk.popWin.setWidth(api.getActWidth() );
		 * dhk.popWin.setHeight(api.getActHeight() );
		 */

		dhk.popWin.showAtLocation(ui, Gravity.CENTER, 0, 0);

	}

}

/*
 * 居中
 * 
 * iv.setImageMatrix(null); iv.setImageBitmap(pic);
 * iv.getLayoutParams().width=pic.getWidth();
 * 
 * iv.postDelayed(new Runnable() {
 * 
 * @Override public void run() { // TODO Auto-generated method stub
 * ff.log(5555); Matrix m= iv.getImageMatrix();
 * m.postTranslate(-pic.getWidth()/3, 0); iv.setImageMatrix(m); } }, 1000);
 */
