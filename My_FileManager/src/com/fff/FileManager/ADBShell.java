package com.fff.FileManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fff.misc.ff;
import com.fff.tools.Linux;
import com.fff.tools.api;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class ADBShell  extends Activity{
	ByteArrayOutputStream bo=new ByteArrayOutputStream();
	ByteArrayOutputStream bo2=new ByteArrayOutputStream();
	private EditText result;
	private EditText err;
	private EditText inputTextView;
	//private Process pro;
//	private DataOutputStream outStream; 
	boolean isok=false;
	//private DataInputStream inStream;
	//private DataInputStream errStream;
	String history=api.getExternalCacheDir()+"/history.txt";

  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.adbshell); 
		 dhk.setContext(this);
		inputTextView=(EditText) findViewById(R.id.adbshell_editeText);
		inputTextView.setOnKeyListener(new key1());
			
		//exception:failed:epipe 
		
		
		
		result=(EditText) findViewById(R.id.adbshell_result);
		err=(EditText) findViewById(R.id.adbshell_err);
	
		
		
		
	}

	void adbshell_exec(){
		
		api.closeInput(inputTextView);


	
		String inputText=inputTextView.getText().toString();

		addtohistory(inputText);
		
		
		 result.setText("");
		 err.setText("");
		 
		 String rr=su.exec(inputText);
		 if(rr==null){
			 err.setText("发生出错");
			 rr="";
		 }
		 result.setText(rr);
			 
			
		
		  
	}
	
	public void onClick(View v){
		
		switch (v.getId()) {
		case R.id.adbshell_exec:
			adbshell_exec();
			break;
		case R.id.adbshell_clear:
			inputTextView.setText("");
			result.setText("");
			inputTextView.requestFocus();
			api.openInput(inputTextView);
			break;
		case R.id.adbshell_history:
			adbshell_history();
			break;	
		case R.id.adbshell_test:
			adbshell_test(null);
			break;	
		case R.id.adbshell_test123:
			adbshell_test(inputTextView.getText().toString());
			break;	
			
			
			
			
	}}
	   void adbshell_test(String curr) {
		  if(curr==null){curr="/data/data";}   
		   
			File rr[]=new File(curr).listFiles();
			ff.sc(ff.getFormatTypeText(rr)); 
			//ff.log(rr);  
			ff.sc(new File("/").listRoots());	
		
	}

	void  addtohistory(String what){
		
		String h=api.readTextFile(history);
		if(api.isNull(h)){h="";}
		String wjs[]=h.split("\\n");
		StringBuilder sb=new StringBuilder();
		sb.append(what+"\n");
		for(int i=0; i< wjs.length ;i++){
			if(wjs[i].equals(what) || wjs[i].equals("")){
				continue;
			}
			sb.append(wjs[i]+"\n");
			
		}
		api.writeTextFile(history, sb.toString(), false);
	}
	
	
	private void adbshell_history() {
		
		String wjm=api.getExternalCacheDir()+"/history.txt";
	
		String t=api.readTextFile(wjm);
		if(t==null){return;}
		//  t=api.deleteCRLFOnce(t);
		String wjs[]=t.split("\\n");
		
			  if (wjs==null) {
				return;
			}
		
			
			  int j[]=dhk.listDialog(wjs, null, "清空");
			  ff.sc(j); 
			  if((j[0])>=0){
				   inputTextView.setText(wjs[j[0]]);
				   inputTextView.setSelection(inputTextView.getText().length());
				   this.adbshell_exec();
			   }
			  if(j[1]==dhk.Positive_BTN){
				  new File(wjm).delete();
			  }
		
		
		  
		  
		
		
	}

	private void adbshell_clear() {
	}
	
	class  key1 implements View.OnKeyListener{

		@Override
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
			if(arg1==66){			
				adbshell_exec();
				return true;			
			}
			return false;
		}
		
	}

	
	
}








