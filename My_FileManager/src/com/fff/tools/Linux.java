package com.fff.tools;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fff.FileManager.jj;
import com.fff.FileManager.md;
import com.fff.FileManager.su;
import com.fff.FileManager.su2;
import com.fff.misc.ff;



/**
 * File operators. Like: delete, create, move...
 * Use linux shell to implement these operators.
 * So, only for linux like operator system. 
 * All return the execute process, and if the requested program can not be executed,
 * may throws IOException.
 * And all files or diretorys String parameters are absolute path.
 * The return new {@code Process} object that represents the native process.
 * */
public  class Linux {
	public static   Runtime shell;
	public Linux(Runtime runtime) {
		shell=runtime;
		// TODO 自动生成的构造函数存根
	}
	
	
public static String execLine(String command){
	// Process r = null;
/*	 byte[] f=null;
	try {//command+"\n"\nexit
		 ff.log(123);
		DataOutputStream dff=new DataOutputStream(MainActivity.su.getOutputStream());
		ff.log(543);
		dff.writeBytes(command+"\n");
	//	r=Runtime.getRuntime().exec(command+"\n\nexit");
		ff.log(0x12d3);

		 InputStream in=MainActivity.su.getInputStream();
			ff.log(5454);
		 
		 f=new byte[in.available()];
			ff.log(111);
		 in.read(f, 0, f.length);
		 
		 DataInputStream in2=new DataInputStream(MainActivity.su.getErrorStream());
			ff.log(54544444);
		 ff.log(in2.readUTF());
		 byte[] f2=new byte[in2.available()];
		 in.read(f2, 0, f2.length);
			ff.log(96);
		 if(f2.length!=0){
			 ff.log( "getErrorStream:"+new String(f2,"utf-8"));	   
		 }
		 
			//r.destroy();
		
	} catch (Exception e) {
		 ff.log( "Exception -> Runtime.exec");	 
		// TODO Auto-generated catch block
		e.printStackTrace();   
	}  
	if(f!=null){
		return new String(f);
	}else{
		return "";	
	}
	
	 
	*/
	return "";	
}

	//private static final int c = 0;
	/**
	 * Constructor. /data/busybox/
	 * @param
	 * 		interface with the environment in which they are running
	 * */
	public static void   LinuxFileCommand(Runtime runtime) {
		shell = runtime;
	}
	
	/** Delete {@code file} file, nerver prompt*/
	public static    Process deleteFile(String file) throws IOException{
		String[] cmds = {"rm", file};
		return shell.exec(cmds);
	}
	
	 	  
	
	public static Process delete(String file) throws IOException {
		String[] cmds = null ;
		if(file.startsWith(md.sdcard)){
			cmds= new String[]{"rm", "-r", file};
			return shell.exec(cmds);
		}else{
			su.exec("rm -r "+file);
			return null;
		}
		
		
	}
	
	public static  Process deleteMult(String[] cmds) throws IOException{
		return shell.exec(cmds);
	}
	
