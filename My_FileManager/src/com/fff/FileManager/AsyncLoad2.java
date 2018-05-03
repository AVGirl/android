package com.fff.FileManager;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.fff.entity.AsynData;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.api;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;

public class AsyncLoad2 extends AsyncTask<String, Integer, Bitmap> {
	LinkedList<AsynData> asynData = new LinkedList<>();
	private AsynData data;
	private String dir = md.pic_cacheDir + "/";
	private File file = null;

final static	String formatApk = FileOperation.FileType.type.apk.name();
final static	String formatImage = FileOperation.FileType.type.image.name();
final  static	String formatVideo = FileOperation.FileType.type.video.name();

	String cacheName;
	String parentDir;
	private ImageView Img;
	private Integer currTaskId;
	
public static ArrayList<Integer> arr=new ArrayList<>();	
	

	AsyncLoad2(String parentDir) {
		this.parentDir = parentDir;
	}

	Bitmap format_Image() {

		Bitmap bit = api.getImageThumbnail(parentDir + data.getWjm(), 250, 250);
		//ThumbnailUtils.extractThumbnail(source, width, height)
		try {
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.JPEG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;

	}


	Bitmap format_Video() {
		Bitmap bit = api.getVideoThumbnail(parentDir + data.getWjm(),MediaStore.Images.Thumbnails.MINI_KIND);
	
	Bitmap bb=	BitmapFactory.decodeResource(md.context.getResources(), R.drawable.video_border2).copy(Config.ARGB_8888, true);
		Canvas ca=new Canvas(bb);
		drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)；
		Rect src: 是对图片进行裁截，若是空null则显示整个图片
		RectF dst：是图片在Canvas画布中显示的区域，大于src则把src的裁截区放大，小于src则把src的裁截区缩小

		关于
		int w=bb.getWidth(),h=bb.getHeight();
		//ff.log(bit.getWidth()-w,"w:"+w,"bit:"+bit.getWidth());
		Rect q1=new Rect(40,20,w+40,h+20);
		Rect q2=new Rect(31,11,w-34,h-13);
		ca.drawBitmap(bit, q1, q2, new Paint());
		bit=bb;
		
		
		try {
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.JPEG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;

	}

	Bitmap format_Apk() {

		Bitmap bit = api.apkInfo(parentDir + data.getWjm());
		try {
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bit;
	}

	@Override
	protected Bitmap doInBackground(String... params) {

		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
		
		data = asynData.removeFirst();
		
		 currTaskId=(Integer)data.getImg().hashCode();	 
		 
		 
		 arr.remove(currTaskId);
		if(arr.contains(currTaskId)){			
				//try {Thread.sleep(500);} catch (Exception e) {}		
		ff.log("if(arr.contains(currTaskId)){::",currTaskId);
		}
	
		
		if(!parentDir.startsWith(md.sdcard)){
			File a=new File(parentDir+ data.getWjm());
			if(!a.exists() || a.canRead()==false){
				return null;
			}
		}
			
	
		Img = data.getImg();
		cacheName = dir + data.getTime() + data.getWjm();
		file = new File(cacheName);
		
		arr.add(currTaskId);
		
		
		if (file.exists()) {
			return BitmapFactory.decodeFile(cacheName);
		}

		
		if (data.getKind().equals(formatImage)) {
			return format_Image();
		}
		if (data.getKind().equals(formatApk)) {
			return format_Apk();
		}
		if (data.getKind().equals(formatVideo)) {
			return format_Video();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			
			if (data.getKind().equals(formatVideo)) {
		
				Img.setImageResource(R.drawable.video_border2);
				//Img.setBackgroundResource(R.drawable.video_border);
				
				Img.setBackground(new BitmapDrawable(result));
				return ;
			}
			int j=0;
			for (int i = 0; i < arr.size(); i++) {
				if(arr.get(i)==currTaskId){
					j++;
				}
			}
			
			
		
			arr.remove(currTaskId);
			if(!arr.contains(currTaskId)){
				Img.setImageBitmap(result);
			}
			
			
			//ff.log("onPostExecute:"+currTaskId,Img.hashCode());
	
			result=null;
		}

	}
}
*/