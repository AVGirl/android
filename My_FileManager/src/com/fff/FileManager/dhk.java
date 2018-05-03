package com.fff.FileManager;
import com.fff.misc.ff;
import com.fff.tools.api;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

public class dhk {

public static final	int Positive_BTN = -1;// -1 ok
public static final	int Negative_BTN = -2;// -2 no
public static final	int Neutral_BTN = -3;// -3 
private static EditText editText=new EditText(jj.mContext);
private static Handler han;
private static Looper looper;


public static CallbackMessage callbackMessage;
public static  AlertDialog.Builder builder;	    
public static  AlertDialog alertDialog;	
static OnClickListener123 btnClick;
private static boolean UserBuilder;
 public static Context myContext;

public static PopupWindow popWin;



public static void popWinClose(){
	if(popWin!=null){popWin.dismiss();api.setWinAlpha(1f);}
}
/*public static void popWinSetOnDismissListener(){
	popWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
		@Override
			public void onDismiss() {
			api.setWinAlpha(1f);	
			}
		});
}
*/
public static void popWin_setAlpha(){
	api.setWinAlpha(0.5f);
}
public static void popWinInit(){
	popWin=new PopupWindow();
	popWin.setBackgroundDrawable(md.context.getResources().getDrawable(R.drawable.transparent_pic));
	//popWin.setBackgroundDrawable(background);
	popWin.setOutsideTouchable(true);
	popWin.setTouchable(true);
	popWin.setFocusable(true);
	popWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
		
		@Override
		public void onDismiss() {
			popWinClose();
		}
	});
	
	
}

  
static public class builderSettings{
public  static boolean openInputMethod=false;
}
//____________________________________________________________________
public static void setUserBuilder(boolean b){	
	initBuiler(0);
	UserBuilder=b;
}

/**
 * 调用前用户必须 调用 setUserBuilder
 * @return
 */
public static void showAlertDialog(){	
	initBuiler(0);
	//builder.setTitle(bt).setPositiveButton(btnOk	,btnClick ).setItems(strs, btnClick);
	initBuiler(1);
}

public static void setContext(Context c){
	myContext=c;
}
static private void initBuiler(int w){

	  if(w==0){		  
		  if(UserBuilder==true){
		  
			  UserBuilder=false;
			  return;
		  }
			

		  editText=new EditText(jj.mContext);
		  btnClick=new OnClickListener123();	
		 callbackMessage=new CallbackMessage();
		 alertDialog=null;
		 builder=null;
	
		if(myContext!=null){
			builder =new AlertDialog.Builder(myContext);
		}else{
			builder =new AlertDialog.Builder(jj.mContext);
		}
		builder.setOnDismissListener(btnClick).setIcon(R.drawable.ic_launcher);

		return;
	}
	  
	  
	

	DialogThread dt=new DialogThread();
	if(builderSettings.openInputMethod==true){
		builderSettings.openInputMethod=false;
		dt.setInputbox();	
	}
	
	dt.start();

	try{dt.join();}catch(Exception t){t.printStackTrace();}
}
	
public static int  singleChoiceDialog(String[]  items,int checkedItem,String bt,String btnOk){	
		initBuiler(0);
		builder.setTitle(bt).setPositiveButton(btnOk	,btnClick ).setSingleChoiceItems(items, checkedItem, btnClick);
		initBuiler(1);
		return callbackMessage.which;
}
	
	
/**
 * 
 * @param strs
 * @param bt
 * @param btnOk
 * @return 0==which  1==btnId
 */
public static int[]  listDialog(String[] strs,String bt,String btnOk){
	
	initBuiler(0);

	builder.setTitle(bt).setPositiveButton(btnOk	,btnClick ).setItems(strs, btnClick);

	initBuiler(1);

	return new int[]{callbackMessage.which,callbackMessage.btnId};
		
}	
	//_____________________________________________comfirmDialog
public static <T>  int dhk( T msg) {
	initBuiler(0);
	builder.setMessage(String.valueOf(msg));
	initBuiler(1);
	return callbackMessage.btnId;
}
public static int dhk( String msg, String bt, String btnOk, String btnNo) {
	if(btnOk==null){btnOk="Ok";}
	if(btnNo==null){btnNo="No";}
	
	initBuiler(0);
	builder.setTitle(bt).setPositiveButton(btnOk	,btnClick ).setNegativeButton(btnNo, btnClick).setMessage(msg);
	initBuiler(1);
	return callbackMessage.btnId;
}
//_____________________________________________inputDialog
/**
 * 
 * <big> result: null or text
 */
public static String inputDialog(String text,Integer select,String bt, String btnOk, String btnNo){
	initBuiler(0);	
	EditText et=editText;
	et.setId(0x101010);
	et.setText(text);
	
	if(select==null){
		select=-1;}	
	
		if (select > text.length() || select==-1 ) {
			select = text.length();
		}   
		if (select <= text.length() && select != -1) {
			et.setSelection(select);
		}

		if(btnOk==null){btnOk="OK";}
		if(btnNo==null){btnNo="No";}
	


	builder.setTitle(bt).setPositiveButton(btnOk,btnClick ).setNegativeButton(btnNo, btnClick).setView(et);;
	
	builderSettings.openInputMethod=true;
	initBuiler(1);
	if(callbackMessage.which!=dhk.Positive_BTN){return null;}else{return callbackMessage.inputResult;}
}


//______________________________________________________________________________

static class  DialogThread extends Thread{
	boolean inputbox;


	void setInputbox(){
		inputbox=true;
	}

	@Override
	public void run() {
		Looper.prepare();
		looper=Looper.myLooper();		
		
		han=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==444){
					alertDialog.dismiss();
					looper.quit();
				}
				if(msg.what==1001){
					requestFocus();
				}
										
			}};			
		alertDialog=	builder.show();		
				
		
		
		if(inputbox==true){
			han.sendEmptyMessageDelayed(1001, 100);
		}	
		
		
		Looper.loop();

	}
	
	


}


static class CallbackMessage {
	int btnId;
	// null or text
	String inputResult;
	// form setItems
	int which;

}

//_________________________________________________________________________________________
static class OnClickListener123 implements OnClickListener, OnDismissListener {
	@Override
	public void onClick(DialogInterface dialog, int which) {
		callbackMessage.btnId=which;
		callbackMessage.which=which;
		callbackMessage.inputResult=editText.getText().toString();
		 han.sendEmptyMessage(444);
		}
	@Override
	public void onDismiss(DialogInterface dialog) {
		callbackMessage.btnId=0;
		callbackMessage.which=-1;
		 han.sendEmptyMessage(444);
	}
		
}
//______________________________________________________________________________
//______________________________________________________________
public static void requestFocus() {	
   InputMethodManager inputManager = (InputMethodManager)  jj.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
   inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);				     
}

}	
//____________________________________

