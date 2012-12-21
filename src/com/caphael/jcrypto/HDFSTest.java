package com.caphael.jcrypto;

import com.caphael.fsconf.HDFSInputStreamConf;

public class HDFSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		HCrypto hc = new HCrypto();
		HDFSInputStreamConf hconf=new HDFSInputStreamConf();
		try{
			hc.loadDesKeys("hdfs://mytest:9000/user/devuser/test/", hconf);
			System.out.println(hc.desDeCrypt("008efd7b12bab4aadc5cbb7b5903088aac"));
			System.out.println(hc.desDeCrypt("008efd7b12bab4aadc5cbb7b5903088aac"));
			System.out.println(hconf.getInFilename());
			System.out.println(hconf.getInFullFilename());

		}catch (Exception e){
//			e.printStackTrace();
		}


	}

}
