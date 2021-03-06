package com.example.videochat;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;
import mylib.FF;

public class tools 
{
	public static void uninstallApk(Context context, String packageName) {    
	    Uri uri = Uri.parse("package:" + packageName);    
	    Intent intent = new Intent(Intent.ACTION_DELETE, uri);    
	    context.startActivity(intent);    
	}  
	
		public static int getBatteryLevel(Context con)
		{
			Intent batteryInfoIntent =con.getApplicationContext() .registerReceiver( null ,  new IntentFilter( Intent.ACTION_BATTERY_CHANGED ) ) ;  
		   return batteryInfoIntent.getIntExtra( "level" , 0 );
		}
	
	
	
	public static int getStatusBarHeight(Context context) {
	       int result = 0;
	       int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
	       if (resourceId > 0) {
	           result = context.getResources().getDimensionPixelSize(resourceId);
	       }
	       return result;
	   }
	
	
	
	public static boolean screen_isBright(Context c)
	{
		PowerManager powerManager = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
		return powerManager.isScreenOn();
		
	}
	
	
	
	
	
	
	




void temp__0x0x0x0x0x()
{

		
}


   
	
	public static void screen_unlock(Context context)
	{
			  //屏锁管理器  
		  KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);  
		  KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");  
		  //解锁  
		  kl.disableKeyguard();  
	}
	@SuppressWarnings("deprecation")
	public static void screen_wakeUp(Context context){  

		  //获取电源管理器对象  
		  PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);  
		  //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
		 PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");  
		  //点亮屏幕  
		  //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag  
		// PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK,"bright");  
		  //点
		  
		  
		  wl.acquire();  
		  //释放  
		  wl.release();  
		}  
	
	//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
}


class CloseScreen{
DevicePolicyManager policyManager;
ComponentName componentName;
Context mContext;
Activity mActivity;


private void systemLock() {
    if (policyManager.isAdminActive(componentName)) {
        Window localWindow =mActivity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        localLayoutParams.screenBrightness = 0.05F;
        localWindow.setAttributes(localLayoutParams);
        policyManager.lockNow();
    }
}
 CloseScreen(Context con,Activity act)
{
	 mContext=con;
	 mActivity=act;
	 policyManager = (DevicePolicyManager)mContext.getSystemService(mContext.DEVICE_POLICY_SERVICE);
     componentName = new ComponentName(mContext, MyDeviceAdminReceiver.class);

}
 
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



 public void closeScreen()
 {
     if (!policyManager.isAdminActive(componentName)) {
         goSetActivity();
      //   FF.sc("   goSetActivity();");
     } else {
         systemLock();
       //  FF.sc("systemLock();");
     }
 }
 

private void goSetActivity() 
{
    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
    mActivity.startActivityForResult(intent, 1);
}
	

/*@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (1 == requestCode) {
        if (RESULT_OK == resultCode) {
           // systemLock();
        } else if (RESULT_CANCELED == resultCode) {
            //用户拒绝激活
        }
    }
}*/


}




class ClassIP {

	public String getLocalIpAddress() {
		try {
			String ipv4;
			ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface ni : nilist) {
				ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
				for (InetAddress address : ialist) {
					if (!address.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ipv4 = address.getHostAddress())) {
						return ipv4;
					}
				}

			}

		} catch (SocketException ex) {
		}
		return null;
	}

	public String WifiIP = null;
	public String mobileIP = null;
	Context mContext;

	ClassIP(Context con) {
		mContext = con;
	}

	public String intToIp(int ipInt) {
		StringBuilder sb = new StringBuilder();
		sb.append(ipInt & 0xFF).append(".");
		sb.append((ipInt >> 8) & 0xFF).append(".");
		sb.append((ipInt >> 16) & 0xFF).append(".");
		sb.append((ipInt >> 24) & 0xFF);
		return sb.toString();
	}

	/**
	 * 不可用
	 * 
	 * @return
	 */
	public String getLocalIp() {

		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip2 = addr.getHostAddress().toString(); // 获取本机ip
			String hostName = addr.getHostName().toString(); // 获取本机计算机名称

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void q() {
		ConnectivityManager conMann = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetworkInfo.isConnected()) {
			mobileIP = getLocalIpAddress();
		}
	}
	

	public String getWifiIP() {
		Thread td = new Thread(new Runnable() {
			@Override
			public void run() {

				ConnectivityManager conMann = (ConnectivityManager) mContext
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if (wifiNetworkInfo.isConnected()) {
					WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					int ipAddress = wifiInfo.getIpAddress();
					WifiIP = intToIp(ipAddress);
				}
			}
		});
		td.start();
		try {
			td.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return WifiIP;

	}
}

