package data;

import com.example.videochat.XJCS;

import android.widget.ImageView.ScaleType;
import mylib.cfg;

public class icfg 
{
	public static final	String xjcsPreviewWidth  ="xjcsPreviewWidth";
	public static final	 String  xjcsPreviewHeight="xjcsPreviewheight";
	public static final	 String  xjcsRefreshRate= "xjcsRefreshRate" ;
	public static final	 String xjcsImageQuality ="xjcsImageQuality" ;
	public static final	 String xjcsPreviewSizeData ="xjcsPreviewSizeData" ;
	
	
	
	public static final	 byte menuClient_videoSize_CENTER =1;
	public static final	 byte menuClient_videoSize_FIT_CENTER =2;
	public static final	 String menuClient_videoSize ="menuClient_videoSize" ;
	
	
	public static final String xjcsroot="cameraId__";
	public static final String xjcsroot_suffix="___";
	
	
	
	public static final	 byte app_entrySel_c =1;
	public static final	 byte app_entrySel_s =2;
	public static final	 String app_entrySel ="app_entrySel";
	

	public static final String menuClient_inputIp = "menuClient_inputIp";
	public static final String bAuto_gc = "bAuto_gc";
	
	
	
	
	//-------------------------------client
	public static final	 String currentCameraId ="client_currentCameraId" ;

	
	public static void save_xjcs(XJCS xjcs,int cameraId)
	{
		cfg.putInt(xjcsroot+cameraId+xjcsroot_suffix+xjcsPreviewWidth, xjcs.iPreviewWidth);
		cfg.putInt(xjcsroot+cameraId+xjcsroot_suffix+xjcsPreviewHeight,xjcs.iPreviewHeight);
		cfg.putInt(xjcsroot+cameraId+xjcsroot_suffix+xjcsRefreshRate, xjcs.refreshRate);
		cfg.putInt(xjcsroot+cameraId+xjcsroot_suffix+xjcsImageQuality, xjcs.imageQuality);
	}	
	public static XJCS read_xjcs(int cameraId)
	{
		 XJCS  xjcs=new XJCS();
		xjcs.iPreviewWidth=(short) cfg.getInt(icfg.xjcsroot+cameraId+xjcsroot_suffix+icfg.xjcsPreviewWidth,0);
		xjcs.iPreviewHeight=(short) cfg.getInt(icfg.xjcsroot+cameraId+xjcsroot_suffix+icfg.xjcsPreviewHeight,0);
		xjcs.imageQuality= (byte) cfg.getInt(icfg.xjcsroot+cameraId+xjcsroot_suffix+icfg.xjcsImageQuality,50);
		xjcs.refreshRate= (short) cfg.getInt(icfg.xjcsroot+cameraId+xjcsroot_suffix+icfg.xjcsRefreshRate,100);
		return xjcs;
	}
	
	//-------------------------------client
/*	public static final	 byte cimg_previewCaleType_center =1;
	public static final	 byte cimg_previewCaleType_FIT_CENTER =2;
	public static final	 String img_previewCaleType ="client_img_previewCaleType" ;
	*/
	
	
}
