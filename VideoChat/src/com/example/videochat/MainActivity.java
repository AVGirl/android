package com.example.videochat;

import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import data.icfg;
import mylib.ByteUtil;
import mylib.FF;
import mylib.MySocket;
import mylib.SendHead;
import mylib.su;
import mylib.cfg;
import mylib.jjFile;

public class MainActivity extends ActionBarActivity implements android.view.View.OnClickListener{
FF ff;






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cfg.init(this);
		ff=new FF(this); 
		temp__0x0x0x0x0x();
		if (1 > 0){return;};
		int i=cfg.getInt(icfg.app_entrySel, 0);
		if(i==0)return;
		if(i== icfg.app_entrySel_c)		main_btn_enterClient();	else		main_btn_enterServer();
	} 
	
	
	private void temp__0x0x0x0x0x() {
	
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	void main_btn_enterServer()
	{
		cfg.putInt(icfg.app_entrySel, icfg.app_entrySel_s);
		Intent yt=new Intent(this, xUI_Server.class);
		startActivity(yt);
		
	}
	
	void main_btn_enterClient()
	{
		Intent yt=new Intent(this, xUI_Client.class);
		startActivity(yt);
		cfg.putInt(icfg.app_entrySel, icfg.app_entrySel_c);
	}
	
	void main_btn_help()
	{    
		ff.xxk(this, "开启热点\t\t手机A点S\t\t手机B点C->连接");    
		
	}
		@Override 
	public void onClick(View v) 
		{
			if(v==null)return;
			switch (v.getId())
			{
			case R.id.main_btn_enterServer:main_btn_enterServer();break;
			case R.id.main_btn_enterClient:main_btn_enterClient();break;
			case R.id.main_btn_help:main_btn_help();break;
			
			
			
			}
		}
}