	/** Delete {@code dire} directory, nerver prompt*/
	public static  Process deleteDirectory(String dire) throws IOException{
		String[] cmds = {"rm", "-r", dire};
		return shell.exec(cmds);
	}
	
	
	public static String toGBK(String source)  {
        StringBuilder sb = new StringBuilder();
        byte[] bytes=null;
		try {
			bytes = source.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for(byte b : bytes) {
            sb.append("%" + Integer.toHexString((b & 0xff)).toUpperCase());
        }
        
        return sb.toString();
    }
	/** Create {@code file} file, if file has already exist, update the
	 * file access and modification time to current time.
	 * @throws IOException 
	 * */
	public static  String createFile(String file) {
		return su2.exec("touch "+file);
	}
	
	/** Create directory. If parent path is not existed, also create parent directory
	 * @throws IOException */	
	public static String createDirectory(String dire) {		
		return su2.exec("mkdir "+dire);
	}
	
	
	
	/** Move or rename(if they in the same directory) file or directory
	 * @throws IOException */
	public static  Process moveFile(String src, String dir) throws IOException{
		String[] cmds = {"mv", src, dir};
		return shell.exec(cmds);
	}
	
	/** Copy file
	 * @throws IOException */
	public static  Process copyFile(String src, String dir) throws IOException{
		String[] cmds = {"cp", "-r", src, dir};
		return shell.exec(cmds);
	}
	
	/**`
	 * File soft Link
	 * @throws IOException 
	 * */
	public static  Process linkFile(String src, String dir) throws IOException{
		String[] cmds = {"ln", "-l", src, dir};
		return shell.exec(cmds);
	}
	
	
	public static Process du_s(String file) throws IOException{
		String[] cmds = {"du", "-s", file};
		return shell.exec(cmds);
	}

	public static Process ls_lhd(String file) throws IOException{
		String[] cmds = {"ls", "-l", file};
		return shell.exec(cmds);
	}
	
	public static  Process ls_Directory(String directory) throws IOException{
		if (directory.equals("/"))
			directory = "";
		String[] cmds = {"ls", "-a", directory};
		return shell.exec(cmds);
		
	}
	
	
	
	/**
	 * 	String f="uid=2000(sh4l)gid=2000(shell) groups=1004(input),1007(log),1011(adb),1015(sdcard_rw),1028(sdcard_r),3001(net_bt_admin),3002(net_bt),3003(inet),3006(net_bw_stats) context=u:r:shell:s0";
	 * @param line
	 * @return
	 */
	public static String getCurrId(){
		
		//Pattern p= Pattern.compile("[(](.+?)[)]");
		Pattern p= Pattern.compile("uid=([\\d].+?)[(]");  
	//	ff.log(Linux.execLine("id"));
		Matcher m= p.matcher(Linux.execLine("id"));
		//ff.log(m.groupCount());
		if(m.find()){
		return  m.group(1);
		}
		return  "";
	}


	/**
	 * drwxr-xr-x root     root              1970-01-01 08:00 bin
	 * @param line
	 * @return
	 */
public static UidGid getUidGid(String line){
	StringTokenizer st=new StringTokenizer(line);
	if(st.hasMoreTokens()){
		st.nextToken();
	}else{
		ff.sc("hasMoreTokens  ->->->");
		return null;
	}
	
	Process pro = null;
	try {
		pro = Runtime.getRuntime().exec("busybox id -u " +st.nextToken());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	DataInputStream di=new DataInputStream(pro.getInputStream());
	
	//String r="busybox id -u " +st.nextToken();
	
	String hh = null;
	try {
		hh = di.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ff.sc(hh,hh);
	return new UidGid(st.nextToken(), hh);

}
	
	
	
	   public static class  linuxChmod{
			
		   public static   String getFileRWX(String path){ 
			    return cast0( su.exec("ls -ld "+path));	
		   };
		   
			public static   String cast0(String w){			
				  int a[]=cast(w.substring(1, 4).toCharArray(),1);
				  int b[]=cast(w.substring(4, 7).toCharArray(),2);
				   int c[]=cast(w.substring(7, 10).toCharArray(),0);	
				
			
				   
		String aa=a[0]+""+b[0]+""+c[0]+""; 
		int special=a[1]+b[1]+c[1];
		/*  
		ff.sc(a,b,c);
		ff.sc(aa,special,w);*/
		if(special!=0){
			return special+aa;
		}else{
			return aa;
		}

				  
			   }
			
			 private  static   int[] cast(char w[],int position){
				   int jg=0;
					  int special = 0;
				  for (int i = 0; i < 3; i++) {
					  int a=0;
					  special= 0;
					   switch(w[i]){
					   case 'r':
						   a=4;
						   break;
					   case 'w':
						   a=2;
						   break;			   
					   case 'x':
						   a=1;
						   break;
					case 's':		
					if (position == 1 ) {
							  a=1;
							special = 4;
						} else {
							  a=1;
							special = 2;
						}

						   break;
					   case 'S':
						 
						   
						   if(position==1 ){
								special = 4;
					   }else{					
								special = 2;
						   }
						   
						   
						   
						   break;
					   case 'T':
							special = 1;
						  
						   
						   break;
					   case 't':
							special = 1;
						   a=1;
						   break;
					   case '-':
					//	ff.sc(a,"ffffffff");
						   break;   
					   
					   }
					   		
					   		 jg+=a;
					   		
					  
				}	   
				   return new int[]{jg,special};
				   
			   }
			
		}




	   
		public  static class UidGid{
			public	static String uid,gid;

				public  UidGid(String uid, String gid) {
					
					this.uid = uid;
					this.gid = gid;
					
				}

				@Override
				public String toString() {
					// TODO Auto-generated method stub
					return uid+","+gid;
				}
			}	   
	   
	   
}
  
          