package com.caphael.fsconf;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;

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
	String HUSER = "";
	FileSystem FS = null;
	FSDataInputStream IN = null;
	
	public HDFSInputStreamConf() throws NoHadoopUserSetException {
		// TODO Auto-generated constructor stub
		super();
		try{
			CONF = new Configuration();
			CONF.set("hadoop.job.ugi", HUSER);
		}catch (Exception e){
			NoHadoopUserSetException ne = new NoHadoopUserSetException();
			throw ne;
		}

	}
	public HDFSInputStreamConf(String huser) {
		// TODO Auto-generated constructor stub
		HUSER=huser+","+huser;
	}
	
	public void initInputStream(String fname) throws Exception{
		super.initInputStream(fname);
		try{
			FS = FileSystem.get(URI.create(INFNAME), CONF);
			IN = FS.open(INFPATH);
			ISR =new InputStreamReader(IN,CHARSET);
		}catch(FileNotFoundException e){
			ISR = null;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
