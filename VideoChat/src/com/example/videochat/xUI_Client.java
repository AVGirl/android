package com.example.videochat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.MethodNotSupportedException;

import android.app.ActivityManager;
import android.app.AlertDialog;
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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import data.icfg;
import mylib.DHK;
import mylib.FF;
import mylib.MySocket;
import mylib.SendHead;
import mylib.cfg;
import mylib.command;


public class xUI_Client extends ActionBarActivity implements android.view.View.OnClickListener,mylib.MySocket.OnSocketReceivedData,mylib.MySocket.SocketError{

	ImageView img_preview;
	FF ff;
	//XJCS xjcs=new XJCS();
	
	byte[]recvBuffer=new byte[1024*1024*5];
	
	 MySocket csoc=new MySocket();
	SendHead gsh=new SendHead();
	DHK dhk;
	private ClientHandler chan;
	Button actClient_btn_connect;
	LinearLayout ll_control;
	private Bitmap Gbmp=null;
	boolean bAuto_gc=false;
	Object sync_Gbmp = new Object();
	
	void bindView()
	{
		img_preview=(ImageView) this.findViewById(R.id.actClient_img_preview);
		actClient_btn_connect=(Button) findViewById(R.id.actClient_btn_connect);
		ll_control=(LinearLayout) this.findViewById(R.id.actClient_ll_control);

		
		
		if(cfg.getInt(icfg.menuClient_videoSize)==icfg.menuClient_videoSize_CENTER)menuClient_videoSize_raw();else menuClient_videoSize_fullScreen();

		
		bAuto_gc=cfg.getBoolean(icfg.bAuto_gc,true);

		
		
		ll_control.bringToFront();
	} 
	void resetAnimation()
	{
		LinearLayout ll= (LinearLayout) this.findViewById(R.id.actClient_LinearLayout);
		ll.removeView(img_preview);
		img_preview=new ImageView(this);  
		img_preview.setBackgroundResource(R.drawable.girl);
		ll.addView(img_preview);
	}
	
	boolean isConnected()
	{
		boolean b=true;
		if(csoc.socket==null)b=false;
		if(!b)
		{
			ff.sc("未连接");
			return b;
		}
		if(csoc.isIOException==true)b=false;
		if(!b)
		{
			ff.sc("未连接");
			return b;
		}
		return b;
		
	}
	 


	void img_preview_rotate_back()
	{
		//if (1 > 0){return;};
		Animation animation = AnimationUtils.loadAnimation(this, R.animator.img_preview_rotate_back);  
		 img_preview.startAnimation(animation);
		 DisplayMetrics dm = getResources().getDisplayMetrics();
	/*	 int  width =cfg.getInt_fromStr(icfg.xjcsPreviewWidth,dm.widthPixels);
		 int height =cfg.getInt_fromStr(icfg.xjcsPreviewHeight,dm.widthPixels);
		 */
		// ff.sc("this.getActionBar().getHeight()=="+this.getActionBar().getHeight());
		 int  width =dm.widthPixels;
		// int height =dm.heightPixels;//-this.getActionBar().getHeight()*2;
		 int height =dm.heightPixels-(this.getActionBar().getHeight()+tools.getStatusBarHeight(this));
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
		 DisplayMetrics dm = getResources().getDisplayMetrics();
		 int  width =dm.widthPixels;
		 int height =dm.heightPixels-(this.getActionBar().getHeight()+tools.getStatusBarHeight(this));
		 LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) img_preview.getLayoutParams();

		params.height=height;//设置当前控件布局的高度 
		params.width=height;//设置当前控件布局的高度 
		params.leftMargin=(width-height)/2;  
	//	params.leftMargin=0;
		params.topMargin=0;
		  
