package com.caphael.fsconf;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.caphael.fsconf.InputStreamConf;

public class LocalInputStreamConf extends InputStreamConf {
	
	public LocalInputStreamConf() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initInputStream(String fname) throws Exception{
		super.initInputStream(fname);
		try{
			ISR = new InputStreamReader(new FileInputStream(INFNAME), CHARSET);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
