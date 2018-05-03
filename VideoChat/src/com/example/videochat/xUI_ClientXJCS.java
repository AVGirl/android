package com.example.videochat;

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
		if(edit_ImageQualit==null)ff.sc("if(edit_ImageQualit==null)");
		edit_ImageQualit.setText(cfg.getString(icfg.xjcsImageQuality,"70"));
		 edit_RefreshRate.setText(cfg.getString(icfg.xjcsRefreshRate,"50"));
		 String previewSize="";
		 previewSize+=cfg.getString(icfg.xjcsPreviewWidth,"")+"x";
		 previewSize+=cfg.getString(icfg.xjcsPreviewHeight,"")+"";
		 tv_setPreviewSize.setText(previewSize);
	}
			
			
			
	void actClientXJCS_btn_OK()
	{
		cfg.putString(icfg.xjcsImageQuality,edit_ImageQualit.getText());
		cfg.putString(icfg.xjcsRefreshRate, edit_RefreshRate.getText());
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
		
		
		
		
		
		//ff.sc("sizes",sizes);
		for (int i = 0; i < sizes.length; i++) 
		{
			String strs[]= sizes[i].split("x");
			if(strs!=null && strs.length==2)
			{
				ws[i]=strs[0];
				hs[i]=strs[1];
				
			}ff.scErr("if(strs!=null && strs.length==2)");
			
			
		}
		int index=dhk.singleChoiceDialog(sizes, 0, "", "Ok");
		if(index==-1)return;
		
		cfg.putString(icfg.xjcsPreviewWidth,ws[index]);
		cfg.putString(icfg.xjcsPreviewHeight,hs[index]);
		tv_setPreviewSize.setText(ws[index]+"x"+hs[index]);
		
/*		gsh.clear();
		gsh.cmd=command.ML_VIDEOMONITORING_SETWH;
		gsh.csA=Integer.valueOf(ws[index]);
		gsh.csB=Integer.valueOf(hs[index]);
		mysoc2.send(gsh);
		ff.sc(ws[index]);
		ff.sc(hs[index]);*/
		
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