		 img_preview.setLayoutParams(params);
	}
	
 
	public  static Context  mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_client);
		ff=new FF(this,this);
		mContext=this;
		dhk=new DHK(this);
		chan=new ClientHandler ();
		chan.sendEmptyMessage(chan.msgLoop);

		bindView();
	//	ActManager.getActManager().pushActivity(this);
		
		//img_preview_rotate();
		
		
		if(connectServer(cfg.getString(icfg.menuClient_inputIp,null))==false)menuClient_inputIp();
		//do_actClient_btn_connect();
		//try {Thread.sleep(300);} catch (Exception e) {}
	//	menuClient_setting();
		
		//chan.sendEmptyMessageDelayed(chan.msgImg_preview_rotate, 500);
	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//dhk.dhk("aaaaaaaa");
		 boolean b=isConnected();
		 boolean b5;
		 
		 
		 
		 menu.findItem(R.id.menuClient_videoSize_raw).setEnabled(b);
		 menu.findItem(R.id.menuClient_videoSize_fullScreen).setEnabled(b);
		 
		 
		 
		 
		 menu.findItem(R.id.menuClient_ML_CLOSEDISPLAY).setEnabled(b);
		 menu.findItem(R.id.menuClient_ML_VIDEOMONITORING_SERVER_LOCK_UI).setEnabled(b);
		 menu.findItem(R.id.menuClient_ML_VIDEOMONITORING_SERVER_UNLOCK_UI).setEnabled(b);

		 
		 menu.findItem(R.id.menuClient_sendPowerShutdown).setEnabled(b);
		 menu.findItem(R.id.menuClient_shutdownServer).setEnabled(b);
		 menu.findItem(R.id.menuClient_setting).setEnabled(b);
		 
		 
		 int i=cfg.getInt(icfg.currentCameraId,0);
		 MenuItem mi= menu.findItem(R.id.menuClient_openBackCamera).setEnabled(b);
		 mi.setChecked(false);
		 if(i==0)mi.setChecked(true);
		  mi= menu.findItem(R.id.menuClient_openFrontCamera).setEnabled(b);
		 mi.setChecked(false);
		 if(i==1)mi.setChecked(true);
		 
		 
		  mi= menu.findItem(R.id.menuClient_Auto_gc).setEnabled(b);
		 b5=cfg.getBoolean(icfg.bAuto_gc,true);
		 mi.setChecked(b5);

		 
		 if(b)
		 {
			 gsh.clear();
			 gsh.cmd=command.ML_PHONE_GetBatteryLevel;
			SendHead sh= csoc.rsend_sh(gsh);
			if(sh!=null) menu.findItem(R.id.menuClient_serverCurrentBatteryLevel).setTitle("服务:电量:"+sh.csB+"%");
		 }
		 
		
		
		 
		 //menuClient_serverCurrentBatteryLevel
		 
		return true;
		
		
	}
	void menuClient_setting()
	{
		String s=getPreviewSize2();		
		cfg.putString(icfg.xjcsPreviewSizeData,s);
		Intent yt=new Intent(this,xUI_ClientXJCS.class);
		startActivityForResult(yt,123);
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==123)
		{
			openCamera(cfg.getInt(icfg.currentCameraId,0));
		}
	}
	
	boolean setCameraParameter(int cameraid)
	{
		XJCS xjcs1 =icfg.read_xjcs(cameraid);
		csoc.sendCmd(command.ML_VIDEOMONITORING_SET_CAMERAID, cameraid, 0, 0);
		csoc.sendCmd(command.ML_VIDEOMONITORING_IMAGE_QUALITY, xjcs1.imageQuality, 0, 0);
		csoc.sendCmd(command.ML_VIDEOMONITORING_REFRESH_RATE, xjcs1.refreshRate, 0, 0);
		return csoc.sendCmd(command.ML_VIDEOMONITORING_SETPREVIEWSIZE, xjcs1.iPreviewWidth, xjcs1.iPreviewHeight, 0);
	}
	
	
	
	void openCamera(int cid)
	{
		//必须停止相机
		csoc.rsend(command.ML_VIDEOMONITORING_CAMERA_STOP);
		
		cfg.putInt(icfg.currentCameraId, cid);
		setCameraParameter(cid);
		csoc.sendCmd(command.ML_VIDEOMONITORING_CAMERA_START);
		if(cid==1)img_preview_rotate_front_();
		if(cid==0)img_preview_rotate_back();
		if(cfg.getInt(icfg.menuClient_videoSize)==icfg.menuClient_videoSize_CENTER)menuClient_videoSize_raw();else menuClient_videoSize_fullScreen();
		
	}


	@Override
	public void onBackPressed() 
	{
		csoc.shutdownSocket();
		recvBuffer=null;
		this.finish();
	}

	void menuClient_videoSize_raw()
	{ 
		//cfg.putInt(icfg.img_previewCaleType, icfg.cimg_previewCaleType_center);
		img_preview.setScaleType(ScaleType.CENTER);
		cfg.putInt(icfg.menuClient_videoSize, icfg.menuClient_videoSize_CENTER);
		
	}
	
	void menuClient_videoSize_fullScreen()
	{
		img_preview.setScaleType(ScaleType.FIT_CENTER);
		cfg.putInt(icfg.menuClient_videoSize, icfg.menuClient_videoSize_FIT_CENTER);
	}
	
	
	void menuItemSelected(int id)
	{
		switch (id) {
		case R.id.menuClient_ConnectServer:connectServer(null);break;		
		//case R.id.actClient_btn_startPreview:do_actClient_btn_startPreview();break;
		case R.id.menuClient_openBackCamera:openCamera(0);break;
		case R.id.menuClient_openFrontCamera:openCamera(1);break;
		
		case R.id.menuClient_shutdownServer:csoc.sendCmd(command.ML_EXITPROCESS);break;
		case R.id.menuClient_sendPowerShutdown:csoc.sendCmd(command.ML_POWER_Shutdown);break;
		case R.id.menuClient_setting:menuClient_setting();break;
		//case R.id.menuClient_serverReboot:msoc.sendCmd(command.ML_SERVER_REBOOT); break;
		case R.id.menuClient_videoSize_raw:menuClient_videoSize_raw(); break;
		case R.id.menuClient_videoSize_fullScreen:menuClient_videoSize_fullScreen(); break;
		case R.id.menuClient_disconnect:csoc.sendCmd(command.ML_VIDEOMONITORING_RESTART_SERVER); break;
		//case R.id.menuClient_serverReboot:msoc.sendCmd(command.ML_VIDEOMONITORING_RESTART_SERVER); break;
		case R.id.menuClient_ML_CLOSEDISPLAY:csoc.sendCmd(command.ML_CLOSEDISPLAY); break;
		case R.id.menuClient_ML_VIDEOMONITORING_SERVER_LOCK_UI:csoc.sendCmd(command.ML_VIDEOMONITORING_SERVER_LOCK_UI); break;
		case R.id.menuClient_ML_VIDEOMONITORING_SERVER_UNLOCK_UI:csoc.sendCmd(command.ML_VIDEOMONITORING_SERVER_UNLOCK_UI); break;
		case R.id.menuClient_test:menuClient_test___0a0a0aaaaaaaaaaaaaaaaaaaaaaaaaaaa0a();break;
		case R.id.menuClient_inputIp:menuClient_inputIp();break;
		
		
		case R.id.menuClient_Auto_gc:
		{
			bAuto_gc=bAuto_gc?false:true;
			cfg.putBoolean(icfg.bAuto_gc, bAuto_gc);
		}break;
		//case R.id.menuClient_options:menuClient_options();break;
		

	
		}
		
	}

