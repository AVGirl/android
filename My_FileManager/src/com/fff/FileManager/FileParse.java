package com.fff.FileManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fff.misc.ff;

import android.R.array;


public class FileParse {

	public static myfile[] parseSimple(String fileList ,String directoryList){
		if(fileList==null){
			return new myfile[0];
		}
		
		
		
		if(fileList!=null){
			if(fileList.equals("")){fileList=null;}
		}
		
		if(directoryList!=null){
			if(directoryList.equals("")){directoryList=null;}
		}
		
		
int countFile=0;
		
		      
		 myfile[] myDir=null;
		String dirs[]=null;
		int dirsLength = 0;
		if (directoryList != null) {
			directoryList = directoryList.replace("/", "");
			dirs = directoryList.split("\n");
			dirsLength=dirs.length;
			myDir=new myfile[dirsLength];
		}

		
		int justFileLength=0;
		 myfile justFile[]=null;
		int fileLength = 0;
		String files[]=null;
		if (fileList != null) {
			fileList = fileList.replace("/", "");
			files= fileList.split("\n");
			fileLength=files.length;
			justFile=new myfile[fileLength-dirsLength];
			justFileLength=fileLength-dirsLength;
			
			
			countFile=fileLength;
		}

		   
		   for (int i = 0; i <dirsLength ; i++) {
			   myDir[i]=new myfile(dirs[i],0,0,false);			
			 }	

		   
		   
		   
		 
			
		   int count=0;
		   for (int i = 0; i <countFile; i++) {
			   String name =files[i];
			   boolean add=true;
			   
			   for (int y = 0; y<dirsLength; y++) {
					   if(dirs[y].equals(name)){
						   add=false;
					   }
				}
			   
			   
			   //ff.log("name:-------"+name);
			   if(add==false){
				   continue;
			   }	   
			   
			   justFile[count]=new myfile(name,0,0,true);
			   count++;
			 }	
		   
	
		   
/*		  	ff.log("countFile---"+countFile);
		  	ff.log("dirsLength---"+dirsLength);	   
			ff.log("justFileLength---"+justFileLength);*/
			
		   
		myfile ok[]=new myfile[fileLength];
		
		if(myDir!=null){
			System.arraycopy(myDir, 0, ok, 0, myDir.length);
			System.arraycopy(justFile, 0, ok, myDir.length, justFile.length);
			return ok;	
		}else{
			return justFile;	
		}
  
		
	}	
    
public static myfile[] parse(String list){		//'(z|f)ood'
	//String u=readTextFile("C:\\Users\\Administrator\\Desktop\\新建文本文档.txt");
	
	myfile[] my;
	//ff.log("list:\n\n"+list);
	if(list==null){
		ff.sc("exception ->  fileparse");
		return my=new myfile[0];
	}
	StringTokenizer tt=new StringTokenizer(list,"\n");
	my=new myfile[tt.countTokens()];
	boolean isFile;
	int i=0;
	while (tt.hasMoreTokens()){
		String l=tt.nextToken().trim();
		if(l.equals("") || l.length()<10){continue;}
		
		isFile=true;
		String name=getRegExFirst(ll.name,l)[1];
	/*	if(name.indexOf("->")!=-1){
			//ff.log(name);
			//ff.log(name.split("->"));
			name=name.split("->")[1].trim();
			//continue;
			
		}
		
		*/
		
		String rwx=getRegExFirst(ll.rwx,l)[1];
		int size=0;
		String time=null;
		
		
		if(l.startsWith("-")){
			size=Integer.valueOf(getRegExFirst(ll.size,l)[1]) ;
			time=getRegExFirst(ll.time,l)[1];
		}else{
			isFile=false;
			String aa[]=getRegExFirst(ll.dirTime,l);
			if(aa!=null){
				if(aa.length>=2){
					
					
					time=getRegExFirst(ll.dirTime,l)[1];	
					
					
					
					
				}
			}
			
		}
		
	 
	   myfile qq=new myfile(name,getTimelong(time),size,isFile);
	   my[i]=qq;
	   i++;
		
	}
	
	return my;
	//ff.sc(my[1].isfile,my[1].getName(),my[1].length(),my[1].lastModified());
	
}
	
	
	
	
	
	
	
	
	
	
	public static String[] getRegExFirst(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		String[] jg = null;
		// ff.sc(m.lookingAt());这话有毒 使第一个消失
		if (m.find()) {
			jg = new String[m.groupCount() + 1];
			for (int i = 0; i < m.groupCount() + 1; i++) {
				jg[i] = m.group(i);
			}
		}

		return jg;
	}

	public static long getTimelong(String time) {
		if (time == null) {
			return 0;
		}
		SimpleDateFormat aa1 = null;
		if (aa1 == null) {
			aa1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		try {
			return aa1.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static class ll {
		public static final String rwx = "(.{1,10})[\\s]";
		public static final String size = "[\\s]{1}.{1,}[\\s].{1,}[\\s]{1,}([0-9]{1,})[\\s]";
		// static final String
		// time="[\\s]{1,}[0-9]{1,}[\\s]{1,}(.{1,}[\\s].{1,})[\\s]{1}";
		// lrwxrwxrwx root root 2018-01-03 22:55 usbdisk -> /storage/usbdisk
		// static final String
		// time="[\\s]{1,}[0-9]{1,}[\\s]{1,}(.{1,}[\\s][0-9]{2}:[0-9]{2})[\\s]{1}";
		public static final String time = "[\\s]{1,}[0-9]{1,}[\\s]{1,}(.{1,}[\\s][0-9]{2}:[0-9]{2})\\b";
		public static final String name = "[0-9]{2}:[0-9]{2}[\\s](.*)";
		// lrwxrwxrwx root root 2018-01-03 22:55 usbdisk -> /storage/usbdisk
		// lrwxrwxrwx root root 2018-01-03 22:55 vendor -> /system/vendor
		// static final String
		// dirTime="[\\s]{1,}[\\w]{1,}[\\s]{1,}(.{1,}[\\s].{1,})[\\s]";
		public static final String dirTime = "[\\s]{1}[\\s]{1,}[\\w]{1,}[\\s]{1,}(.{1,}[\\s][0-9]{2}:[0-9]{2})";

	}

}
