package com.fff.FileManager;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class myfile extends File{
	public myfile( java.lang.String name, long time , int size, boolean isFile) {
		super(name);
		if(name==null){name="";}
		this.name = name;
		this.time = time;
		this.size = size;
		this.isfile = isFile;
	}

String name;long time;int size;boolean isfile;
	
    
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getParent() {
		return  null;
	}

	@Override
	public File getParentFile() {
		return  null;
	}

	@Override
	public String getPath() {
		return  null;
	}

	@Override
	public boolean isAbsolute() {
		return false;
	}

	@Override
	public String getAbsolutePath() {
		return  null;
	}

	@Override
	public File getAbsoluteFile() {
		return  null;
	}

	@Override
	public String getCanonicalPath() throws IOException {
		return null;
	}

	@Override
	public File getCanonicalFile() throws IOException {
		return  null;
	}

	@Override
	public URL toURL() throws MalformedURLException {
		return  null;
	}

	@Override
	public URI toURI() {
		return null;
	}

	@Override
	public boolean canRead() {
		return false;
	}

	@Override
	public boolean canWrite() {
		return false;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public boolean isFile() {
		return isfile;
	}

	@Override
	public boolean isHidden() {
		return false;
	}
 
	@Override
	public long lastModified() {
		return time;
	}

	@Override
	public long length() {
		return size;
	}

	@Override
	public boolean createNewFile() throws IOException {
		return false;
	}

	@Override
	public boolean delete() {
		return false;
	}

	@Override
	public void deleteOnExit() {
		
	}

	@Override
	public String[] list() {
		return new String[]{};
	}

	@Override
	public String[] list(FilenameFilter filter) {
		return new String[]{};
	}

	@Override
	public File[] listFiles() {
		return new myfile[]{};
	}

	@Override
	public File[] listFiles(FilenameFilter filter) {
		return new myfile[]{};
	}

	@Override
	public File[] listFiles(FileFilter filter) {
		return new myfile[]{};
	}

	@Override
	public boolean mkdir() {
		return false;
	}

	@Override
	public boolean mkdirs() {
		return false;
	}

	@Override
	public boolean renameTo(File dest) {
		return false;
	}

	@Override
	public boolean setLastModified(long time) {
		return false;
	}

	@Override
	public boolean setReadOnly() {
		return false;
	}

	@Override
	public boolean setWritable(boolean writable, boolean ownerOnly) {
		return false;
	}

	@Override
	public boolean setWritable(boolean writable) {
		return false;
	}

	@Override
	public boolean setReadable(boolean readable, boolean ownerOnly) {
		return false;
	}

	@Override
	public boolean setReadable(boolean readable) {
		return false;
	}

	@Override
	public boolean setExecutable(boolean executable, boolean ownerOnly) {
		return false;
	}

	@Override
	public boolean setExecutable(boolean executable) {
		return false;
	}

	@Override
	public boolean canExecute() {
		return false;
	}

	@Override
	public long getTotalSpace() {
		return 0;
	}

	@Override
	public long getFreeSpace() {
		return 0;
	}

	@Override
	public long getUsableSpace() {
		return 0;
	}

	@Override
	public int compareTo(File pathname) {
		return 0;
	}



	@Override
	public int hashCode() {
		return 0;
	}

	
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	
	
	
	
		
		
		
	
}