/*	private void menuClient_options() 
	{
		String [] menus=new String[2];
		menus[0]="手动输入目标ip";
		menus[1]="自动释放内存";
		csoc.rsend(command.ML_VIDEOMONITORING_CAMERA_STOP);
		int sel= dhk.singleChoiceDialog(menus, 0, null, null);
		if(sel==-1)
		{
			csoc.sendCmd(command.ML_VIDEOMONITORING_CAMERA_START);
			return;
		}
		
		if(sel==0)	menuClient_inputIp();
		if(sel==1)bAuto_gc=bAuto_gc?false:true;

		
		
		csoc.sendCmd(command.ML_VIDEOMONITORING_CAMERA_START);
		
	}*/
	private void menuClient_inputIp() 
	{
		if(csoc!=null)	csoc.shutdownSocket();
		
		String iip= cfg.getString(icfg.menuClient_inputIp,"");
		String s= dhk.inputDialog(iip, null, "wifi热点为本机则可用", null, null);
		if(s==null)return;
		cfg.putString(icfg.menuClient_inputIp, s);
		connectServer(s);
		
	}
	void menuClient_test___0a0a0aaaaaaaaaaaaaaaaaaaaaaaaaaaa0a()
	{
		if (1 > 0){return;};
		//menuClient_setting();
		String s=getPreviewSize2();
		if(s==null)
		{
			ff.sc("getPreviewSize2()==null");
		}else ff.sc("getPreviewSize2==",s.length());
		
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		menuItemSelected(item.getItemId());
		return super.onOptionsItemSelected(item);
	}
	
	void abc(boolean show)
	{
		
	}

	boolean  connectServer(String ip)
	{
		if(csoc.socket!=null){if(csoc.isIOException==false)return true;}
		//ff.toast("开始连接...");
	//	Socket soc=csoc.connectToServer("192.168.43.1",1234);
		if(ip==null)ip=new String("192.168.43.1");
		Socket soc=csoc.connectToServer(ip,1234);
		
		  
		if(soc==null)
		{
			ff.xxk("连接失败");
			ll_control.setVisibility(View.VISIBLE);
			return false;
		}
		
		csoc.toNetMsg=false;
		csoc.initSocket(soc);
		csoc.setOnRecvSendHead(this);
		csoc.setSocketError(this);
		csoc.startRecv(recvBuffer);
		ff.setTitle("已连接 ");
		return true;
		
		
	}
	
	

