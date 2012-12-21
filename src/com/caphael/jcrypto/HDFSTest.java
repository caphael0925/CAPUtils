package com.caphael.jcrypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.TreeMap;

import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;

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
