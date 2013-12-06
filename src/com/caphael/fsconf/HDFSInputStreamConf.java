package com.caphael.fsconf;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	
	public HDFSInputStreamConf() {
		// TODO Auto-generated constructor stub
		super();
		CONF = new Configuration();

	}
	
	public void setHadoopUser(String huser){
		HUSER = huser+","+huser;
	}
	
	public void initInputStream(String fname,String huser,String charset) throws Exception{
		super.initInputStream(fname,charset);
		setHadoopUser(huser);
		try{
			CONF.set("hadoop.job.ugi", HUSER);
			FS = FileSystem.newInstance(URI.create(INFNAME), CONF);
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
	
	public void close() throws Exception{
		super.close();
		if(FS!=null){
			FS.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			String fname = "hdfs://mytest:9000/user/devuser/TestTable.json";
			HDFSInputStreamConf conf = new HDFSInputStreamConf();
//			conf.initInputStream(fname, "devuser", "UTF-8");
			String contents = conf.loadFile(fname);
			System.out.println(contents);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
