package com.example.videochat;


import android.support.v7.app.ActionBarActivity;

import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import mylib.DHK;
import mylib.FF;
import mylib.MySocket;
import mylib.SendHead;
import mylib.su;
import mylib.cmdcs;
import mylib.command;

class XJCS
{
	public static int iPreviewWidth=0;
	public static int iPreviewHeight=0;
	public static int  refreshRate = 50;//刷新率  毫秒
	public static int imageQuality = 60;//图像质量
	
}


public class xUI_Server extends ActionBarActivity implements android.view.View.OnClickListener{

	MySocket ssoc=new MySocket();
	ServerSocket serverSocket;
	SendHead gsh=new SendHead();
	SrvRecvCmd srvRecvCmd=new SrvRecvCmd();
	byte[] srvRecvCmd_buffer=new byte[1024*1024];
	DHK dhk;
	XJCS xjcs=new XJCS();
	ClassOnPreviewFrame classOnPreviewFrame=new ClassOnPreviewFrame();
	CloseScreen closeScreen; 
	LinearLayout ll_main;
	
	byte ifrontCamera=0;
	
	
	Camera xj=null;
	android.view.SurfaceView surface;
	FF ff;
	ImageView img_xjPreview;
	//SurfaceView
	private boolean bool_lockUI_inputDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_server);
		ff=new FF(this,this);
		dhk=new DHK(this);
		closeScreen=new CloseScreen(this,this);
		bindView();
		surface.getHolder().addCallback(surfaceCallback);
		ssoc.toNetMsg=false;
	
		han=new ServerHandler();
		actServer_btn_startServer();
		//getActionBar().hide();
		
		
	}
	
	
	
	//Camera camera;// 声明Camera类的对象camera

	
	
	
	
	void closeServer()
	{

		
		try {
			ssoc.shutdownSocket();
			if(serverSocket!=null)serverSocket.close();
			
		} catch (IOException e) {
			ff.scErr("	void closeServer()");
			e.printStackTrace();
		}

		
	}
	
	void stopCamera()
	{
		if(xj==null)return;
		xj.stopPreview();
		xj.setPreviewCallback(null);
		xj.release();
		xj=null;

	}
	@Override
	public void onBackPressed() 
	{
		stopCamera();
		closeServer();
		this.finish();
		
		
	}
	void bindView()
	{
		surface=(SurfaceView) findViewById(R.id.surfaceView1);
		img_xjPreview=(ImageView) findViewById(R.id.actServer_img_imageView1);
		ll_main= (LinearLayout) findViewById(R.id.actServer_ll_main);
		//ff.sc("xj.getParameters().getPreviewSize().height=="+xj.getParameters().getPreviewSize().height);
		surface.setFitsSystemWindows(true);
		//ff.sc("xj.getParameters().getPreviewSize().height=="+xj.getParameters().getPreviewSize().height);
	} 
