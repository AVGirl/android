package com.fff.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.fff.FileManager.R.drawable;

import com.fff.entity.AsynData3;
import com.fff.misc.ViewShow;
import com.fff.misc.ff;
import com.fff.tools.FileOperation;
import com.fff.tools.api;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

public class AsyncLoad extends AsyncTask<String, Integer, Bitmap> {

	public static LinkedList<AsynData3> asynData = new LinkedList<>();

	private static final String dir = md.pic_cacheDir + "/";

	private File file = null;

	final static String formatApk = FileOperation.FileType.type.apk.name();
	final static String formatImage = FileOperation.FileType.type.image.name();
	final static String formatVideo = FileOperation.FileType.type.video.name();

	String cacheName;
	String parentDir;

	private AsynData3 data;

	private Cursor cursor;

	private String fileName;

	public static Boolean isRunning = null;

	public static ArrayList<Integer> arr = new ArrayList<>();
	private static boolean stopExec = false;

	AsyncLoad(String parentDir, Cursor cursor) {
		this.parentDir = parentDir;
		this.cursor = cursor;
		stopExec = false;
	}

	public static void stop() {
		stopExec = true;
	}

	Bitmap getDefIcon(int res) {
		return BitmapFactory.decodeResource(md.context.getResources(), res);
	}

	Bitmap format_Image() {
		
		Bitmap bit = api.getImageThumbnail(parentDir + fileName, ViewShow.ThumbnailMode.picture,
				ViewShow.ThumbnailMode.picture);

		try {
			if (bit == null) {
				bit = this.getDefIcon(R.drawable.format_picture_broken);
			}

			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;

	}

	Bitmap format_Video() {
		Bitmap bit = api.getVideoThumbnail(parentDir + fileName, MediaStore.Images.Thumbnails.MINI_KIND);

		try {
			if (bit == null) {
				if (bit == null) {
					bit = this.getDefIcon(R.drawable.format_video);
				}
			}
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bit;

	}

	Bitmap format_Apk() {

		Bitmap bit = api.apkInfo(parentDir + fileName);
		try {

			if (bit == null) {
				bit = this.getDefIcon(R.drawable.format_apk);
			}
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 100, out);
			// out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bit;
	}

	private Bitmap startDoInBackground() {
		/*
		 * AsynData ad = new AsynData(fb.data.getString(fb.FieldIndex.name),
		 * whatKind, 0, fb.data.getString(fb.FieldIndex.modified).hashCode(),
		 * view_icon); AsyncLoad.asynData.addFirst(ad);
		 */

		String whatKind = cursor.getString(FieldIndex.kind);

		if (file.exists()) {
			return BitmapFactory.decodeFile(cacheName);
		}

		if (whatKind.equals(formatImage)) {
			return format_Image();
		}
		if (whatKind.equals(formatApk)) {
			return format_Apk();
		}
		if (whatKind.equals(formatVideo)) {
			return format_Video();
		}

		return null;
	}

	void setImage(final ImageView iv, final Bitmap bit) {
		if ((int) iv.getTag() != 0) {
			return;
		}
		iv.post(new Runnable() {

			@Override
			public void run() {
				if (bit != null) {
					iv.setImageBitmap(bit);
					iv.setTag(1);
				}

			}
		});

	}

	void setImageDrawable(final ImageView iv, final Drawable dr) {
		if ((int) iv.getTag() != 0) {
			return;
		}
		iv.post(new Runnable() {

			@Override
			public void run() {
				if (dr != null) {
					iv.setImageDrawable(dr);
					iv.setTag(1);
				}

			}
		});

	}

	@Override
	protected Bitmap doInBackground(String... params) {
		isRunning = false;
	
		while (!asynData.isEmpty()) {

			data = asynData.removeFirst();

			cursor.moveToFirst();
			cursor.moveToPosition(data.getId());

			fileName = cursor.getString(FieldIndex.name);
			
			
			String qq2=cursor.getString(FieldIndex.parent);
			if(qq2!=null){
				//ff.sc("			if(qq2!=null){");
				parentDir=qq2+"/";  	
			}
			

			cacheName = data.getCacheFile();

			// cacheName = dir +cursor.getString(FieldIndex.modified).hashCode()
			// + fileName+"..";

			file = new File(cacheName);
			if (file.exists()) {
				setImageDrawable(data.getImg(), Drawable.createFromPath(cacheName));
			} else {
				setImage(data.getImg(), startDoInBackground());
			}

			if (stopExec == true) {
				asynData.clear();
				stopExec = false;
			}

		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		stopExec = false;
		isRunning = null;

	}
}
