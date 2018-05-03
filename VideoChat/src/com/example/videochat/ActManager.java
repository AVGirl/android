package com.example.videochat;

import java.util.Stack;

import android.app.Activity;
import mylib.FF;

public class ActManager {
	 private static Stack<Activity> activityStack;
	 private static ActManager instance;
	 private  ActManager(){
	 }
	 
	 public static ActManager getActManager(){
	  if(instance==null){
	   instance=new ActManager();
	  }
	  return instance;
	 }
	//退出栈顶Activity
	 public void popActivity(Activity activity){
	  if(activity!=null){
	   activity.finish();
	   activityStack.remove(activity);
	   activity=null;
	  }
	 }

	//获得当前栈顶Activity
	 public Activity currentActivity(){
	  Activity activity=activityStack.lastElement();
	  return activity;
	 }

	//将当前Activity推入栈中
	 public void pushActivity(Activity activity){
	  if(activityStack==null){
	   activityStack=new Stack<Activity>();
	  }
	  activityStack.add(activity);
	 }
	 
	 public void popAllActivity(){
		 FF.sc("activityStack.size(); "+activityStack.size());
		 for (int i = 0; i < activityStack.size(); i++) {
			 
			 activityStack.pop().finish();
		}
		  }
	 //退出栈中所有Activity
	 public void popAllActivityExceptOne(Class cls){
	  while(true){
	   Activity activity=currentActivity();
	   if(activity==null){
	    break;
	   }
	   if(activity.getClass().equals(cls) ){
	    break;
	   }
	   popActivity(activity);
	  }
	 }
	}
