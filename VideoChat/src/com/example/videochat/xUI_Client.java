package com.example.videochat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import data.icfg;
import mylib.DHK;
import mylib.FF;
import mylib.MySocket;
import mylib.OnRecvSendHead;
import mylib.SendHead;
import mylib.cfg;
import mylib.command;

public class xUI_Client extends ActionBarActivity implements android.view.View.OnClickListener,OnRecvSendHead{

	ImageView img_preview;
	FF ff;
	XJCS xjcs=new XJCS();
	
	static final byte rotate_back=1;
	static final byte rotate_front=2;
	static final byte rotate_unknow=3;
	byte bImgPreview_rotateState=rotate_unknow;
	
	
	
	byte[]ClientBuffer=new byte[1024*1024*5];
	 MySocket msoc=new MySocket();
	SendHead gsh=new SendHead();
	DHK dhk;
	private ClientHandler chan;
	Button actClient_btn_connect,actClient_btn_startPreview;
	
	
	void bindView()
	{
		img_preview=(ImageView) this.findViewById(R.id.actClient_img_preview);
		actClient_btn_connect=(Button) findViewById(R.id.actClient_btn_connect);
		actClient_btn_startPreview=(Button) findViewById(R.id.actClient_btn_startPreview);
	} 
	void resetAnimation()
	{
		LinearLayout ll= (LinearLayout) this.findViewById(R.id.actClient_LinearLayout);
		ll.removeView(img_preview);
		img_preview=new ImageView(this);  
		img_preview.setBackgroundResource(R.drawable.girl);
		ll.addView(img_preview);
	}
	
	 


	void img_preview_rotate_back()
	{
		//if (1 > 0){return;};
		Animation animation = AnimationUtils.loadAnimation(this, R.animator.img_preview_rotate_back);  
		 img_preview.startAnimation(animation);
		bImgPreview_rotateState=rotate_back;
		 DisplayMetrics dm = getResources().getDisplayMetrics();
	/*	 int  width =cfg.getInt_fromStr(icfg.xjcsPreviewWidth,dm.widthPixels);
		 int height =cfg.getInt_fromStr(icfg.xjcsPreviewHeight,dm.widthPixels);
		 */
		// ff.sc("this.getActionBar().getHeight()=="+this.getActionBar().getHeight());
		 int  width =dm.widthPixels;
		 int height =dm.heightPixels;//-this.getActionBar().getHeight()*2;
		 LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) img_preview.getLayoutParams();
		//ff.sc("params.height=="+params.height+"       params.width=="+params.width);
		//ff.toast("params.height=="+params.height+"       params.width=="+params.width);
	/*	params.height=width;//设置当前控件布局的高度
		params.width=height;//设置当前控件布局的高度 	
		params.leftMargin=(width-height)/2;
		
		*/
			params.height=height;//设置当前控件布局的高度 
			params.width=height;//设置当前控件布局的高度 
			params.leftMargin=(width-height)/2;  
		//	params.leftMargin=0;
			params.topMargin=0;
			   
			
			 img_preview.setLayoutParams(params);
	} 

	
	void img_preview_rotate_front_()
	{
		//if (1 > 0){return;};
		Animation animation = AnimationUtils.loadAnimation(this, R.animator.img_preview_rotate_front);  
		 img_preview.startAnimation(animation);
		bImgPreview_rotateState=rotate_front;  
		 DisplayMetrics dm = getResources().getDisplayMetrics();
		 int  width =dm.widthPixels;
		 int height =dm.heightPixels;//-this.getActionBar().getHeight()*2;
		 LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) img_preview.getLayoutParams();

		params.height=height;//设置当前控件布局的高度 
		params.width=height;//设置当前控件布局的高度 
		params.leftMargin=(width-height)/2;  
	//	params.leftMargin=0;
		params.topMargin=0;
		  
		
		 img_preview.setLayoutParams(params);
	}
	
 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_client);
		ff=new FF(this);
		dhk=new DHK(this);
		chan=new ClientHandler ();
		bindView();
	//	ActManager.getActManager().pushActivity(this);
		
		//img_preview_rotate();
		
		do_actClient_btn_connect();
		//try {Thread.sleep(300);} catch (Exception e) {}
	//	menuClient_setting();
		
		//chan.sendEmptyMessageDelayed(chan.msgImg_preview_rotate, 500);
	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.client_setting, menu);
		return true;
	}
	
	void menuClient_setting()
	{
		cfg.putString(icfg.xjcsPreviewSizeData,getPreviewSize());
		Intent yt=new Intent(this,xUI_ClientXJCS.class);
		startActivityForResult(yt,123);
	}

	void setCameraParameter()
	{
		
		//mysoc2.xsend_ML_SERVER_SOCKET_STOP();
		
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_IMAGE_QUALITY;
		gsh.csA=cfg.getInt_fromStr(icfg.xjcsImageQuality,50);
		msoc.send(gsh);
		
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_REFRESH_RATE;
		gsh.csA=cfg.getInt_fromStr(icfg.xjcsRefreshRate,100);
		msoc.send(gsh);
		
	
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_SETPREVIEWSIZE;
		gsh.csA=cfg.getInt_fromStr(icfg.xjcsPreviewWidth,0);
		gsh.csB=cfg.getInt_fromStr(icfg.xjcsPreviewHeight,0);
		xjcs.iPreviewWidth=gsh.csA;
		xjcs.iPreviewHeight=gsh.csB;
		
		//String ee="setCameraParameter=="+gsh.csA+"  "+gsh.csB;
		//ff.toast(ee);
		
		if(	gsh.csA<0)return;
		msoc.send(gsh);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode==123)setCameraParameter();
	}
	
	void openCamera(boolean front)
	{
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_OPEN_BACK_CAMERA;
		if(front)gsh.cmd=command.ML_VIDEOMONITORING_OPEN_FRONT_CAMERA;
		msoc.send(gsh);
		if(front)img_preview_rotate_front_();
		if(!front)img_preview_rotate_back();
		
		
	}


	@Override
	public void onBackPressed() 
	{
		msoc.shutdownSocket();
		ClientBuffer=null;
		this.finish();
		
	}

	
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
		case R.id.actClient_btn_connect:do_actClient_btn_connect();break;		
		case R.id.actClient_btn_startPreview:do_actClient_btn_startPreview();break;
		case R.id.menuClient_openBackCamera:openCamera(false);break;
		case R.id.menuClient_openFrontCamera:openCamera(true);break;
		case R.id.menuClient_shutdownServer:msoc.sendCmd(command.ML_EXITPROCESS);break;
		case R.id.menuClient_sendPowerShutdown:msoc.sendCmd(command.ML_POWER_Shutdown);break;
		case R.id.menuClient_setting:menuClient_setting();break;
		case R.id.menuClient_serverReboot:msoc.sendCmd(command.ML_SERVER_REBOOT); break;
		
		
		
		
		}
		
	
		
		return super.onOptionsItemSelected(item);
	}
	
	

	
	boolean do_actClient_btn_connect()
	{
				if(msoc.socket!=null){if(msoc.isIOException==false)return true;}
			
		//ff.toast("开始连接...");
		
		Socket soc=msoc.connectToServer("192.168.43.1",1234);
		if(soc==null)
		{
			ff.xxk("连接失败");
			return false;
		}
		
		msoc.toNetMsg=false;
		msoc.initSocket(soc);
		msoc.setOnRecvSendHead(this);
		msoc.startRecvSendHead_buffer(ClientBuffer);
		setCameraParameter();
		ff.toast("已连接 ");
		actClient_btn_connect.setVisibility(View.GONE);
		
		return true;
		
	}

