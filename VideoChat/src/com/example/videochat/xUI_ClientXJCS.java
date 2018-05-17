package com.example.videochat;

import android.R.integer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import data.icfg;
import mylib.DHK;
import mylib.FF;
import mylib.cfg;
import mylib.command;

public class xUI_ClientXJCS  extends ActionBarActivity
{
	EditText edit_ImageQualit;
	EditText edit_RefreshRate;
	TextView tv_setPreviewSize;
	Button btn_setPreviewSize;
	Button btn_ok;
	DHK dhk;
	FF ff;
	XJCS xjcs = new XJCS();
	
	
	void bindView()
	{
		 edit_ImageQualit=(EditText) this.findViewById(R.id.actClientXJCS_edit_ImageQualit);
		 edit_RefreshRate=(EditText) this.findViewById(R.id.actClientXJCS_edit_refreshRate);
		
		
		
		 tv_setPreviewSize=(TextView) this.findViewById(R.id.actClientXJCS_tv_setPreviewSize);
		 btn_setPreviewSize=(Button) this.findViewById(R.id.actClientXJCS_btn_setPreviewSize);
		 btn_ok=(Button) this.findViewById(R.id.actClientXJCS_btn_OK);
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.act_client_xjcs);
		 dhk=new DHK(this);
		 ff=new FF(this);
		 bindView();
		 initViewData();
	}

	void initViewData()
	{
		
		 xjcs=icfg.read_xjcs(cfg.getInt(icfg.currentCameraId,0));
		edit_ImageQualit.setText(""+xjcs.imageQuality);
		 edit_RefreshRate.setText(""+xjcs.refreshRate);
		 String previewSize=xjcs.iPreviewWidth+"x"+xjcs.iPreviewHeight;
		 tv_setPreviewSize.setText(previewSize);
	}
			
			
			
	void actClientXJCS_btn_OK()
	{
		
		xjcs.imageQuality=Byte.valueOf(edit_ImageQualit.getText().toString());
		xjcs.refreshRate=Short.valueOf(edit_RefreshRate.getText().toString());
		
		int a=cfg.getInt(icfg.currentCameraId,0);
		icfg.save_xjcs(xjcs, a);
		
		this.setResult(123);
		this.finish();
		
	}
	
	void actClientXJCS_btn_setPreviewSize()
	{
		
		String sizes[]= cfg.getString(icfg.xjcsPreviewSizeData,"").split(";");
		if(sizes==null){ff.sc("sizes==null");return;}
		
		if(sizes.length<2){ff.scErr("if(sizes.length<2)");}
		
		
		
		String ws[]=new String[sizes.length];
		String hs[]=new String[sizes.length];
		
		
		int select=0;
		
		
		//ff.sc("sizes",sizes);
		for (int i = 0; i < sizes.length; i++) 
		{
			String strs[]= sizes[i].split("x");
			if(strs!=null && strs.length==2)
			{
				ws[i]=strs[0];
				hs[i]=strs[1];
				if(Short.valueOf(ws[i])==xjcs.iPreviewWidth&&Short.valueOf(hs[i])==xjcs.iPreviewHeight)
				{
					select=i;
				}
				
				
			}ff.scErr("if(strs!=null && strs.length==2)");
			
			
		}
		int index=dhk.singleChoiceDialog(sizes, select, "", "Ok");
		if(index==-1)return;
		
		xjcs.iPreviewWidth=Short.valueOf(ws[index]);
		xjcs.iPreviewHeight=Short.valueOf(hs[index]);
		
		tv_setPreviewSize.setText(ws[index]+"x"+hs[index]);
		
	}
	
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.actClientXJCS_btn_OK:actClientXJCS_btn_OK();break;
		case R.id.actClientXJCS_btn_setPreviewSize:actClientXJCS_btn_setPreviewSize();break;

		default:
			break;
		}
		
		
		

	}

	
	
}