/*	@Override
	protected void onPause() 
	{
	
		super.onPause();
	}*/

	void unlockUI()
	{
		bool_lockUI_inputDialog=false;
		ll_main.setVisibility(View.VISIBLE);
		this.getActionBar().show();
		
		
	}
	


	void lockUI()
	{
		bool_lockUI_inputDialog=true;
		ll_main.setVisibility(View.GONE);
		this.getActionBar().hide();
		
		
		AlertDialog.Builder aa=new AlertDialog.Builder(this);
		aa.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				
				if(bool_lockUI_inputDialog)
				{
					ff.sc("--if(bool_lockUI_inputDialog)-----su.shutdown();");	
					su.shutdown();//todo
				}
			}
		});
		aa.setTitle("信息:");
		aa.setMessage("点击关闭");
		aa.setPositiveButton("确定", null);
		aa.show();
		
	}
	
	
	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback()
	{

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			//startPreview();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO 自动生成的方法存根
			
		}  
	
		
		
	};

	boolean startServer()
	{
		 Socket clientSocket;		
		serverSocket=ssoc.newServerSocket(1234);
		if(serverSocket==null)
		{
			ff.toast("if(serverSocket==null)");
			return false;
		}
		ff.toast("等待连接");
		 clientSocket= ssoc.accept(serverSocket);
		if(clientSocket==null)
		{
		ff.toast("if(clientSocket==null)");	
			return false;
		}
		
	if(	ssoc.initSocket(clientSocket)==false)
	{
		ff.toast("if(	socSer.initSocket(clientSocket)==false)");		
		return false;
	}
	
		return true;
		
	}
	

	
	ServerHandler han;
	private boolean b_xj_startPreview=false;
	
	class ServerHandler extends Handler
	{

	
		public static final int msgStartPreview=300;
		public static final int msgML_EXITPROCESS=301;
		public static final int msgML_SERVER_REBOOT=302;
		
		public static final int msgSetTitle=303;
		public static final int msgCloseScreen = 304;
		public static final int msgLockUI = 305;
	
		
/*		  void domsgML_SERVER_REBOOT()
		  {
			//  ff.sc("getPackageName()"+xUI_Server.this.getPackageName());
			ff.sc("void domsgML_SERVER_REBOOT()");  
			ActivityManager manager = (ActivityManager)xUI_Server.this.getSystemService(Context.ACTIVITY_SERVICE);
			manager.restartPackage(xUI_Server.this.getPackageName());
		  
		  }
		*/
	
		
		@Override
		public void handleMessage(Message msg) 
		{
			switch (msg.what) {
			case msgStartPreview:startPreview();break;
			case msgML_EXITPROCESS: su.kill_process(getPackageName());break;
			//case msgML_SERVER_REBOOT: su.kill_process(getPackageName());break;
			case msgSetTitle:ff.setTitle((String)msg.obj);;break;
			case msgCloseScreen:closeScreen.closeScreen();break;
			case msgLockUI:lockUI();break;
			
			
			
			}
		};
		
		
	}
	
	

	
	
	
	void startPreview()
	{
		
/*		ff.sc("	void startPreview()");
		
	if(	xjcs.iPreviewWidth==0)xjcs.iPreviewWidth=xj.getParameters().getPreviewSize().width;
	if(	xjcs.iPreviewHeight==0)	xjcs.iPreviewHeight=xj.getParameters().getPreviewSize().height;
		 
		try { 
			//review.set("orientation", "portrait");
			//preview.set("rotation",90);
			
			Parameters p=xj.getParameters();
			p.setRotation(90);
			xj.setParameters(p);
			
			
			xj.setDisplayOrientation(90); 
		
			xj.setPreviewDisplay(surface.getHolder());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		xj.setPreviewCallback(classOnPreviewFrame);
		classOnPreviewFrame.startPreviewe(xjcs.iPreviewWidth, xjcs.iPreviewHeight);
		
		xj.startPreview();
	
		b_xj_startPreview=true;*/
	}
	


	class SrvRecvCmd implements mylib.MySocket.OnRecvSendHead
	{
		boolean doML_VIDEOMONITORING_getSupportedPreviewSizes()
		{
			//ff.sc("doML_VIDEOMONITORING_getSupportedPreviewSizes");
			SendHead sh=new SendHead();
			
			String back=new String();
			 
			if(xj==null) 
			{
				ff.scMsg("if(xj==null)------doML_VIDEOMONITORING_getSupportedPreviewSizes");
				ssoc.senderr();
				return true;
			}

			Parameters parameters = xj.getParameters();  
				List<Camera.Size> lt;
				 lt = parameters.getSupportedPreviewSizes(); 
				 
				 
				for (Camera.Size size : lt) 
				{
					back+= size.width+"x"+size.height+";";
					
				}
				
				byte[] backdata=back.getBytes();
				sh.cmd=command.ML_VIDEOMONITORING_getSupportedPreviewSizes;
				sh.size=backdata.length;
				ssoc.send(sh);
				ssoc.send(backdata,sh.size);
			return true;
		}

		
		
		
		
		
		void doML_VIDEOMONITORING_GETWH()
		{		
			SendHead sh=new SendHead();
			sh.cmd=command.ML_VIDEOMONITORING_GETWH;
			sh.csA=xjcs.iPreviewWidth;
			sh.csB=xjcs.iPreviewHeight;
			ssoc.send(sh);
		}
		void doML_VIDEOMONITORING_STOP(){stopCamera();}
		
		void doML_VIDEOMONITORING_PAUSE()
		{}
		

		void doML_VIDEOMONITORING_SETWH(SendHead sh)
		{
			//ff.sc("doML_VIDEOMONITORING_SETWH",sh.csA,sh.csB);
	/*		Parameters parameters = xj.getParameters();
			parameters.setPreviewSize(sh.csA, sh.csB);
			xj.setParameters(parameters);*/
			doML_VIDEOMONITORING_SETPREVIEWSIZE(sh);
		}
		
		void doML_VIDEOMONITORING_SETPREVIEWSIZE(SendHead sh) 
		{
			
/*			if(b_xj_startPreview==true)
			{
				xj.stopPreview();
				ff.scMsg("if(b_xj_startPreview==true)");
				//return;
			}*/
			
			
		/*	if( xj==null)
			{
				ff.scErr("if( xj==null) 00011");
				return;
			}*/
	/*		Parameters parameters = xj.getParameters() ; 
			parameters.setPreviewSize(sh.csA, sh.csB);
*/
			//ff.sc("new size===", sh.csA, sh.csB);
			
			if(sh.csA<1 || sh.csB<1)
			{
				ff.scErr("	if(sh.csA<1 || sh.csB　<1)");
				ff.sc("parameters.setPreviewSize",sh.csA, sh.csB);
				return;
			}
			
			classOnPreviewFrame.startPreviewe(sh.csA, sh.csB,ifrontCamera);
/*			xjcs.iPreviewWidth=sh.csA;
			xjcs.iPreviewHeight=sh.csB;
			 
			try {
				xj.setParameters(parameters);
				xj.stopPreview();
				
				xj.startPreview();
				
			} catch (Exception e) {
				ff.scErr("xj.setParameters(parameters);");
				e.printStackTrace();
			}*/
		}
		
	
		
	
		void doML_VIDEOMONITORING_OPEN_CAMERA(boolean frontCamera)
		{
			
			ifrontCamera=frontCamera?1:0;
			
			ff.sc("doML_VIDEOMONITORING_OPEN_CAMERA");
			
			boolean screen_isBright=tools.screen_isBright(xUI_Server.this);
			if(screen_isBright==false)tools.screen_wakeUp(xUI_Server.this);
			
			classOnPreviewFrame.startPreviewe(0, 0, ifrontCamera);
			
			if(screen_isBright==false)han.sendEmptyMessageDelayed(han.msgCloseScreen,1000);
			
		}
		
		
void doML_VIDEOMONITORING_RESTART_SERVER()
{
	stopCamera();
	closeServer();
	actServer_btn_startServer()	;
	
}
		@Override
		public boolean onRecvSendHead(SendHead sh, byte[] data) 
		{
		//ff.sc("onRecvSendHead",sh.cmd);
			
			switch (sh.cmd)
			{
			
			case command.ML_SERVER_REBOOT:han.sendEmptyMessage(han.msgML_SERVER_REBOOT);break;
			case command.ML_VIDEOMONITORING_getSupportedPreviewSizes:return doML_VIDEOMONITORING_getSupportedPreviewSizes();
			case command.ML_EXITPROCESS:su.kill_process(getPackageName());;break;
			
			case command.ML_VIDEOMONITORING_GETWH:doML_VIDEOMONITORING_GETWH();break;
			case command.ML_VIDEOMONITORING_STOP:doML_VIDEOMONITORING_STOP();break;
			case command.ML_VIDEOMONITORING_PAUSE:doML_VIDEOMONITORING_PAUSE();break;
			case command.ML_VIDEOMONITORING_REFRESH_RATE:xjcs.refreshRate=sh.csA;break;
			case command.ML_VIDEOMONITORING_IMAGE_QUALITY:xjcs.imageQuality=sh.csA;break;	
			case command.ML_VIDEOMONITORING_SETWH:doML_VIDEOMONITORING_SETWH(sh);break;
		
			case command.ML_VIDEOMONITORING_SETPREVIEWSIZE:doML_VIDEOMONITORING_SETPREVIEWSIZE(sh);break;
			case command.ML_VIDEOMONITORING_OPEN_BACK_CAMERA:doML_VIDEOMONITORING_OPEN_CAMERA(false);break;
			case command.ML_VIDEOMONITORING_OPEN_FRONT_CAMERA:doML_VIDEOMONITORING_OPEN_CAMERA(true);break;
			case command.ML_POWER_Shutdown:su.shutdown();break;
		
			case command.ML_VIDEOMONITORING_RESTART_SERVER:doML_VIDEOMONITORING_RESTART_SERVER();break;
			case command.ML_CLOSEDISPLAY:han.sendEmptyMessage(han.msgCloseScreen);break;
			
			
			case command.ML_VIDEOMONITORING_SERVER_LOCK_UI:han.sendEmptyMessage(han.msgLockUI);break;
			case command.ML_VIDEOMONITORING_SERVER_UNLOCK_UI:bool_lockUI_inputDialog=false;break;
			
			
			case command.ML_VIDEOMONITORING_START_PREVIEW:{
				han.sendEmptyMessage(ServerHandler.msgStartPreview);
				break;
				//return false;
				}//todo
			
			
			}
			
			return true;
		}
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.server, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menuServer_ActivateScreenLock:closeScreen.closeScreen(); break;
		}
		
		return true;
	}

