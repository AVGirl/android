package com.fff.FileManager;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter  extends FragmentStatePagerAdapter{  
	
	//ArrayList<Fragment> arrFragmen;
	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		notifyDataSetChanged();
		//this.arrFragmen=arrFragmen;
	}
 
	@Override
	public Fragment getItem(int arg0) {
		return jj.arrFE.get(arg0);
		
	}
  
	@Override
	public int getCount() {
		return  jj.arrFE.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}


	
}
