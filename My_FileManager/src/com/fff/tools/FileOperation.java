package com.fff.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fff.FileManager.FieldIndex;
import com.fff.FileManager.FileBrowser;
import com.fff.FileManager.jj;
import com.fff.FileManager.R;
import com.fff.FileManager.dhk;
import com.fff.FileManager.md;
import com.fff.FileManager.dhk.builderSettings;
import com.fff.misc.ff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FileOperation {
	public static DecimalFormat decimalFormat = new DecimalFormat("#.00");
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static String[] clipboard;
	
	//static String clipboard_fileDir;
      
	
	/**
	 * false==copy true==cut
	 */
	static boolean clipboard_type;
	static Linux shell = new Linux(Runtime.getRuntime());

	
	public static String[] getChoiceFilePath(final FileBrowser src) {
		final int[] count = src.getCheckedItemDatas();

		if (count.length == 0) {
			return null;
		}

		
		String name[] = new String[count.length];
		for (int i = 0; i < count.length; i++) {
			name[i] =src.dbGetString(count[i],FieldIndex.parent)+"/"+ src.dbGetString(count[i],FieldIndex.name);
		}
		return name;
	}
	
	
	
	
	
	public static String[] getChoiceFilename(final FileBrowser src) {
		final int[] count = src.getCheckedItemDatas();

		if (count.length == 0) {
			return null;
		}

		
		String name[] = new String[count.length];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count.length; i++) {
			name[i] = src.dbGetString(count[i],src.data.getColumnIndex("name"));
			
			sb.append(name[i] + "\n");
		}
		return name;
	}

	public static void createFile(String w, final FileBrowser father) {

	}

	public static void createFileDialog(final FileBrowser father) {
		View v=md.activity.getLayoutInflater().inflate(R.layout.dialog_create_file, null);
		final EditText et=(EditText) v.findViewById(R.id.editText);
		
		class Click implements View.OnClickListener{
			@Override
			public void onClick(View v) {
				dhk.alertDialog.dismiss();
				String jg=father.parentPath + et.getText().toString();
				
				switch(v.getId()){
				case R.id.button_a:
	
					new File(jg).mkdir();
				
					break;
				case R.id.button_b:
					try {
						new File(jg).createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				 
				}
				jj.gridview.postDelayed(new Runnable() {
					
					@Override
					public void run() {		
						father.Refresh();
					}
				}, 200);
				
			
				
				
			}
			
		}
		Click click=new Click();
		//String items[] = new String[] { "文件", "文本", "文件夹" };
		dhk.setUserBuilder(true);
	
		dhk.builder.setView(v);
		v.findViewById(R.id.button_x).setOnClickListener(click);;
		v.findViewById(R.id.button_a).setOnClickListener(click);;
		 v.findViewById(R.id.button_b).setOnClickListener(click);;
			
		dhk.builderSettings.openInputMethod=true;	
		dhk.showAlertDialog();

		    
/*
		int which[] = dhk.listDialog(items, "新建", null);
		if (which[0] == -1) {
			ff.log(which, "which");
			return;
		}

		String w = null;
		String t1 = null;
		switch (which[0]) {
		case 0:
			w = "文件";
			t1 = "新文件";
			break;
		case 1:
			w = "文本";
			t1 = "新文本.txt";
			break;
		case 2:
			w = "文件夹";
			t1 = "文件夹";
			break;
		}
		ff.log(t1, w);
		// private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
		String jg = dhk.inputDialog(t1, 100, "新建  " + w, null, null);
		if (jg == null) {
			return;
		}*/

/*		if (w.equals("文件夹")) {
			//if (new File().mkdir() == true) {
			String a=Linux.createDirectory(father.parentPath + jg);
			ff.toast(MainActivity.mContext, a);
					
			
		} else {	
				String a=Linux.createFile(father.parentPath + jg);
				ff.toast(MainActivity.mContext, a);		
		}*/

		// createFile(w,father);
	}

	public static void paste(final FileBrowser src) {

		if (clipboard == null) {
			ff.toast(jj.mContext, "  if (clipboard == null) ");
			return;
		}

		// ________________________________onclick555
		class OnClickListener555 implements OnClickListener {
			Thread t;
			Handler han;

			OnClickListener555(Thread t, Handler han) {
				this.t = t;
				this.han = han;
			}

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// int a= dhk.dhk("ȷ�ֹ", null, null);
				int a = dhk.dhk("确定放弃？", "终止", null, null);
				if (a != -1) {
					han.sendEmptyMessageDelayed(888, 100);
					return;
				}
				// han.sendEmptyMessageDelayed(888, 300);
				try {
					this.t.stop();
				} catch (Exception t) {
					t.printStackTrace();
				}

			}
		}
		// _____________________________Thread____________________________
		class Thread555 extends Thread {
			Handler hh;

			Thread555(Handler hh) {
				this.hh = hh;
			};

			@Override
			public void run() {

				for (int i = 0; i < clipboard.length; i++) {
					try {
						Message m = Message.obtain();
						m.arg1 = i;
						m.obj = "[" + i + "/" + clipboard.length + "]" + clipboard[i];
						hh.sendMessage(m);
						Process p;
						if (clipboard_type == true) {
							p = shell.moveFile(clipboard[i], src.parentPath + getwjm( clipboard[i]));

						} else {
							p = shell.copyFile(clipboard[i], src.parentPath +getwjm( clipboard[i]));

						}

						try {
							p.waitFor();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				hh.sendEmptyMessage(666);
			}

		}
		// ______________________________________________handler555
		class Handler555 extends Handler {
			ProgressDialog pro;

			Handler555(ProgressDialog pro) {
				this.pro = pro;
			}

			@Override
			public void handleMessage(Message msg) {

				if (msg.what == 666) {
					src.Refresh();
					pro.dismiss();
					return;
				}
				if (msg.what == 888) {
					pro.show();
					return;
				}

				pro.setMessage((String) msg.obj);
				pro.setProgress(msg.arg1);
				// ff.log((String)msg.obj);
			}
		}
		// ________________________________________________________________
		ProgressDialog pro = new ProgressDialog(jj.mContext);
		Handler555 handler555 = new Handler555(pro);
		Thread555 thread555 = new Thread555(handler555);

		pro.setButton("终止ֹ", new OnClickListener555(thread555, handler555));
		pro.setMax(clipboard.length);
		if (clipboard_type == true) {
			pro.setTitle("删除中...");
		} else {
			pro.setTitle("准备中...");
		}

		pro.setCancelable(false);
		pro.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pro.setIndeterminate(false);
		pro.setMessage("...");
		pro.show();
		// ____________________________________________________________________
		thread555.start();

	}

	public static void cutFile(final FileBrowser src) {
		clipboard = getChoiceFilePath(src);
		if (clipboard == null) {
			return;
		}
		clipboard_type = true;
		ff.toast(jj.mContext, "  剪切 (" + clipboard.length + ") ");
	}

	public static void copy(final FileBrowser src) {
		clipboard = getChoiceFilePath(src);
		if (clipboard == null) {
			return;
		}
		clipboard_type = false;
		//clipboard_fileDir = father.parentPath;
		ff.toast(jj.mContext, "  复制 (" + clipboard.length + ") ");
	}

	// ___________________________________________________________________
	public static void rename(final FileBrowser src) {
		String name[] = getChoiceFilePath(src);
		if (name == null) {
			return;
		}

	//	String parentDir = father.parentPath;
		String titile = "rename";
		;
		String olderName = new File(name[0]).getName();
		;
		if (name.length >= 2) {
			titile = "多文件";
		}

		int sel = olderName.length() - getFileType(olderName).length() - 1;

		String newName = dhk.inputDialog(olderName, sel, titile, null, null);
		// ff.log("---------aaaaa");
		if (newName == null) {
			return;
		}

		String format = FileOperation.getFileType(newName);

		for (int i = 0; i < name.length; i++) {

			if (i == 0) {
				boolean bb = new File(name[i]).renameTo(new File(new File(name[i]).getParent()+"/" + newName));

				continue;
			}
			new File( name[i]).renameTo(new File(new File(name[i]).getParent()+"/"  + "(" + i + ")" + newName));
		}

		src.disabledMulti();
		src.Refresh();

	}

	// _____________________________________________delete
	public static void delete(final FileBrowser src) {
		final String[] files = getChoiceFilePath(src);
		if (files == null) {
			return;
		}

		int a = dhk.dhk(ff.StringArrToString(getChoiceFilename(src), null), "删除[" + files.length + "]", null, null);
		if (a != -1) {
			return;
		}
		// ________________________________onclick555
		class OnClickListener555 implements OnClickListener {
			Thread t;
			Handler han;

			OnClickListener555(Thread t, Handler han) {
				this.t = t;
				this.han = han;
			}

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int a = dhk.dhk("0x123", "0x1234ֹ", null, null);
				if (a != -1) {
					han.sendEmptyMessageDelayed(888, 100);
					return;
				}
				// han.sendEmptyMessageDelayed(888, 300); ��ȶԻ��� �� ������
				try {
					this.t.stop();
				} catch (Exception t) {
					t.printStackTrace();
				}

			}
		}
		// _____________________________Thread____________________________
		class Thread555 extends Thread {
			Handler hh;

			Thread555(Handler hh) {
				this.hh = hh;
			};

			@Override
			public void run() {
				for (int i = 0; i < files.length; i++) {
					new File(files[i]).delete();
				/*	try {
						Message m = Message.obtain();
						m.arg1 = i;
						m.obj = "[" + i + "/" + files.length + "]" + files[i];
						hh.sendMessage(m);
						Process p = shell.delete(files[i]);
						if(p==null){
							continue;
						}
						try {
							p.waitFor();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					} catch (IOException e) {
						e.printStackTrace();
					}*/
				}

				hh.sendEmptyMessage(666);
			}

		}
		// ______________________________________________handler555
		class Handler555 extends Handler {
			ProgressDialog pro;

			Handler555(ProgressDialog pro) {
				this.pro = pro;
			}

			@Override
			public void handleMessage(Message msg) {

				if (msg.what == 666) {
					pro.dismiss();
					src.Refresh();
					return;
				}
				if (msg.what == 888) {
					pro.show();
					return;
				}

				pro.setMessage((String) msg.obj);
				pro.setProgress(msg.arg1);
				// ff.log((String)msg.obj);
			}
		}

		// ________________________________________________________________
		ProgressDialog pro = new ProgressDialog(jj.mContext);
		Handler555 handler555 = new Handler555(pro);
		Thread555 thread555 = new Thread555(handler555);

		pro.setButton("0x78", new OnClickListener555(thread555, handler555));
		pro.setMax(files.length);
		pro.setTitle("0x99...");
		pro.setCancelable(false);
		pro.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pro.setIndeterminate(false);
		pro.setMessage("0xrr44...");
		pro.show();
		// ____________________________________________________________________
		thread555.start();

	}

	// ******************* getFileModefied *********
	public static String getFileModefied(long modified) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(modified);
		return simpleDateFormat.format(cal.getTime());
	}

	// *************** getFileSize
	public static String getFileSize(long length) {
		String size = "";
		long fileS = length;
		if (fileS < 1024) {
			size = decimalFormat.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			size = decimalFormat.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			size = decimalFormat.format((double) fileS / 1048576) + "MB";
		} else {
			size = decimalFormat.format((double) fileS / 1073741824) + "GB";
		}

		return size;
	}

	// ---------------------
	public static class FileType {
		public static enum type {
			audio, video, image, document, txt, zip, apk, other
		}

		public static final String audio = "-mp3-aac-wav-ogg-wma-flac-ape-";
		public static final String video = "-avi-wma-rmvb-rm-flv-mp4-3gp-mkv-mov-";
		public static final String image = "-bmp-jpg-png-gif-psd-";
		public static final String document = "-doc-pdf-chm-xls-ppt-wps-";
		public static final String txt = "-xml-txt-lrc-html-conf-php-htm-cfg-ini-css-js-java-";
		public static final String zip = "-zip-rar-7z-iso-jar-cab-tar-";
		public static final String apk = "-apk-";
		public static final String other = "-db-";

		static type findFileType(String format) {
			format = "-" + format + "-";
			if (audio.indexOf(format) != -1) {
				return type.audio;
			}
			if (video.indexOf(format) != -1) {
				return type.video;
			}
			if (image.indexOf(format) != -1) {
				return type.image;
			}
			if (document.indexOf(format) != -1) {
				return type.document;
			}
			if (txt.indexOf(format) != -1) {
				return type.txt;
			}
			if (zip.indexOf(format) != -1) {
				return type.zip;
			}
			if (apk.indexOf(format) != -1) {
				return type.apk;
			}
			
			return type.other;
		}
	}

	public static int getFileTypeIcon(String fileType, String returnName[]) {
		int resId = 0;
		fileType = fileType.toLowerCase();

		// ff.log("fileType:"+fileType);
		FileType.type type = FileType.findFileType(fileType);

		switch (type) {
		case apk:
			resId = R.drawable.format_apk;
			returnName[0] = "apk";
			//returnName[0] = "apk";
			
			break;
		case image:
			resId = R.drawable.format_image;
			returnName[0] = "image";
			break;
		case audio:
			resId = R.drawable.format_music;
			returnName[0] = "music";
			break;
		case video:
			resId = R.drawable.format_video;
			returnName[0] = "video";
			break;
		case document:
			resId = R.drawable.format_document;
			returnName[0] = "document";
			break;
		case txt:
			resId = R.drawable.format_txt;
			returnName[0] = "txt";
			break;

		case zip:
			resId = R.drawable.format_zip;
			returnName[0] = "zip";
			break;
		case other:
			resId = R.drawable.toolbar_cut;
			returnName[0] = "other";
			break;
			
		default:
			resId = R.drawable.format_unkown;
			returnName[0] = "file";
			break;
		}
		return resId;
	}

	// ------------------- g
	public static String getwjm(String file) {
		return new File(file).getName();
	}

	// ****************** getFileType ****************************

	/**
	 * mp3,mkv....
	 * 
	 */
	public static String getFileType2(String name) {
		int end2 = name.lastIndexOf(".");
		if (end2 == -1) {
			return "";
		}
		return name.substring(end2 + 1, name.length());
	}
	public static String getFileType(String path) {
		int end = path.lastIndexOf("/");
		
		if (end == -1) {
			end = path.lastIndexOf(".");
		}
		
	if(end==-1){
		return "";
	}
		
	
	
		String a=path.substring(end, path.length());
		
		
		int ok=a.lastIndexOf(".");
		if(ok==-1)return "";
	
		return a.substring(ok+1, a.length());
	}

	// ------************** getMIMEType --
	public static String getMIMEType(String FileSuffix) {
		String str[][] = com.fff.misc.MIMEType.MIME_MapTable;
		String a = FileSuffix.toLowerCase();
		for (int i = 0; i < str.length; i++) {
			ff.sc(str[i][0]);
			if (str[i][0].equals(a)) {
				return str[i][1];
			}

		}
		return "*/*";
	}

	// --------- getMIMEType --------------
	public static String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();

		// 
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}

	
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "") {
			return type;
		}
		String MIME_MapTable[][] = com.fff.misc.MIMEType.MIME_MapTable;


		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0])) {
				type = MIME_MapTable[i][1];
			}
		}

		return type;
	}

	// ---------------------------------------------------------------------------------
	public static void openFile(String file_, Activity activity) {
		if (file_ == null) {
			return;
		}
		File file = new File(file_);

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.setAction(Intent.ACTION_VIEW);

		String type = FileOperation.getMIMEType(file);

		intent.setDataAndType(Uri.fromFile(file), type);

		try {
			activity.startActivity(intent);
		} /*
			 * catch(E) {
			 * 
			 * }
			 */catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
