package com.fff.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.fff.FileManager.jj;
import com.fff.FileManager.md;
import com.fff.entity.ClassIntFiled;
import com.fff.misc.ff;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class api {
public static final class reflect{

/**
 * 返回字段值和字段名
 * @param cls
 * @param include
 * @param type
 * @return
 */
public static <T> HashMap getClsFileds2(Class cls, String include,Class type) {
	Field f1[] = cls.getDeclaredFields();
	int count=0;
	 T t[] = null;
	 
	if (include == null) {
			include = "";
		}     
	
	  for(int i=0; i<f1.length;i++){
		  	  if(f1[i].getName().indexOf(include)!=-1){
		  		  count++;
		  	  }	    
	  }
				if(type.equals(String.class)){
					t=(T[]) new String[count];
				}
				if(type.equals(Integer.class)){
					t=(T[]) new Integer[count];
				}     		
				if(type.equals(Boolean.class)){
					t=(T[]) new Boolean[count];
				}     
				if(type.equals(Long.class)){
					t=(T[]) new Long[count];		
				}     
				if(type.equals(Float.class)){
					t=(T[]) new Float[count];	
				}     			
		for (int i = 0; i < f1.length; i++) {	
			if (f1[i].getName().indexOf(include) == -1) {
				continue;
			}

			try {				
				t[i]= (T)f1[i].get(null);	
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}
  
	
public static <T> T[] getClsFileds(Class cls, String include,Class type) {
	Field f1[] = cls.getDeclaredFields();
	int count=0;
	 T t[] = null;
	 
	if (include == null) {
			include = "";
		}     
	
	  for(int i=0; i<f1.length;i++){
		  	  if(f1[i].getName().indexOf(include)!=-1){
		  		  count++;
		  	  }	    
	  }
				if(type.equals(String.class)){
					t=(T[]) new String[count];
				}
				if(type.equals(Integer.class)){
					t=(T[]) new Integer[count];
				}     		
				if(type.equals(Boolean.class)){
					t=(T[]) new Boolean[count];
				}     
				if(type.equals(Long.class)){
					t=(T[]) new Long[count];		
				}     
				if(type.equals(Float.class)){
					t=(T[]) new Float[count];	
				}     			
		for (int i = 0; i < f1.length; i++) {	
			if (f1[i].getName().indexOf(include) == -1) {
				continue;
			}

			try {				
				t[i]= (T)f1[i].get(null);	
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return t;
	}
  
	
	public static  ClassIntFiled getIntFiled(Class cls, Boolean all, String like, String notLike) {
		int count = 0;

		Field f1[] = cls.getDeclaredFields();
		count = f1.length;
		
		if (count == 0) {
			return null;
		}

		boolean jumpNotLike = false;
		if (notLike == null || notLike.equals("")) {
			jumpNotLike = true;
		}

		boolean jumpLike = false;
		if (like == null || like.equals("")) {
			jumpLike = true;
		}
		// ______________________________________________

		String name[] = new String[count];
		int[] value = new int[count];

		for (int i = 0; i < f1.length; i++) {
			name[i] = f1[i].getName();
			try {
				value[i] = f1[i].getInt(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (all != null && all == true) {
			return new ClassIntFiled(name, value);
		}

		if (jumpLike && jumpNotLike) {
			return new ClassIntFiled(name, value);
		}

		String[] names2 = new String[count];
		int[] vlues2 = new int[count];

		int j = 0;
		for (int i = 0; i < name.length; i++) {
			if (!jumpNotLike && name[i].indexOf(notLike) != -1) {
				continue;
			}
			if (!jumpLike && name[i].indexOf(like) == -1) {
				continue;
			}

			names2[j] = name[i];
			vlues2[j] = value[i];
			j++;
		}

		if (j == 0) {
			return null;
		}

		String[] name3 = new String[j];
		int[] values3 = new int[j];

		System.arraycopy(names2, 0, name3, 0, j);
		System.arraycopy(vlues2, 0, values3, 0, j);

		return new ClassIntFiled(name3, values3);
	}
}


	public static   String  deleteCRLFOnce(String input) {  	
		        if (input!=null) {  			
		            return input.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");  
		        } else {  
		            return null;  
		        }
 }
	
	
	public static   boolean  isNull(Object o) {  	
      return o==null;
}
	
	
	
	
	
public static	String getSDuseState(){
		File t5 = new File(md.sdcard);

		long total = t5.getTotalSpace();
		long usable = t5.getUsableSpace();

		DecimalFormat e1 = new DecimalFormat("#.00");
		int r4 = 1073741824;

		String tt = e1.format((double) (total - usable) / r4) + "G/" + e1.format((double) total / r4) + "G";
		;
		double dd = (double) (total - usable) / total * 100;
		return tt + "  " + Math.round(dd) + "%";
	}
	
	
	
	
	
	
	
	public static void writeTextFile(String file, String what, boolean append) {
String res = null;
if(append){
	res=readTextFile(file);
	if(res==null){res="";}
}
		
		try {
			FileOutputStream fo = new FileOutputStream(new File(file));
			if (append == true) {
				fo.write((res+ what).getBytes());
			} else {
				fo.write(what.getBytes());
			}
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readTextFile(String wj) {
		File file=new File(wj);
		if(!file.exists()){
			return null;
		}
		
		byte b[] = null;
		try {
			FileInputStream in = new FileInputStream(file);
			b = new byte[in.available()];
			if(b==null){return null;}
			in.read(b, 0, b.length);
			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new String(b);
	}

	public static void setClipText(String text) {
		ClipboardManager c;
		c = (ClipboardManager) jj.mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData myClip;
		myClip = ClipData.newPlainText("text", text);
		c.setPrimaryClip(myClip);
	}

	public static void closeInput(View et) {
		InputMethodManager inputManager = (InputMethodManager) jj.mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(et.getWindowToken(), 0);

	}

	public static void openInput() {
		InputMethodManager inputManager = (InputMethodManager) jj.mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, 0);

		// InputMethodManager.HIDE_NOT_ALWAYS
	}

	public static void openInput(View v) {
		InputMethodManager inputManager = (InputMethodManager) jj.mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, 0);
		inputManager.showSoftInput(v, 0);
		// InputMethodManager.HIDE_NOT_ALWAYS
	}

	public static String getExternalCacheDir() {
		return jj.mContext.getExternalCacheDir().getAbsolutePath();
	}

	public static Bitmap apkInfo(String absPath) {

		PackageManager pm = jj.mContext.getPackageManager();
		PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
		if (pkgInfo != null) {
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			/* ����������䣬��Ȼ����icon��ȡ��default icon����Ӧ�ð��icon */
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			Drawable icon2 = appInfo.loadIcon(pm);
			return drawableToBitmap(icon2);
		}
		return null;

	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("DrawableתBitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);

		return bitmap;
	}
	
	public static Bitmap bitmapTodrawable(Bitmap bitmap) {

		Drawable dr=new BitmapDrawable(bitmap);
		
/*		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("DrawableתBitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
*/
		return bitmap;
	}

	public static void setWinAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = jj.mActivity.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		// lp.screenBrightness
		// lp.screenOrientation
		jj.mActivity.getWindow().setAttributes(lp);
	}

	public static int getActHeight() {
		return jj.mActivity.getWindow().getDecorView().getHeight();

	}

	public static int getActWidth() {
		return jj.mActivity.getWindow().getDecorView().getWidth();

	}

	// _______________________________________
	/**
	 * ���ָ����ͼ��·���ʹ�С����ȡ����ͼ �˷���������ô��� 1.
	 * ʹ�ý�С���ڴ�ռ䣬��һ�λ�ȡ��bitmapʵ����Ϊnull��ֻ��Ϊ�˶�ȡ��Ⱥ͸߶ȣ�
	 * �ڶ��ζ�ȡ��bitmap�Ǹ�ݱ���ѹ�����ͼ�񣬵���ζ�ȡ��bitmap����Ҫ������ͼ�� 2.
	 * ����ͼ����ԭͼ������û�����죬����ʹ����2.2�汾���¹���ThumbnailUtils��ʹ
	 * �����������ɵ�ͼ�񲻻ᱻ���졣
	 * 
	 * @param imagePath
	 *            ͼ���·��
	 * @param width
	 *            ָ�����ͼ��Ŀ��
	 * @param height
	 *            ָ�����ͼ��ĸ߶�
	 * @return ��ɵ�����ͼ
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		
		if(new File(imagePath).length()<1024*100){
			return BitmapFactory.decodeFile(imagePath);
		}
		
		
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// ��ȡ���ͼƬ�Ŀ�͸ߣ�ע��˴���bitmapΪnull
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // ��Ϊ false
		// �������ű�
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// ���¶���ͼƬ����ȡ���ź��bitmap��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ
		// false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
	//	ff.log(bitmap.)
		// ����ThumbnailUtils����������ͼ������Ҫָ��Ҫ�����ĸ�Bitmap����
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * ��ȡ��Ƶ������ͼ
	 * ��ͨ��ThumbnailUtils������һ����Ƶ������ͼ��Ȼ��������ThumbnailUtils�����ָ����
	 * С������ͼ��
	 * �����Ҫ������ͼ�Ŀ�͸߶�С��MICRO_KIND��������Ҫʹ��MICRO_KIND��Ϊkind��ֵ��������ʡ
	 * �ڴ档
	 * 
	 * @param videoPath
	 *            ��Ƶ��·��
	 * @param width
	 *            ָ�������Ƶ����ͼ�Ŀ��
	 * @param height
	 *            ָ�������Ƶ����ͼ�ĸ߶ȶ�
	 * @param kind
	 *            ����MediaStore.Images.Thumbnails���еĳ���MINI_KIND��MICRO_KIND�
	 *            � ���У�MINI_KIND: 512 x 384��MICRO_KIND: 96 x 96
	 * @return ָ����С����Ƶ����ͼ
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
		Bitmap bitmap = null;
		// ��ȡ��Ƶ������ͼ
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w" + bitmap.getWidth());
		System.out.println("h" + bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	public static Bitmap getVideoThumbnail(String videoPath, int kind) {
		return ThumbnailUtils.createVideoThumbnail(videoPath, kind);
	}

	
	
	
	
	
	public static void  unZip(InputStream in,String path){
		File a=new File(path);
		if(!a.exists()){
			a.mkdirs();
		}
		if(!path.endsWith("/")){
			path+="/";
		}
		
		ZipInputStream zip=null;

			zip = new ZipInputStream(in);
	
		ZipEntry ent;
		
		
		try {
			while((ent=zip.getNextEntry())!=null){
				
				String destFile=path+ent.getName();
				new File(destFile).getParentFile().mkdirs();
				
			
				FileOutputStream out =new FileOutputStream(new File(destFile));
				BufferedOutputStream buf=new BufferedOutputStream(out,4096);
				 
				byte[] b=new byte[4096];
				int i=-1;
				while((i=zip.read(b, 0, 4096))!=-1){
						//out.write(b, 0, i); 
						buf.write(b, 0, i);
					
				}
				buf.close();
				out.close();
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