/*	@Override
	public void onBackPressed() 
	{
		Intent yt=new Intent(this, xUI_Server.class);
		startActivity(yt);
	}*/
/*
	void do_actClient_btn_startPreview()
	{
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_START_PREVIEW;
		
		if(msoc.send(gsh))actClient_btn_startPreview.setVisibility(View.GONE);
	}*/
	
	void actClient_btn_frontCamera()
	{
		//ff.sc(getPreviewSize());
		 
		//if (1 > 0){return;};
		 
		
		//ff.toast("ddddddddd");
		//ff.sc("	img_preview_rotate_front_();");
		//img_preview_rotate_front_();
		openCamera(1);
		
	} 
	
	void actClient_btn_backCamera()
	{
		//img_preview_rotate_back();
		 openCamera(0);
		
	}
	
	
	void temp__0x0x0x0x0x0x00xx0()
	{
		 gsh.clear();
		 gsh.cmd=command.ML_PHONE_GetBatteryLevel;
		SendHead sh= csoc.rsend_sh(gsh);
		ff.setTitle(sh.csB);
		//ff.toast( sh.csB);
		//if(sh!=null) menu.findItem(R.id.).setTitle("电量："+sh.csB+"%");
		 
		
/*		ff.toast(getPreviewSize2());
		
		if (1 > 0){return;};
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_SERVER_PAUSE;
		ff.setTitle("等待回复");
		//、、ff.xxk("ddddd");
		//if (1 > 0){return;};
		ff.sc("send2,,,,",csoc.rsend(gsh,null,0,null));*/
		
	}
	
	
	@Override
	public void onClick(View v) {
		
		
		if(v.getId()== R.id.actClient_btn_disconnect)
		{
			temp__0x0x0x0x0x0x00xx0();
			return;
		}
		 
		//ff.toast("	public void onClick(View v) {");
		switch (v.getId()) 
		{ 
   
		case R.id.actClient_btn_connect:connectServer(null);	break;
		case R.id.actClient_btn_test: tools.uninstallApk(this,this.getPackageName());
		case R.id.actClient_btn_disconnect:menuItemSelected(R.id.menuClient_disconnect);break;
		case R.id.actClient_btn_backCamera:menuItemSelected(R.id.menuClient_openBackCamera);break;
		case R.id.actClient_btn_frontCamera:menuItemSelected(R.id.menuClient_openFrontCamera);break;
		case R.id.actClient_btn_hideControl:ll_control.setVisibility(View.GONE);;break;
		
	/*	case R.id.actClient_btn_startPreview:
		{
			actClient_btn_startPreview.setVisibility(View.GONE);
			do_actClient_btn_startPreview();
			break;
			
		}*/
		
		
		}
		
	 
	 
	
		
	} 
