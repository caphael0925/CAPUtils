package com.caphael.jcrypto;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;

public class HDFSInputStreamConf extends InputStreamConf {
	
	static class NoHadoopUserSetException extends Exception{
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
	
	public HDFSInputStreamConf() throws Exception {
		// TODO Auto-generated constructor stub
		super();
		try{
			CONF = new Configuration();
			CONF.set("hadoop.job.ugi", HUSER);
		}catch (Exception e){
			e = new NoHadoopUserSetException();
			throw e;
		}

	}
	public HDFSInputStreamConf(String huser) {
		// TODO Auto-generated constructor stub
		HUSER=huser+","+huser;
	}
	
	public InputStreamReader getInputStream(String fname) {
		try{
			FS = FileSystem.get(URI.create(fname), CONF);
			IN = FS.open(new Path(fname));
			return new InputStreamReader(IN,CHARSET);
		}catch(FileNotFoundException e){
			return null;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