class ClassOnPreviewFrame implements  PreviewCallback
{
	
	ByteArrayOutputStream byteArrOut=new ByteArrayOutputStream(1024*1024*2);//2MB
	long execuTime=0;
	SendHead sh5=new SendHead();
	/*前置摄像头宽高不能用于后置摄像头*/
	YuvImage YuvImg;
	byte[]comByte;
	Rect img_rect;
	int  preview_w=0;
	int  preview_h=0;
	
	
	ClassOnPreviewFrame()
	{
		sh5.cmd=command.ML_FILE_Picture;
		sh5.csA=cmdcs.ML_CS_FILE_SEND;
	
		
	}
	
	
	//PreviewSizeChange
	/**
	 * 0 后 1前
	 * @param width
	 * @param height
	 * @param frontCamera
	 */
	
	public void  startPreviewe(int width,int height,int frontCamera)
	{
		if(xj!=null)stopCamera();
	
		ff.sc("previewSizeChange:",width,height);
		
		xj=Camera.open(frontCamera);
		
		boolean b=false;
		Parameters parameters = xj.getParameters();
		List<Camera.Size> lt;
		 lt = parameters.getSupportedPreviewSizes();
		 
		for (Camera.Size sz : lt)
		{
			if(sz.width==width && sz.height==height){b=true;break;}
		} 
		
		if(b==false)
		{
			ff.scErr("previewSizeChange(int width,int height)无效参数：",width,height);
			width=xj.getParameters().getPreviewSize().width;
			height=xj.getParameters().getPreviewSize().height;
		}
		
		img_rect=new Rect(0,0, width,height);
		  preview_w=width;
		  preview_h=height;
		
		parameters.setPreviewSize(width, height);
		xj.setParameters(parameters);
		xj.setDisplayOrientation(90); 
		
		try {
			xj.setPreviewDisplay(surface.getHolder());
		} catch (IOException e) {
			e.printStackTrace();ff.scErr("xj.setPreviewDisplay");
		}
		
			xj.setPreviewCallback(this);
			xj.startPreview();
		  
/*		  xj.startPreview();
		  b_xj_startPreview=true;
		  ff.sc("end ************");*/
	}
	
	
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
			if(xjcs.refreshRate!=0)
			{
				long l=System.currentTimeMillis();
				if(l<execuTime+xjcs.refreshRate)
				{
					return;
				}
				execuTime=l;
			}

