package com.Caphael.JCrypto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;

public class HDFSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration CONF;
		CONF = new Configuration();
		CONF.set("hadoop.job.ugi", "devuser,devuser");
		FileSystem fs=null;
		FSDataInputStream in = null;
		InputStreamReader fr = null;
		BufferedReader br = null;
		String keyfname=args[1];
		
		try {
			fs = FileSystem.get(URI.create(keyfname), CONF);
			
			in = fs.open(new Path(keyfname));
			fr = new InputStreamReader(in);
			br = new BufferedReader(fr);
			String line = br.readLine();
			System.out.println(line);
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
	}

}
