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
import data.icfg;
import mylib.DHK;
import mylib.FF;
import mylib.MySocket;
import mylib.SendHead;
import mylib.cfg;
import mylib.su;
import mylib.cmdcs;
import mylib.command;



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
		actServer_btn_startServer(null);
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

	/*boolean startServer()
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
	*/

	
	ServerHandler han;
	private boolean b_xj_startPreview=false;
	
	class ServerHandler extends Handler
	{

	
		//public static final int msgStartPreview=300;
		public static final int msgML_EXITPROCESS=301;
		public static final int msgML_SERVER_REBOOT=302;
		
		public static final int msgSetTitle=303;
		public static final int msgCloseScreen = 304;
		public static final int msgLockUI = 305;
		//、、public static final int msgStopCamera = 306;
	
		
/*		  void domsgML_SERVER_REBOOT()
		  {
			//  ff.sc("getPackageName()"+xUI_Server.this.getPackageName());
			ff.sc("void domsgML_SERVER_REBOOT()");  
			ActivityManager manager = (ActivityManager)xUI_Server.this.getSystemService(Context.ACTIVITY_SERVICE);
			manager.restartPackage(xUI_Server.this.getPackageName());
		  
		  }
		*/
		
		public  void setTitle(String s)
		{
			Message msg=new Message();
			msg.obj=s;
			msg.what=msgSetTitle;
			this.sendMessage(msg);
		}
		
		@Override
		public void handleMessage(Message msg) 
		{
			switch (msg.what) {
		
			case msgML_EXITPROCESS: su.kill_process(getPackageName());break;
			//case msgML_SERVER_REBOOT: su.kill_process(getPackageName());break;
			case msgSetTitle:ff.setTitle((String)msg.obj);;break;
			case msgCloseScreen:closeScreen.closeScreen();break;
			case msgLockUI:lockUI();break;
		//	case msgStopCamera:stopCamera();break;
			
			
			
			}
		};
		
		
	}
	


	class SrvRecvCmd implements mylib.MySocket.OnSocketReceivedData
	{
		boolean doML_VIDEOMONITORING_getSupportedPreviewSizes()
		{
			SendHead sh=new SendHead();
			String back=new String();
			
			if(xj==null) 
			{
				ssoc.respond(command.ML_VIDEOMONITORING_GetSupportedPreviewSizes);
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
				sh.cmd=command.ML_SOCKET_RESPOND;
				sh.csA=command.ML_VIDEOMONITORING_GetSupportedPreviewSizes;
				sh.size=backdata.length;
				ssoc.send(sh,backdata,sh.size);
				///ssoc.send(backdata,sh.size);
				return true;
		}

		
		
		
		
		
		
		void doML_VIDEOMONITORING_PAUSE()
		{}
		
		void doML_VIDEOMONITORING_SETPREVIEWSIZE(SendHead sh) 
		{
			ff.scMsg("doML_VIDEOMONITORING_SETPREVIEWSIZE");
			if(sh.csA<1 || sh.csB<1)
			{
				ff.sc("parameters.setPreviewSize",sh.csA, sh.csB);
				return;
			}
			
			xjcs.iPreviewWidth=(short) sh.csA;
			xjcs.iPreviewHeight=(short) sh.csB;
		}
		
	
		
	
		void doML_VIDEOMONITORING_CAMERA_START()
		{
			boolean screen_isBright=tools.screen_isBright(xUI_Server.this);
			if(screen_isBright==false)tools.screen_wakeUp(xUI_Server.this);
			classOnPreviewFrame.startPreviewe();
			if(screen_isBright==false)han.sendEmptyMessageDelayed(han.msgCloseScreen,1000);
			
		}
		
		
void doML_VIDEOMONITORING_RESTART_SERVER()
{
	stopCamera();
	closeServer();
	actServer_btn_startServer(null)	;
	
}

/*void doML_VIDEOMONITORING_START_PREVIEW()
{
	
	
}*/


/*void doML_VIDEOMONITORING_SET_CAMERAID(int)
{
	
}
*/
		@Override
		public boolean onSocketReceivedData(SendHead sh, byte[] data ,MySocket __ssss) 
		{
			switch (sh.cmd)
			{
			case command.ML_PHONE_GetBatteryLevel:ssoc.respond(sh.cmd,tools.getBatteryLevel(xUI_Server.this),0);break;
			case command.ML_SERVER_REBOOT:han.sendEmptyMessage(han.msgML_SERVER_REBOOT);break;
			case command.ML_VIDEOMONITORING_GetSupportedPreviewSizes:return doML_VIDEOMONITORING_getSupportedPreviewSizes();
			case command.ML_EXITPROCESS:su.kill_process(getPackageName());;break;
			
			case command.ML_VIDEOMONITORING_CAMERA_STOP:stopCamera();ssoc.respond(sh.cmd); break;
			case command.ML_VIDEOMONITORING_REFRESH_RATE:xjcs.refreshRate=(short) sh.csA;break;
			case command.ML_VIDEOMONITORING_IMAGE_QUALITY:xjcs.imageQuality=(byte) sh.csA;break;	
			case command.ML_VIDEOMONITORING_SETPREVIEWSIZE:doML_VIDEOMONITORING_SETPREVIEWSIZE(sh);break;
			case command.ML_VIDEOMONITORING_SET_CAMERAID:xjcs.cameraID=(byte) sh.csA; break;
			
			case command.ML_POWER_Shutdown:su.shutdown();break;
		
			case command.ML_VIDEOMONITORING_RESTART_SERVER:doML_VIDEOMONITORING_RESTART_SERVER();break;
			case command.ML_CLOSEDISPLAY:han.sendEmptyMessage(han.msgCloseScreen);break;
			case command.ML_VIDEOMONITORING_SERVER_LOCK_UI:han.sendEmptyMessage(han.msgLockUI);break;
			case command.ML_VIDEOMONITORING_SERVER_UNLOCK_UI:bool_lockUI_inputDialog=false;break;
			//case command.ML_VIDEOMONITORING_START_PREVIEW:doML_VIDEOMONITORING_START_PREVIEW();break;
			
			case command.ML_VIDEOMONITORING_CAMERA_START:doML_VIDEOMONITORING_CAMERA_START();break;
			
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
	public boolean onPrepareOptionsMenu(Menu menu) {
		ClassIP  ip = new ClassIP (this);
		
		String ip2=ip.getWifiIP();
		if(ip2==null)return true;
		
		menu.findItem(R.id.menuServer_WIFIIP).setTitle("本机ip："+ip2);
		menu.findItem(R.id.menuServer_changeWifiIp).setTitle("绑定ip为："+ip2);
		
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menuServer_ActivateScreenLock:closeScreen.closeScreen(); break;
		
		case R.id.menuServer_changeWifiIp:menuServer_changeWifiIp(); break;
		
		
		
		}
		
		return true;
	}

private void menuServer_changeWifiIp() 
{
	ClassIP cip = new ClassIP(this);
	
	if(serverSocket!=null)
	{
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	actServer_btn_startServer(cip.getWifiIP());
	
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
	
	ClassOnPreviewFrame()
	{
		sh5.cmd=command.ML_FILE_Picture;
		sh5.csA=cmdcs.ML_CS_FILE_SEND;
	}
	

	
	
	public void  startPreviewe()
	{
		if(xj!=null)stopCamera();
		
		xj=Camera.open(xjcs.cameraID);
		
		boolean b=false;
		Parameters parameters = xj.getParameters();
		List<Camera.Size> lt;
		 lt = parameters.getSupportedPreviewSizes();
		 
		for (Camera.Size sz : lt)
		{
			if(sz.width==xjcs.iPreviewWidth && sz.height==xjcs.iPreviewHeight){b=true;break;}
		} 
		
		if(b==false)
		{
			ff.scErr("previewSizeChange(int width,int height)无效参数：",xjcs.iPreviewWidth,xjcs.iPreviewHeight);
			xjcs.iPreviewWidth=(short) xj.getParameters().getPreviewSize().width;
			xjcs.iPreviewHeight=(short) xj.getParameters().getPreviewSize().height;
		}
		
		img_rect=new Rect(0,0, xjcs.iPreviewWidth,xjcs.iPreviewHeight);
		parameters.setPreviewSize(xjcs.iPreviewWidth, 	xjcs.iPreviewHeight);
		
		xj.setParameters(parameters);
		xj.setDisplayOrientation(90); 
		
		try {
			xj.setPreviewDisplay(surface.getHolder());
		} catch (IOException e) {
			e.printStackTrace();ff.scErr("xj.setPreviewDisplay");
		}
		
			xj.setPreviewCallback(this);
			xj.startPreview();
	}
	
	
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
			if(xjcs.refreshRate>0)
			{
				long l=System.currentTimeMillis();
				if(l<execuTime+xjcs.refreshRate)
				{
					return;
				}
				execuTime=l;
			}
			
		 YuvImg=new YuvImage(data, ImageFormat.NV21,xjcs.iPreviewWidth ,xjcs.iPreviewHeight, null); 
		 
		 byteArrOut.reset();
			try {
				YuvImg.compressToJpeg(img_rect, xjcs.imageQuality, byteArrOut);
			} catch (Exception e) {
				FF.scErr("img.compressToJpeg");
				e.printStackTrace();
				//han.sendEmptyMessageDelayed(han.msgStopCamera, 1000);
				stopCamera();
				return;
			} 
			                               
			comByte=byteArrOut.toByteArray();
			sh5.size=comByte.length;

			if(! ssoc.send(sh5,comByte, sh5.size))
			{
				stopCamera();
				ff.scErr("onPreviewFrame----stopCamera");
			}
			comByte=null;
		
	}
	
}

	
	
	

/*	@Override
	public void onPreviewFrame(byte[] data, Camera camera) 
	{
	
	
	}*/

	
	void actServer_btn_startServer(String ip)
	{
		serverSocket=ssoc.newServerSocket(1234,ip);
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
					 han.setTitle("等待接入");
					
					while((clientSocket=serverSocket.accept()) != null)
					{
						ssoc.shutdownSocket();
						ssoc.initSocket(clientSocket);
						ssoc.setOnRecvSendHead(srvRecvCmd);
						ssoc.startRecv(srvRecvCmd_buffer);
							 han.setTitle("已启动服务");
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
		actServer_btn_startServer(null)	;
		
	}
	@Override
	public void onClick(View v) 
	{
		
		switch (v.getId()) {
		case R.id.actServer_btn_startServer:actServer_btn_startServer(null);break;
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
