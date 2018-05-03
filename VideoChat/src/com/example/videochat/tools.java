package com.example.videochat;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class tools 
{
	public static void uninstallApk(Context context, String packageName) {    
	    Uri uri = Uri.parse("package:" + packageName);    
	    Intent intent = new Intent(Intent.ACTION_DELETE, uri);    
	    context.startActivity(intent);    
	}  
}
