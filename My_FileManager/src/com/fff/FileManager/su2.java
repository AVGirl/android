package com.fff.FileManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fff.misc.ff;
import com.fff.tools.Linux;

public class su2 {
	public static final String Sendend = "echo \"->END<-\" \n";
	public static final String end = "->END<-";
	public static Process proc;
	public static DataOutputStream outStream;
	public static String errMsg;
	
	

	public static String getErr() {
		return errMsg;
	}
	
	
	public static String exec(String cmd) {        
		try {
			proc = Runtime.getRuntime().exec("su");
			outStream = new DataOutputStream(proc.getOutputStream());
			outStream.writeBytes(cmd+"\nexit\n");
			outStream.flush();
			proc.waitFor();
		BufferedReader buf=new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String ee,tt="";
		  while((ee=buf.readLine())!=null){
			  tt+=ee+"\n";
		  }
			outStream.close();
			proc.destroy();
      return tt;
		//	return r.getResult();
		} catch (Exception e) {
	
			e.printStackTrace();
			ff.sc("IOException:outStream.writeBytes(cmd+)");
			rebootSu();
		}

		return null;
	}

	static void rebootSu() {
		ff.sc("outStream:reboot....");
		proc.destroy();
		try {
			proc = Runtime.getRuntime().exec("su");
			outStream = new DataOutputStream(proc.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
static class retrieve extends Thread {
		BufferedReader read1;
		String result;
		boolean ok;
String msg ;
		void setmsg(String f){
			msg=f;
		}
		
		retrieve(InputStream in) {
			read1 = new BufferedReader(new InputStreamReader(in));
			ok=false;
		}

		String getResult() {
			while (!ok) {
			}
			return result;
		}

		@Override
		public void run() {
			String ee;
			StringBuilder sb = new StringBuilder();
		int	appendConut=0;

			try {
		//	dhk.dhk(1);
		//	proc.destroy();
				while ((ee = read1.readLine()) != null) {
					//dhk.dhk(2);
					if (ee.equals(end)) {
						result = sb.toString();
						if(appendConut==0){
							result=null;
						}
						
						ok = true;
						break;
					}
					
					
					appendConut++;
					sb.append(ee+"\n");
					dhk.dhk(ee);

				}
				
				//兼容 代码
				result = sb.toString();
				if(appendConut==0){
					result=null;
				}				
				ok = true;
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	
	//TODO 有问题
	static class retrieveErr extends Thread {
		BufferedReader read1;
		String result;
		boolean ok;
String msg ;
		void setmsg(String f){
			msg=f;
		}
		
		retrieveErr(InputStream in) {
			read1 = new BufferedReader(new InputStreamReader(in));
			ok=false;
		}

		String getResult() {		
			try {Thread.sleep(100);} catch (Exception e) {}		
			return result;
		}

		@Override
		public void run() {
			String ee;
			StringBuilder sb = new StringBuilder();

			try {
				
		
		//	ff.log(msg+ "  start");
				while ((ee = read1.readLine()) != null) {
				//	ff.log(ee,"eeeeeeeeeeee");
					/*if (ee.equals(end)) {
						result = sb.toString();
						ok = true;
						break;
					}*/
					sb.append(ee);
					result = sb.toString();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