		 YuvImg=new YuvImage(data, ImageFormat.NV21,preview_w ,preview_h, null); 
		 
		 byteArrOut.reset();
			try {
				YuvImg.compressToJpeg(img_rect, xjcs.imageQuality, byteArrOut);
			} catch (Exception e) {
				ff.sc("w=="+xj.getParameters().getPreviewSize().width+"   h=="+xj.getParameters().getPreviewSize().height);
				FF.scErr("img.compressToJpeg");
				e.printStackTrace();
				xj.setPreviewCallback(null);
			} 
			 
			comByte=byteArrOut.toByteArray();
			sh5.size=comByte.length;

			if(! ssoc.send(sh5,comByte, sh5.size))
			{
				stopCamera();
				ff.scErr("onPreviewFrame----stopCamera");
			}
			//else ff.sc("发送图片 已发送----onPreviewFrame");
			comByte=null;
		
	}
	
}

	
	
	

/*	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
	
	
	}*/

	
	void actServer_btn_startServer()
	{
		serverSocket=ssoc.newServerSocket(1234);
		if(serverSocket==null)
		{
			ff.toast("if(serverSocket==null)");
			return ;
		}
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
					 Socket clientSocket=null;
				
					
					while((clientSocket=serverSocket.accept()) != null)
					{
						ssoc.shutdownSocket();
						ssoc.initSocket(clientSocket);
						ssoc.setOnRecvSendHead(srvRecvCmd);
						ssoc.startRecvSendHead_buffer(srvRecvCmd_buffer);
						 Message msg=new Message();
							msg.what=han.msgSetTitle;
							msg.obj=new String("已启动服务");
						han.sendMessage(msg);
						clientSocket=null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					ff.scErr("IOException,actServer_btn_startServer");
					return;
				}
				
			}}).start();
		
	
		
	}
	
	
	
	
	// byte[]ClientBuffer=new byte[1024*1024*15];
	// MySocket mysoc2;
	void actServer_btn_enterClient()
	{
		
		Intent yt=new Intent(this, xUI_Client.class);
		startActivity(yt);
	
	}
	
		
	void actServer_btn_test_0000000000000000000000000000000000000()
	{
	}
	
	void actServer_btn_stopServer()
	{
		stopCamera();
		closeServer();
		ff.setTitle("已停止服务");
		
	}
	
	void actServer_btn_rebootServer()
	{
		stopCamera();
		closeServer();
		ff.toast("等待连接");
		ff.setTitle("等待连接");
		actServer_btn_startServer()	;
		
	}
	@Override
	public void onClick(View v) 
	{
		
		switch (v.getId()) {
		case R.id.actServer_btn_startServer:actServer_btn_startServer();break;
		case R.id.actServer_btn_enterClient:actServer_btn_enterClient();break;
		case R.id.actServer_btn_test:actServer_btn_test_0000000000000000000000000000000000000();break;
		case R.id.actServer_btn_rebootServer:actServer_btn_rebootServer();break;
		case R.id.actServer_btn_stopServer:actServer_btn_stopServer();break;
		
		
		default:  
			break;
		}
		
		
		
	}
	

	
	
/*	void temp123(SendHead sh, byte[] data) 
	{
		ff.sc("temp123",sh.cmd,sh.size);
	}*/
	
	
	
	

/*
	@Override
	public void onClick(View v) {
	//	ff.toast("fffffff");
		 Animation animation = AnimationUtils.loadAnimation(this, R.animator.qwe);  
		
		 img_xjPreview.startAnimation(animation);
	
		
	}*/
}
