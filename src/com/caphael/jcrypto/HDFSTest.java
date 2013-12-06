package com.caphael.jcrypto;

import com.caphael.fsconf.HDFSInputStreamConf;

public class HDFSTest {

	/**
	 * @param args
	 */
	static String CITYFNAME = "hdfs://mytest:9000/user/devuser/test/haoduanbiao.umsc";
	static String KEYFPATH = "hdfs://mytest:9000/user/devuser/configurations";
	static String HUSER = "devuser";
	static String CHARSET = "gb2312";
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		HCrypto hc = new HCrypto();
		HDFSInputStreamConf hconf=new HDFSInputStreamConf();
		hconf.initInputStream(KEYFPATH, HUSER, CHARSET);
		try{
			hc.loadDesKeys(KEYFPATH, hconf);
			System.out.println(hc.desDeCrypt("009ddd05190edf05108145c6cdce73d42e"));
			System.out.println(hc.desDeCrypt("009ddd05190edf05108145c6cdce73d42e").substring(2,5));
			System.out.println(("009ddd05190edf05108145c6cdce73d42e"));
			System.out.println(hc.toString());
			System.out.println(hconf.getInFullFilename());
		}catch (Exception e){
//			e.printStackTrace();
		}
	}
}
