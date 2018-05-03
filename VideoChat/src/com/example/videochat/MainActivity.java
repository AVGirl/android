package com.example.videochat;

import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import mylib.ByteUtil;
import mylib.FF;
import mylib.MySocket;
import mylib.SendHead;
import mylib.cfg;
import mylib.jjFile;

public class MainActivity extends ActionBarActivity implements android.view.View.OnClickListener{
FF ff;

	void bindView()
	{
		
	} 
	ActManager actmanger;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cfg.init(this);
		ff=new FF(this);
		bindView();
		 //actmanger=ActManager.getActManager();
		// actmanger.pushActivity(this);
		
		ff.toast("hello world");
			 
		if(1==1)
		{
			main_btn_enterServer();
		}
		else
		{
			main_btn_enterClient();	
		}
		
		
			//
		//nClick(null);
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
		Intent yt=new Intent(this, xUI_Server.class);
		startActivity(yt);
		
	}
	
	void main_btn_enterClient()
	{
		Intent yt=new Intent(this, xUI_Client.class);
		startActivity(yt);
		
	}
		@Override 
	public void onClick(View v) 
		{
			
			if(v==null)return;
			
			
			switch (v.getId())
			{
			case R.id.main_btn_enterServer:main_btn_enterServer();break;
			case R.id.main_btn_enterClient:main_btn_enterClient();break;
			
			
			}
			
			
			

		}
}