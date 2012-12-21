package com.caphael.fsconf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.hadoop.fs.Path;

import com.caphael.fsconf.StdioInputStreamConf.UnexpectedFilenameException;

public class InputStreamConf {
	String CHARSET = null;
	String INFNAME = null;
	Path INFPATH = null;
	int BUFLENTH = 1024;
	InputStreamReader ISR = null;
	
	static class BufferExcessException extends Exception{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BufferExcessException(){
			super("The buffered string is excess!!");
		}
	}
	
	public InputStreamConf(){
		CHARSET = Charset.defaultCharset().name();
	}
	
	public void initInputStream(String fname) throws Exception{
		INFNAME = fname;
		INFPATH = new Path(INFNAME);
	}
	
	public InputStreamReader getInputStream(){
		return ISR;
	}
	
	public String getInFullFilename(){
		return INFPATH.toString();
	}
	
	public String getInFilename(){
		return INFPATH.getName();
	}
	
	
	public String loadData() throws BufferExcessException{
		BufferedReader br = null;
		try {
			if (null==ISR){
				return null;
			}
			br = new BufferedReader(ISR);
			String bufstr = "";
			String line;
			line=br.readLine();
			while (null!=line){
				bufstr+=line;
				if (bufstr.length()>=BUFLENTH){
					BufferExcessException e = new BufferExcessException();
					throw e;
				}
				line=br.readLine();
			}
			return bufstr;
		} catch( Exception e) {
			e.printStackTrace();
		}		
		finally {
			try {
				if( br != null ) br.close();
				if( ISR != null ) ISR.close();
			} catch( IOException e ) {
				e.printStackTrace();
			}
		}
		
		return null;		
	}

	public String loadFile(String fname) {
		try {
			initInputStream(fname);
			return loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
