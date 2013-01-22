package com.caphael.fsconf;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;

import com.caphael.fsconf.InputStreamConf;

public class HDFSInputStreamConf extends InputStreamConf {
	
	public static class NoHadoopUserSetException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NoHadoopUserSetException(){
			super("Illegal Hadoop user name!!");
		}
	}
	
	Configuration CONF=null;
	String HUSER = null;
	FileSystem FS = null;
	FSDataInputStream IN = null;
	
	public HDFSInputStreamConf() throws NoHadoopUserSetException {
		// TODO Auto-generated constructor stub
		super();
		try{
			CONF = new Configuration();
		}catch (Exception e){
			NoHadoopUserSetException ne = new NoHadoopUserSetException();
			throw ne;
		}
	}
	
	private void setHadoopUser(String huser){
		HUSER = huser+","+huser;
	}
	
	public void initInputStream(String fname,String huser,String charset) throws Exception{
		super.initInputStream(fname,charset);
		setHadoopUser(huser);
		try{
			CONF.set("hadoop.job.ugi", HUSER);
			FS = FileSystem.get(URI.create(INFNAME), CONF);
			IN = FS.open(INFPATH);
			ISR =new InputStreamReader(IN,charset);
		}catch(FileNotFoundException e){
			ISR = null;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void initInputStream(String fname) throws Exception {
		initInputStream(fname,HUSER,CHARSET);
	}
}
