package com.fff.entity;

public class FileInfo {
	public FileInfo(String fileName, String modified, String size,long modifiedLong) {
	
		this.fileName = fileName;
		this.modified = modified;
		this.size = size;
		this.modifiedLong=modifiedLong;
	}

	String fileName,modified,size;
	long modifiedLong;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