/*	@Override
	public void onBackPressed() 
	{
		Intent yt=new Intent(this, xUI_Server.class);
		startActivity(yt);
	}*/

	void do_actClient_btn_startPreview()
	{
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_START_PREVIEW;
		
		if(msoc.send(gsh))actClient_btn_startPreview.setVisibility(View.GONE);
	}
	
	void actClient_btn_frontCamera()
	{
		//ff.sc(getPreviewSize());
		 
		//if (1 > 0){return;};
		 
		
		//ff.toast("ddddddddd");
		//ff.sc("	img_preview_rotate_front_();");
		//img_preview_rotate_front_();
		openCamera(true);
		
	} 
	
	void actClient_btn_backCamera()
	{
		//img_preview_rotate_back();
		 openCamera(false);
		
	}
	@Override
	public void onClick(View v) {
		
		//ff.toast("	public void onClick(View v) {");
		switch (v.getId()) 
		{ 
		
		case R.id.actClient_btn_backCamera:actClient_btn_backCamera();break;
		case R.id.actClient_btn_frontCamera:actClient_btn_frontCamera();break;
		
		
		case R.id.actClient_btn_connect:
		{
			if(do_actClient_btn_connect())actClient_btn_connect.setVisibility(View.GONE);
			break;
		}
		
		case R.id.actClient_btn_test:{
			 tools.uninstallApk(this,this.getPackageName());
		}
		
		
		case R.id.actClient_btn_startPreview:
		{
			actClient_btn_startPreview.setVisibility(View.GONE);
			do_actClient_btn_startPreview();
			break;
			
		}
		
		
		}
		
	 
	 
	
		
	} 
