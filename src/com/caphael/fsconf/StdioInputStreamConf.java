package com.caphael.fsconf;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.System;

public class StdioInputStreamConf extends InputStreamConf {
	
	static String INFILE = "StandardIn";
	static String OUTFILE = "StandardOut";

	public class UnexpectedFilenameException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public UnexpectedFilenameException(){
			super("Standard IO configurations don't need specified filename!!");
		}
	}
	
	public StdioInputStreamConf() {
		// TODO Auto-generated constructor stub
	}
	
	public void initInputStream() {
		ISR = new InputStreamReader(System.in);
	}
	
	public void initInputStream(String fname) throws UnexpectedFilenameException{
		UnexpectedFilenameException e = new UnexpectedFilenameException();
		throw e;
	}
}