//---------------------------------------------------------------------------------------
	
	@Override
	public boolean onSocketReceivedData(SendHead sh, byte[] data,MySocket ___sssss) 
	{
		switch (sh.cmd) {  
		case command.ML_FILE_Picture:
			{
				
				//chan.setImgPreviewShowBitmap(BitmapFactory.decodeByteArray(data, 0, sh.size));
				//synchronized (sync_Gbmp) 
			//	{
		/*		Message msg11=Message.obtain();
				msg11.obj=data;
				msg11.what=chan.msgImgPreviewShowBitmap;
				msg11.arg1=sh.size;
				chan.sendMessage(msg11);*/
					 Gbmp=BitmapFactory.decodeByteArray(data, 0, sh.size);
					 chan.sendEmptyMessage(chan.msgImgPreviewShowBitmap);
			//	}
			/*	synchronized (sync_Gbmp) 
				{
					try {
						sync_Gbmp.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	*/
			}break;
			
	 
		
		
		default:
			break;
		}
		
			return true;
	}
	
//---------------------------------------------------------------------------------------
	

	void do_ML_FILE_Picture(SendHead sh, byte[] data) 
	{
			//Message msg=new Message();              
		
			/*msg.what=chan.msgImgPreviewShowBitmap;
			msg.obj=bmp;
			chan.sendMessage(msg);
			data=null;        */  
			//System.gc();
			
			
	}
/*	
	String getPreviewSize()
	{
	
		if(!isConnect())return null;
		
		msoc.recv_pause();
		
		SendHead sh=new SendHead();
		gsh.cmd=command.ML_VIDEOMONITORING_getSupportedPreviewSizes;
		msoc.send(gsh);
		 sh= msoc.recvHead();	
		 
		 
		  if(sh.cmd!=command.ML_VIDEOMONITORING_getSupportedPreviewSizes)
		  {
			  ff.sc(" if(sh.cmd!=command.ML_VIDEOMONITORING_getSupportedPreviewSizes)",sh.cmd);
			  return null;
			  
		  }
		  byte[] backData=new byte[sh.size];    
		  
		  msoc.recv(backData, sh.size);
		  
		msoc.recv_continue();
		return new String(backData);
		
	}
	*/
	String getPreviewSize2()
	{ 
		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_GetSupportedPreviewSizes;
		byte[] backData=new byte[1000];   
		gsh=csoc.rsend(gsh,null,0,backData);
		
		//ff.sc("getPreviewSize*----",gsh.size);
		if(gsh==null || gsh.size==0)
		{
			ff.sc("if(gsh.size==0)	");
			return null;
		}
	//	ff.sc("new Stri---",new String(backData, 0, gsh.size));
		return new String(backData, 0, gsh.size);
		
	}
	
