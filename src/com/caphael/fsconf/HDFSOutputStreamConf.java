package com.caphael.fsconf;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import com.caphael.fsconf.OutputStreamConf;

public class HDFSOutputStreamConf extends OutputStreamConf {
	
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
	FSDataOutputStream OUT = null;
	
	
	public HDFSOutputStreamConf() throws NoHadoopUserSetException {
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
	
	public void initOutputStream(String fname,String huser,String charset) throws Exception{
		super.initOutputStream(fname,charset);
		setHadoopUser(huser);
		CONF.set("hadoop.job.ugi", HUSER);
		FS = FileSystem.get(URI.create(OUTFNAME), CONF);
		OUT = FS.create(OUTFPATH);
	}
	
	public void initOutputStream(String fname) throws Exception {
		initOutputStream(fname,HUSER,CHARSET);
	}


	public void writeLine(String line) throws IOException{
		OUT.writeChars(line+"\n");
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		if (null!=OUT){
			OUT.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			String fname = "hdfs://mytest:9000/user/devuser/test/writetest";
			HDFSOutputStreamConf conf = new HDFSOutputStreamConf();
			conf.initOutputStream(fname);
			conf.writeLine("aaaaaaaaaaaaaaaa");
			conf.writeLine("bbbbbbbbbbbbbbbb");
			conf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
