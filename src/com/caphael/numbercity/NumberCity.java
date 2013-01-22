package com.caphael.numbercity;

import com.caphael.fsconf.HDFSInputStreamConf;
import com.caphael.fsconf.HDFSInputStreamConf.NoHadoopUserSetException;
import com.caphael.fsconf.InputStreamConf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;;

public class NumberCity {

	/**
	 * @param args
	 */
	static String FNAME = "hdfs://mytest:9000/user/devuser/test/haoduanbiao.umsc";
	static String HUSER = "devuser,devuser";
	List<Integer> LOCLSTIDX = new ArrayList<Integer>();
	List<String> LOCLSTDESC = new ArrayList<String>();
	String[] LOCARR;
	String LOCDESC;
	int LOCIDX=0,NUMMIN=0,NUMCNT=0,NUMMAX=0;
	static String LOCUNK="<UNKNOWN>";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NumberCity nc = new NumberCity();
		HDFSInputStreamConf hconf = null;
		try {
			hconf = new HDFSInputStreamConf();
			hconf.initInputStream(FNAME, HUSER, "gb2312");
			nc.loadCityList(hconf);
		} catch (NoHadoopUserSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (null!=hconf.getInputStream()){
				hconf.closeISR();
			}
		}
		System.out.println(nc.getCity("8613086538808"));
		
	}
	
	public void loadCityList(InputStreamConf isconf) {
		InputStreamReader isr = null;
		BufferedReader br = null;
		String[] fields;
		
		try {			
			isr = isconf.getInputStream();
			br = new BufferedReader(isr);
			
			String line;
			
			line = br.readLine();
			while (null!=line){
				fields = line.split(",");
				int n = Integer.parseInt(fields[0]);
				
				LOCLSTIDX.add(n);
				LOCLSTDESC.add(fields[2]);
				if (n>NUMMAX){
					NUMMAX=n;
				}
				if (n<NUMMIN||0==NUMMIN){
					NUMMIN=n;
				}
				line=br.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if (null!=br){
					br.close();
				}
				if (null!=isr){
					isr.close();
				}	
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		LOCARR = new String[NUMMAX-NUMMIN+1];
		
		for (int i=0;i<LOCLSTIDX.size();i++){
			LOCARR[LOCLSTIDX.get(i)-NUMMIN]=LOCLSTDESC.get(i);
		}	
	}

	public String getCity(String mnum){
		if (mnum.matches("86[0-9]{11}")){
			LOCIDX = Integer.parseInt(mnum.substring(2, 9))-NUMMIN;
			
			if (LOCIDX<0 || LOCIDX > NUMMAX-NUMMIN){
				LOCDESC= LOCUNK;
			}else{
				LOCDESC = LOCARR[LOCIDX];
				if (null==LOCDESC){
					LOCDESC = LOCUNK;
				} 
			}
		}else{
			LOCDESC = LOCUNK;
		}
		
		return LOCDESC;
	}
	
}