/*	void setPreviewSize()
	{
		Message msg=new Message();
		msg.what=ClientHandler.msgSetPreviewSize;
		msg.obj=getPreviewSize();
		chan.sendMessage(msg);

		
	}*/
	

	
	class ClientHandler extends Handler
	{
		
		//public static final int msgSetPreviewSize=300;
		public static final int msgImgPreviewShowBitmap=301;
		public static final int msgImg_preview_rotate=302;
		public static final int msgSetTitle=304;
		public static final int msgSocketError=305;
		
		public static final int msgLoop=306;
		
		
		
		
		
		public static final int msgTest=110;
		
		public  void setTitle(String s)
		{
			Message msg=new Message();
			msg.obj=s;
			msg.what=msgSetTitle;
			this.sendMessage(msg);
		}

		
		public void setImgPreviewShowBitmap(Object o)
		{
			Message msg5=new Message();
			msg5.obj=o;
			msg5.what=msgImgPreviewShowBitmap;
			this.sendMessage(msg5);
		}
	
		
		
		void doMsgSocketError()
		{
			ff.setTitle("doMsgSocketError");
			new AlertDialog.Builder(xUI_Client.this).setMessage("已断开连接").show();

			
		/*	int q= dhk.dhk("已断开连接", null, "重新连接", "取消");
			if(q==dhk.Positive_BTN)
			{
				connectServer();
			}*/
		/*	img_preview.setVisibility(View.GONE);
			ll_control.setVisibility(View.VISIBLE);
			ll_control.bringToFront();
			ff.setTitle("doMsgSocketError");*/
			
		}
		
		
/*	case msgImgPreviewShowBitmap:{
		
		img_preview.setImageBitmap(Gbmp);
		if(Gbmp != null && !Gbmp.isRecycled()){  
			Gbmp.recycle();  
			Gbmp = null;  
		}  
		Gbmp = null;
		if(bAuto_gc)System.gc();
		synchronized (sync_Gbmp) {
			sync_Gbmp.notify();
			System.gc();
		}
		//ff.sc("w=="+((Bitmap) msg.obj).getWidth(),((Bitmap) msg.obj).getHeight());
		//img_preview.setImageBitmap((Bitmap) msg.obj);
		//msg.obj==null;
		break;
		}*/
		
		/*public void bitloop()
		{
			while(true)
			{
			//	dhk.dhk("f");
				try {Thread.sleep(1);} catch (Exception e) {}
				if(Gbmp!=null)
				{
					img_preview.setImageBitmap(Gbmp);
					Gbmp = null;
					if(bAuto_gc)System.gc();
				}
			}
			
		}*/
		
		@Override
		public void handleMessage(Message msg) 
		{
			
			switch (msg.what) {
			//case msgSetPreviewSize:setPreviewSize((String) msg.obj);break;
			
			case msgSocketError:doMsgSocketError();break;
			case msgSetTitle:ff.setTitle((String) msg.obj);break;
			
			case msgImgPreviewShowBitmap:{
			img_preview.setImageBitmap(Gbmp);
			Gbmp = null;
			if(bAuto_gc)System.gc();
			}break;
			
			
			/*
			case msgImgPreviewShowBitmap:{
				
				img_preview.setImageBitmap(BitmapFactory.decodeByteArray((byte[])msg.obj, 0,msg.arg1));
				Gbmp = null;
				if(bAuto_gc)System.gc();
				}break;
				*/
				
			
			
			
			
			//case msgLoop:bitloop();break;
		/*	case msgImgPreviewShowBitmap:
			{
				img_preview.setImageBitmap((Bitmap) msg.obj);
				if(bAuto_gc)System.gc();
			}*/
			
			
			//case msgImg_preview_rotate:img_preview_rotate_back();break;
			//case msgTest:ff.toast("msgTest");break;
			
			
			
			}
			
		}
		
	}
	
void xjStartPreview()
{
	gsh.clear();
	gsh.cmd=command.ML_VIDEOMONITORING_START_PREVIEW;
	csoc.send(gsh);
	
}
@Override
public void socketError(short what,Socket s1)  
{
	ff.sc("socketError",what);
	chan.sendEmptyMessage(chan.msgSocketError);

}

	
}
