package com.caphael.fsconf;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.hadoop.fs.Path;


public abstract class OutputStreamConf {
	String CHARSET = null;
	String OUTFNAME = null;
	Path OUTFPATH = null;
	
	public OutputStreamConf(){
		CHARSET = Charset.defaultCharset().name();
	}
	
	private void setCharset(String charset){
		CHARSET = charset;
	}
	
	private void setFilename(String fname){
		OUTFNAME = fname;
		OUTFPATH = new Path(OUTFNAME);
	}
	
	public void initOutputStream(String fname,String charset) throws Exception{
		setCharset(charset);
		setFilename(fname);
	}
	public void initOutputStream(String fname) throws Exception{
		setFilename(fname);
	}
	
	public String getOutFullFilename(){
		return OUTFPATH.toString();
	}
	
	public String getOutFilename(){
		return OUTFPATH.getName();
	}
	
	public abstract void close() throws IOException;

}
