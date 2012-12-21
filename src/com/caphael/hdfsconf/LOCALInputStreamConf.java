package com.caphael.hdfsconf;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LOCALInputStreamConf extends InputStreamConf {
	
	public LOCALInputStreamConf() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public InputStreamReader getInputStream(String fname){		
		try{
			return new InputStreamReader(new FileInputStream(fname), CHARSET);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}


}
