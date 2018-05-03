package com.fff.entity;

import android.widget.ImageView;

public class AsynData3 {


public AsynData3(ImageView img, int id, String cacheFile) {
		super();
		this.img = img;
		this.id = id;
		this.cacheFile = cacheFile;
	}
ImageView img;
public ImageView getImg() {
	return img;
}
public void setImg(ImageView img) {
	this.img = img;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCacheFile() {
	return cacheFile;
}
public void setCacheFile(String cacheFile) {
	this.cacheFile = cacheFile;
}
int id;
String cacheFile;




}
