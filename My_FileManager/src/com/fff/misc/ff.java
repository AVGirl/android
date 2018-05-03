package com.fff.misc;
import java.io.File;
import java.util.Arrays;

import com.fff.FileManager.jj;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/29.
 */




public  class  ff <TString> {
static	Context mContext;
	ff(){}
	
	ff(Context c){mContext=c;}
	
	static	  void  getContentView(Activity ac){  
		       ViewGroup view = (ViewGroup)ac.getWindow().getDecorView();
		       FrameLayout content= (FrameLayout) view.findViewById(android.R.id.content);
		       View v=content.getChildAt(0);
		      
		       Button btn=new Button(v.getContext());
		  
		       btn.setText("new view");
		         
		      LinearLayout ll=(LinearLayout) v;
		       ll.addView(btn);
		       btn.setX(1.0f);
		       btn.setY(1.0f);
		       btn.setHeight(100);
		       btn.setWidth(300);
		      // FrameLayout content = (FrameLayout)view.findViewById(android.R.id.content);  
		      // return content.getChildAt(0);  
		       ff.sc(v.getClass().getName());
		}
	
	
	static <T>void setTitle(Activity act,T  s){
	
	//if(act==null){act=mContext.getApplicationContext();}
	act.getActionBar().setTitle(String.valueOf(s));
	}
	

	
//-----
	 public static String StringArrToString(String[] strs,String separator){
		 if(strs==null){return "";}
		if(separator==null){separator="\n";}
		 StringBuilder sb=new StringBuilder();
		 for(int i=0; i< strs.length ;i++){
			sb.append(strs[i]+separator);
		}
		return sb.toString();
		 
	 }                       
	
  public static <T>void toast(Context c,T t){
        Toast.makeText(c,String.valueOf(t),Toast.LENGTH_SHORT).show();
    }
  
  public static <T>void toast(T t){
      Toast.makeText(jj.mContext,String.valueOf(t),Toast.LENGTH_SHORT).show();
  }
//--------------------

  public static <T>void  xxk(Context c,T string) {
	android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(c);
    b.setMessage(String.valueOf(string));
    b.setPositiveButton("OK", null);
    b.create().show();
}

 
 public static String  getsdpath() {
	// boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //�Ƿ����sd����������ҪSD��ȡȨ��

	 return  Environment.getExternalStorageDirectory().toString();
}

 

 
 public static void sc(String t){
	 Log.v("【app输出】", String.valueOf(t));	
	}
public static<T> void sc(T...t){
	 Log.v("【app输出】", getFormatTypeText(t));
	}
 
public static <T> String getFormatTypeText(T...t){
	 StringBuilder str=new StringBuilder();
	 
	 if(t==null){return "java.lang.NullPointerException.NullPointerException()";}
	 
	for(int i=0; i< t.length ;i++){
	
		if(i==0){str.append(formatType(t[i]));}
		if(i>=1){
			str.append("  ->  "+ formatType(t[i]));
		}
	} 
	return str.toString();
 }
 
 
 //----------------------------------------------------------

 
 
/*public static<T> void log(T t){
	Log.v("��app�����", String.valueOf(t));
	
	
}*/

public static void lognewLine(){
	Log.v("��app�����", "\n  ");
	
}



//-----------------------------------------------------------------------------------

public static <T> String formatType(T t) {

	if (t == null) {
		return "java.lang.NullPointerException";
	}
	
	
	String type=t.getClass().getSimpleName();
	boolean isArray=(type.lastIndexOf("[]")==-1)?false:true;
	if(isArray==false){return String.valueOf(t);};
	
	String msg = null;
	int length=0;

	switch(type){
	case "char[]":
		char temp[]=(char[])t;
		msg=Arrays.toString(temp);
		length=temp.length;
		break;
	case "int[]":
		int temp1[]=(int[])t;
		msg=Arrays.toString(temp1);
		length=temp1.length;
		break;
	case "byte[]":
		byte temp2[]=(byte[])t;
		msg=Arrays.toString(temp2);
		length=temp2.length;
		break;
	case "long[]":
		long temp3[]=(long[])t;
		msg=Arrays.toString(temp3);
		length=temp3.length;
		break;
	case "short[]":
		short temp4[]=(short[])t;
		msg=Arrays.toString(temp4);
		length=temp4.length;
		break;
	case "boolean[]":
		boolean temp5[]=(boolean[])t;
		msg=Arrays.toString(temp5);
		length=temp5.length;
		break;
	case "float[]":
		float temp6[]=(float[])t;
		msg=Arrays.toString(temp6);
		length=temp6.length;
		break;
	case "double[]":
		double temp7[]=(double[])t;
		msg=Arrays.toString(temp7);
		length=temp7.length;
		break;
		
		default :
		return	formatType2((T[]) t);
		//break;
	}
	if(isArray==true){
		type=type.substring(0, type.lastIndexOf("[]"));
		return type+"["+length+"]:"+msg;
	}
	return "";

}
//--------------------------------------------------------------
public static <T> String formatType(T[] t) {
	if (t == null) {
	return "java.lang.NullPointerException";
	}
	String type= t.getClass().getSimpleName();
	
	type=type.substring(0, type.lastIndexOf("[]"));
	Log.v("?????", type);
return type+"["+t.length+"]:"+Arrays.toString(t);
	
}
//---------------------------------------
//--------------------------------------------------------------
public static <T> String formatType2(T[] t) {
	if (t == null) {
	return "java.lang.NullPointerException";
	}
	String type= t.getClass().getSimpleName();
	
	type=type.substring(0, type.lastIndexOf("[]"));
	Log.v("?????", type);
return type+"["+t.length+"]:"+Arrays.toString(t);
	
}


}
