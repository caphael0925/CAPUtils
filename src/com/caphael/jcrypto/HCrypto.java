package com.caphael.jcrypto;

import com.caphael.fsconf.InputStreamConf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class HCrypto{
	
	private TreeMap<String, String> desKeys = new TreeMap<String, String>();
	private TreeMap<String, SecretKey> desSecretKeys = new TreeMap<String, SecretKey>();
	private TreeMap<String, byte[]> rsaPriKeys = new TreeMap<String, byte[]>();

	@Override
	public String toString() {
		
		String o = new String();
		for(Map.Entry<String, String> it : desKeys.entrySet()) {
			if (o.length() > 0) {
				o += ",";
			}
			o += it.getKey() + "=" + it.getValue();
		}
		return o;
	}

	private SecretKey desBuildKey(String deskey) {
		// make the des key
		byte[] arrB = new byte[8];
		for (int i = 0; i < deskey.length() && i < arrB.length; i++) {
			arrB[i] = (byte) deskey.charAt(i);
		}
		
		DESKeySpec dks;
		try {
			dks = new DESKeySpec(arrB);
	
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			return securekey;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean loadDesKeys(String keypath,InputStreamConf isconf) throws Exception {
		
		for (int i=0; i < 0xffff; ++i) {
			String keyid = String.format("%02d", i);
			String keyfname = String.format("%s/des.%s.key", keypath, keyid);
			String deskey = isconf.loadFile(keyfname);
			if (deskey == null) {
				break;
			}		
			desKeys.put(keyid, deskey);
			desSecretKeys.put(keyid, desBuildKey(deskey));
			isconf.close();
		}
		
		return desKeys.size() > 0;
	}

	public String desDeCrypt(String id) {
		// only support des encrypt
		if (id == null || id.length() != 34) {
			return id;
		}
		
		String kid = id.substring(0, 2);
		SecretKey securekey  = desSecretKeys.get(kid);
		if (securekey == null) {
			return null;
		}
				
		try {
			// encrypt
			SecureRandom sr = new SecureRandom();
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			byte[] data = cipher.doFinal(hex2byte(id.substring(2)));
			return new String(data).replaceFirst("^0*", "");
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		return null;		
	}

	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	public void desDecryptSTD(String fname){
		
	}
}