//---------------------------------------------------------------------------------------
	@Override
	public boolean onRecvSendHead(SendHead sh, byte[] data) 
	{
		  
	//、、if (1>0){return true;} 
//ff.sc("onRecvSendHead");   
		//if(sh.cmd==command.ML_FILE_Picture && sh.csA==cmdcs.ML_CS_FILE_SEND)
		switch (sh.cmd) {  
		case command.ML_FILE_Picture:do_ML_FILE_Picture(sh, data);break;
		case command.ML_CLIENT_SOCKET_STOP:{if (1>0){return false;}  /* return false;*/}break;
	 
		
		default:
			break;
		}
		
			return true;
	}
	
//---------------------------------------------------------------------------------------
	

	void do_ML_FILE_Picture(SendHead sh, byte[] data) 
	{
			Message msg=new Message();                                                                                                
			Bitmap bmp=BitmapFactory.decodeByteArray(data, 0, sh.size);
		
			msg.what=chan.msgImgPreviewShowBitmap;
			msg.obj=bmp;
			chan.sendMessage(msg);
			data=null;          
			//System.gc();
		
	}
	
	String getPreviewSize()
	{
		if(msoc.socket==null)
		{
			ff.sc("尚没连接");
			return "";
		}
		
		msoc.xsend_ML_CLIENT_SOCKET_STOP();
		
		SendHead sh=new SendHead();
		gsh.cmd=command.ML_VIDEOMONITORING_getSupportedPreviewSizes;
		msoc.send(gsh);
		 sh= msoc.recvHead();	
		 
		 
		  if(sh.cmd!=command.ML_VIDEOMONITORING_getSupportedPreviewSizes)return "";
		  byte[] backData=new byte[sh.size];    
		  msoc.recv(backData, sh.size);
		  
		msoc.continueRecvSendHead_buffer();
		return new String(backData);
		
	}
	
	void setPreviewSize()
	{
		/*mysoc2.xsend_ML_CLIENT_SOCKET_STOP();
		SendHead sh=new SendHead();
		gsh.cmd=command.ML_VIDEOMONITORING_getSupportedPreviewSizes;
		mysoc2.send(gsh);
		  sh= mysoc2.recvHead();
			
		  if(sh.cmd!=command.ML_VIDEOMONITORING_getSupportedPreviewSizes)return;
		  byte[] backData=new byte[sh.size];
		  mysoc2.recv(backData, sh.size);
		String jg=new String(backData);
		ff.sc(jg);*/
		Message msg=new Message();
		msg.what=ClientHandler.msgSetPreviewSize;
		msg.obj=getPreviewSize();
		chan.sendMessage(msg);

		
	}
	

	
	class ClientHandler extends Handler
	{
		
		public static final int msgSetPreviewSize=300;
		public static final int msgImgPreviewShowBitmap=301;
		public static final int msgImg_preview_rotate=302;
		
		public static final int msgTest=110;
		
		
		
		void setPreviewSize(String jg)
		{
			
			String sizes[]= jg.split(";");
			if(sizes==null){ff.sc("sizes==null");return;}
			
			String ws[]=new String[sizes.length];
			String hs[]=new String[sizes.length];
			
			for (int i = 0; i < sizes.length; i++) 
			{
				String strs[]= sizes[i].split("x");
				ws[i]=strs[0];
				hs[i]=strs[1];
			}
			
			
			int index=dhk.singleChoiceDialog(sizes, 0, "", "Ok");
			if(index==-1)return;
			
			gsh.clear();
			gsh.cmd=command.ML_VIDEOMONITORING_SETWH;
			gsh.csA=Integer.valueOf(ws[index]);
			gsh.csB=Integer.valueOf(hs[index]);
			msoc.send(gsh);
			ff.sc(ws[index]);
			ff.sc(hs[index]);
			
		}
		@Override
		public void handleMessage(Message msg) 
		{
			
			switch (msg.what) {
			case msgSetPreviewSize:setPreviewSize((String) msg.obj);break;
			case msgImgPreviewShowBitmap:{
				//ff.sc("w=="+((Bitmap) msg.obj).getWidth(),((Bitmap) msg.obj).getHeight());
				img_preview.setImageBitmap((Bitmap) msg.obj);
				break;
				}
			//case msgImg_preview_rotate:img_preview_rotate_back();break;
			//case msgTest:ff.toast("msgTest");break;
			
			
			
			}
			
		}
		
	}
	
void xjStartPreview()
{
	gsh.clear();
	gsh.cmd=command.ML_VIDEOMONITORING_START_PREVIEW;
	msoc.send(gsh);
	
}

	
}
