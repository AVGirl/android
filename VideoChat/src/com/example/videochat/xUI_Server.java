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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
import mylib.OnRecvSendHead;
import mylib.SendHead;
import mylib.SuUtil;
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
	
	
	Camera xj=null;
	android.view.SurfaceView surface;
	FF ff;
	ImageView img_xjPreview;
	//SurfaceView
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_server);
		ff=new FF(this);ff.setActivity(this);
		dhk=new DHK(this);
		bindView();
		surface.getHolder().addCallback(surfaceCallback);
		img_xjPreview_rotate_back();
		ssoc.toNetMsg=false;
	
		ActManager.getActManager().pushActivity(this);
		xj=Camera.open(0);
		
/*
		Parameters parameters = xj.getParameters();
			List<Camera.Size> lt;
			 lt = parameters.getSupportedPreviewSizes();
			for (Camera.Size size : lt) 
			{
				ff.sc( size.width+"x"+size.height+";");
			}
		*/
		
		
	
		 han=new ServerHandler();
			
			
	/*	java.util.Date d = new java.util.Date();
		setTitle("" + System.currentTimeMillis());
*/
		actServer_btn_startServer();
		
		
	//	FF.sc(getDisplayOrientation(this,1));
		
		// actServer_btn_enterClient();
		//startPreview();
	}
	
	
	
	//Camera camera;// 声明Camera类的对象camera

	private int getDisplayOrientation(Activity activity, int cameraId) { // 调整摄像头方向
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(cameraId, info);
			int rotation = activity.getWindowManager().getDefaultDisplay()
					.getRotation();
			int degrees = 0;
			
			switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
			}
			
			int result;
			
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				result = (info.orientation + degrees) % 360;
				result = (360 - result) % 360; // compensate the mirror
			} else {
				result = (info.orientation - degrees + 360) % 360;
			}
			//FF.sc("result");
			return result;
			//camera.setDisplayOrientation(result);
		}
	
	
	
	
	
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
		img_xjPreview=(ImageView) findViewById(R.id.imageView1);
		//ff.sc("xj.getParameters().getPreviewSize().height=="+xj.getParameters().getPreviewSize().height);
		surface.setFitsSystemWindows(true);
		//ff.sc("xj.getParameters().getPreviewSize().height=="+xj.getParameters().getPreviewSize().height);
		
	} 
	
	void img_xjPreview_rotate_front()
	{
	
	}
	
	void img_xjPreview_rotate_back()
	{
		if (1 > 0){return;};
		 
		 DisplayMetrics dm = getResources().getDisplayMetrics();
		int  width = dm.widthPixels;
		 int height = dm.heightPixels;

		 LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) img_xjPreview.getLayoutParams();
		params.height=width;//设置当前控件布局的高度
		params.width=width;//设置当前控件布局的高度
		img_xjPreview.clearAnimation();
		img_xjPreview.refreshDrawableState();
		 Animation animation = AnimationUtils.loadAnimation(this, R.animator.img_preview_rotate_back1111);  
		 img_xjPreview.startAnimation(animation);
		
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
		
		
		
		  void domsgML_SERVER_REBOOT()
		  {
			//  ff.sc("getPackageName()"+xUI_Server.this.getPackageName());
			ff.sc("void domsgML_SERVER_REBOOT()");  
			ActivityManager manager = (ActivityManager)xUI_Server.this.getSystemService(Context.ACTIVITY_SERVICE);
			manager.restartPackage(xUI_Server.this.getPackageName());
		  
		  }
		
		
		void domsgML_EXITPROCESS()
			  {
				  		ff.sc("getPackageName()"+getPackageName());
						  SuUtil.kill_process(getPackageName());
			  }
			  
		
		@Override
		public void handleMessage(Message msg) 
		{
			switch (msg.what) {
			case msgStartPreview:startPreview();break;
			case msgML_EXITPROCESS:domsgML_EXITPROCESS();break;
			case msgML_SERVER_REBOOT:domsgML_SERVER_REBOOT();break;
			case msgSetTitle:ff.setTitle((String)msg.obj);;break;
			
			
			}
		};
		
		
	}
	
	

	
	
	
	void startPreview()
	{
		
		ff.sc("	void startPreview()");
		
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
		classOnPreviewFrame.previewSizeChange(xjcs.iPreviewWidth, xjcs.iPreviewHeight);
		
		xj.startPreview();
	
		b_xj_startPreview=true;
	}
	


	class SrvRecvCmd implements OnRecvSendHead
	{
		boolean doML_VIDEOMONITORING_getSupportedPreviewSizes()
		{
			//ff.sc("doML_VIDEOMONITORING_getSupportedPreviewSizes");
			SendHead sh=new SendHead();
			
			
			String back=new String();

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
			//	ff.sc("准备返回数据");
				ssoc.send(sh);
				//ff.sc("已返回数据");
				//ff.sc("sh.size==="+sh.size);
				ssoc.send(backdata,sh.size);
				//ff.sc("已返回数据---backdata");
				
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
			
			if(b_xj_startPreview==true)
			{
				ff.scMsg("if(b_xj_startPreview==true)");
				return;
			}
			
			
			if( xj==null)
			{
				ff.scErr("if( xj==null) 00011");
			}
			Parameters parameters = xj.getParameters() ; 
			parameters.setPreviewSize(sh.csA, sh.csB);

			
			if(sh.csA<1)
			{
				ff.scErr("if(sh.csA<1)");
				ff.sc("parameters.setPreviewSize",sh.csA, sh.csB);
				return;
			}
			
			xjcs.iPreviewHeight=sh.csA;
			xjcs.iPreviewHeight=sh.csB;
			
	/*		Parameters p=xj.getParameters();
			p.setRotation(180);
			xj.setParameters(p);*/
			
			
			
			try {
				xj.setParameters(parameters);
				xj.stopPreview();
				classOnPreviewFrame.previewSizeChange(xjcs.iPreviewWidth, xjcs.iPreviewHeight);
				xj.startPreview();
				
			} catch (Exception e) {
				ff.scErr("xj.setParameters(parameters);");
				e.printStackTrace();
			}
		}
		
	
		
	
		void doML_VIDEOMONITORING_OPEN_CAMERA(boolean frontCamera)
		{
			ff.sc("doML_VIDEOMONITORING_OPEN_CAMERA");
		
			stopCamera();
			xj=Camera.open(frontCamera?1:0);
			han.sendEmptyMessage(han.msgStartPreview);
			
		}
		
		

		@Override
		public boolean onRecvSendHead(SendHead sh, byte[] data) 
		{
		//ff.sc("onRecvSendHead",sh.cmd);
			
			switch (sh.cmd)
			{
			
			case command.ML_SERVER_REBOOT:han.sendEmptyMessage(han.msgML_SERVER_REBOOT);break;
			case command.ML_VIDEOMONITORING_getSupportedPreviewSizes:return doML_VIDEOMONITORING_getSupportedPreviewSizes();
			case command.ML_EXITPROCESS:SuUtil.kill_process(getPackageName());;break;
			
			case command.ML_VIDEOMONITORING_GETWH:doML_VIDEOMONITORING_GETWH();break;
			case command.ML_VIDEOMONITORING_STOP:doML_VIDEOMONITORING_STOP();break;
			case command.ML_VIDEOMONITORING_PAUSE:doML_VIDEOMONITORING_PAUSE();break;
			case command.ML_VIDEOMONITORING_REFRESH_RATE:xjcs.refreshRate=sh.csA;break;
			case command.ML_VIDEOMONITORING_IMAGE_QUALITY:xjcs.imageQuality=sh.csA;break;	
			case command.ML_VIDEOMONITORING_SETWH:doML_VIDEOMONITORING_SETWH(sh);break;

			case command.ML_CLIENT_SOCKET_STOP:	ssoc.sendCmd(command.ML_CLIENT_SOCKET_STOP);break;
		
			case command.ML_VIDEOMONITORING_SETPREVIEWSIZE:doML_VIDEOMONITORING_SETPREVIEWSIZE(sh);break;
			case command.ML_VIDEOMONITORING_OPEN_BACK_CAMERA:doML_VIDEOMONITORING_OPEN_CAMERA(false);break;
			case command.ML_VIDEOMONITORING_OPEN_FRONT_CAMERA:doML_VIDEOMONITORING_OPEN_CAMERA(true);break;
			case command.ML_POWER_Shutdown:SuUtil.shutdown();break;
		
			
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

class ClassOnPreviewFrame implements  PreviewCallback
{
	
	ByteArrayOutputStream byteArrOut=new ByteArrayOutputStream(1024*1024*2);//2MB
	long execuTime=0;
	SendHead sh5=new SendHead();

	
	/*前置摄像头宽高不能用于后置摄像头*/
	//YuvImage img=new YuvImage(data, ImageFormat.NV21,xjcs.iPreviewWidth ,xjcs.iPreviewHeight, null); 
	YuvImage YuvImg;//=new YuvImage(data, ImageFormat.NV21,640 ,480, null); 
	byte[]comByte;
	Rect img_rect;//=new Rect(0,0, 640,480);
	int  preview_w=0;
	int  preview_h=0;
	
	
	ClassOnPreviewFrame()
	{
		sh5.cmd=command.ML_FILE_Picture;
		sh5.csA=cmdcs.ML_CS_FILE_SEND;
	
		
	}
/*	
	void checkPreviewSize(int width,int height)
	{
		Parameters parameters = xj.getParameters();
		List<Camera.Size> lt;
		 lt = parameters.getSupportedPreviewSizes();
		for (Camera.Size sz : lt) 
		{
			if(sz.width==width && sz.height==height)return true;
		}
		
		
		previewSizeChange(xj.getParameters().getPreviewSize().width,xj.getParameters().getPreviewSize().height);
		
	}*/

	
	public void  previewSizeChange(int width,int height)
	{
		boolean b=false;
		Parameters parameters = xj.getParameters();
		List<Camera.Size> lt;
		 lt = parameters.getSupportedPreviewSizes();
		for (Camera.Size sz : lt)if(sz.width==width && sz.height==height){b=true;break;} 

		if(b==false)
		{
			ff.scErr("previewSizeChange(int width,int height)无效参数：",width,height);
			width=xj.getParameters().getPreviewSize().width;
			height=xj.getParameters().getPreviewSize().height;
		}
		
		//img_rect=new Rect(0,0, 640,480);
		img_rect=new Rect(0,0, width,height);
		  preview_w=width;
		  preview_h=height;
		
		
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
					 Message msg=new Message();
						msg.what=han.msgSetTitle;
						msg.obj=new String("已启动服务");
					
					while((clientSocket=serverSocket.accept()) != null)
					{
						ssoc.shutdownSocket();
						ssoc.initSocket(clientSocket);
						ssoc.setOnRecvSendHead(srvRecvCmd);
						ssoc.startRecvSendHead_buffer(srvRecvCmd_buffer);
						han.sendMessage(msg);
						clientSocket=null;
					}
				} catch (IOException e) {
					e.printStackTrace();
					ff.sc("err454515");
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
		ff.toast("已停止");
		ff.setTitle("已停止服务");
		
	}
	
	void actServer_btn_rebootServer()
	{
		stopCamera();
		closeServer();
		actServer_btn_stopServer();
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
