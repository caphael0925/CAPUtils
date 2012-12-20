package com.caphael.jcrypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamConf {
	String CHARSET = null;

	public InputStreamConf(){
		CHARSET = Charset.defaultCharset().name();
	}
	
	public InputStreamReader getInputStream(String fname) throws Exception{
		return null;
	}
	
	public String loadFile(String fname) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			isr = getInputStream(fname);
			if (null==isr){
				return null;
			}
			br = new BufferedReader(isr);
			String line = br.readLine();
			return line;
		} catch( Exception e) {
			e.printStackTrace();
		}		
		finally {
			try {
				if( br != null ) br.close();
				if( isr != null ) isr.close();
			} catch( IOException e ) {
				e.printStackTrace();
			}
		}
		
		return null;		
	}
}
