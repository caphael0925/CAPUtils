package com.Caphael.JCrypto;

import com.Caphael.JCrypto.MobileNumberCrypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Random;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import javax.crypto.Cipher;

public class HCrypto extends MobileNumberCrypto{
	
	Configuration CONF;
	
	private String loadDesKeyFile(String keyfname) {
		CONF = new Configuration();
		CONF.set("hadoop.job.ugi", "devuser,devuser");
		FileSystem fs=null;
		FSDataInputStream in = null;
		InputStreamReader fr = null;
		BufferedReader br = null;
		
		try {
			fs = FileSystem.get(URI.create(keyfname), CONF);
			
			in = fs.open(new Path(keyfname));
			fr = new InputStreamReader(in);
			br = new BufferedReader(fr);
			String line = br.readLine();
			return line;
		} catch( Exception e) {
		}		
		finally {
			try {
				if( br != null ) br.close();
				if( fr != null ) fr.close();
			} catch( IOException e ) {
				e.printStackTrace();
			}
		}
		
		return null;		
	}
}
