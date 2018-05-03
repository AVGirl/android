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

public class ADBShell2  extends Activity{
	ByteArrayOutputStream bo=new ByteArrayOutputStream();
	ByteArrayOutputStream bo2=new ByteArrayOutputStream();
	private EditText result;
	private EditText err;
	private EditText inputTextView;
	private Process pro;
	private DataOutputStream outStream;
	boolean isok=false;
	private DataInputStream inStream;
	private DataInputStream errStream;
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
		try {
			 pro=Runtime.getRuntime().exec("su");
			// pro.waitFor();
			   outStream=new DataOutputStream(pro.getOutputStream());
			   inStream=new DataInputStream(pro.getInputStream());
			   errStream=new DataInputStream(pro.getErrorStream());	   
		} catch (Exception e) {
			ff.sc(e.getMessage());
			e.printStackTrace();  
		}	
	
		
		
		
	}

	void adbshell_exec(){
		
		api.closeInput(inputTextView);
		bo=new ByteArrayOutputStream();
		bo2=new ByteArrayOutputStream();
		
	 inStream=new DataInputStream(pro.getInputStream());
	 errStream=new DataInputStream(pro.getErrorStream());
		
		
/*	try{  
		 pro=Runtime.getRuntime().exec("su");
		   outStream=new DataOutputStream(pro.getOutputStream());
		   inStream=new DataInputStream(pro.getInputStream());
		   errStream=new DataInputStream(pro.getErrorStream());	   
	}catch(Exception t){t.printStackTrace();
	} */

	 String ii="mount -o remount /data";
	ii="find /sdcard/ -name *.mp3";
	ii=" cd /sdcard/ \n ls > /sdcard/456.txt\n";
	ii=" cd /sdcard/ \n ls";
	
		String inputText=inputTextView.getText().toString();
		//inputText=ii;
		addtohistory(inputText);
		
		
		 result.setText("");
		 err.setText("");
		 try { 
		 
			outStream.writeBytes(inputText+"\n");
			//outStream.writeBytes("exit\n");
			//pro.waitFor();
		//	outStream.writeUTF(inputText+"\n");
		
			//LinuxFileCommand.shell=Runtime.getRuntime();
		// pro=	LinuxFileCommand.ls_lhd("/sdcard/");
		// inStream=new DataInputStream(pro.getInputStream());
			
			 String jg="",tt=null;
			new Thread(new Runnable() {

				byte bb[]=new byte[1024];
				 int i ;
				@Override
				public void run() {
					try {
						
						  while(true){						  
							 i= inStream.read(bb);			 
								bo.write(bb,0,i);							
					  }
							
			
					} catch (IOException e) {
						ff.sc("} catch (IOException e) {");
						e.printStackTrace();
					}
				}
			}).start();
			
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
				int length=bo.size();
			
				while(true){
				try {Thread.sleep(10);} catch (Exception e) {}
					if(length==bo.size()){
						 length=bo.size();
						break;
					}
				 length=bo.size();
				 
				}
				
				result.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						result.setText(bo.toString());
					}
				}, 1);
			
				//try {inStream.close();} catch (Exception e) {}
				
					//ff.log(bo.toString());
				///	ff.log("okokokokokok");
				}
			}).start();
			
			//______________________________________________________________________________________________
		
					new Thread(new Runnable() {

						byte bb[]=new byte[1024];
						 int i ;
						@Override
						public void run() {
							try {
								
								  while(true){						  
									 i= errStream.read(bb);			 
										bo2.write(bb,0,i);							
							  }
									
					
							} catch (IOException e) {
								ff.sc("} catch (IOException e) {");
								e.printStackTrace();
							}
						}
					}).start();
					new Thread(new Runnable() {
						
						@Override
						public void run() {
						int length=bo2.size();
					
						while(true){
						try {Thread.sleep(10);} catch (Exception e) {}
							if(length==bo2.size()){
								break;
							}
						 length=bo2.size();
						 
						}
						
						err.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								err.setText(bo2.toString());
							}
						}, 1);
						}
					}).start();
			//if(1>0){return;};
		
			
			//bo.close();
			// result.setText(bo.toString());
			//ff.toast(bo.toString());
			 
			 if(1>0){return;};
			
			 
			//inStream.read(bb, 0, bb.length);
			//ff.toast(1);
		//	ff.toast(new String(bb));
		
			 
		
			
			 
			  jg="";tt=null;
			 while((tt=errStream.readLine())!=null){
				 jg+=tt+"\n";
			 }
			 err.setText(jg);
			 
			 
		} catch (Exception e) { 
			ff.toast("Exception:"+e.getMessage());
			e.printStackTrace();
		}
		
		  
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








