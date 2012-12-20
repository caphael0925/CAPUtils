package com.Caphael.JCrypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class MobileNumberCrypto {
	
	private TreeMap<String, String> desKeys = new TreeMap<String, String>();
	private TreeMap<String, SecretKey> desSecretKeys = new TreeMap<String, SecretKey>();
	
	private TreeMap<String, byte[]> rsaPriKeys = new TreeMap<String, byte[]>();
	
	private String loadDesKeyFile(String keyfname) {
		InputStreamReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new InputStreamReader(new FileInputStream(keyfname), 
					Charset.defaultCharset().name());
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
	
	public boolean loadDesKeys(String keypath) {
		
		for (int i=0; i < 0xffff; ++i) {
			String keyid = String.format("%02d", i);
			String keyfname = String.format("%s/des.%s.key", keypath, keyid);
			String deskey = loadDesKeyFile(keyfname);
			if (deskey == null) {
				break;
			}		
			desKeys.put(keyid, deskey);
			desSecretKeys.put(keyid, desBuildKey(deskey));
		}
		
		return desKeys.size() > 0;
	}
	
	private byte[] loadRSAPriKeyFile(String keyfname) {
		
		FileInputStream fis = null;
		
		try {
			File f = new File(keyfname);
			fis = new FileInputStream(f);
			
			byte[] priBytes = new byte[(int) f.length()];
			fis.read(priBytes);
			fis.close();
			
			return priBytes;
		} catch( Exception e) {
		}		
		finally {
		}
		
		return null;		
	}
	
	public boolean loadRSAPriKeys(String keypath) {
		for (int i=0; i < 0xffff; ++i) {
			String keyid = String.format("%02d", i);
			String keyfname = String.format("%s/pri.%s.key.der", keypath, keyid);
			byte[] prikey = loadRSAPriKeyFile(keyfname);
			if (prikey == null) {
				break;
			}		
			rsaPriKeys.put(keyid, prikey);
		}
		
		return rsaPriKeys.size() > 0;		
	}
	
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

	public void fromString(String deskeys) {
		desKeys.clear();
		desSecretKeys.clear();
		
		String[] items = deskeys.split(",", -1);
		for (int i=0; i<items.length;++i) {
			String[] kvpairs = items[i].split("=", -1);
			if (kvpairs.length != 2) {
				continue;
			}
			desKeys.put(kvpairs[0], kvpairs[1]);
			desSecretKeys.put(kvpairs[0], desBuildKey(kvpairs[1]));
		}
		
	}

	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
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
		
	public String desEnCrypt(String id) {
		if (id == null || id.length() >= 34) {
			return id;
		}
		
		String desEKeyId = desKeys.lastKey();
		SecretKey securekey  = desSecretKeys.get(desEKeyId);
		if (securekey == null) {
			return id;
		}
		
		try {			
			if (id.length() % 8 != 0) {
				int len = id.length() / 8 * 8 + 8;
				id = String.format("%"+len+"s", id);
				id = id.replace(' ', '0');
			}
					        
			// encrypt
			SecureRandom sr = new SecureRandom();
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			byte[] data = cipher.doFinal(id.getBytes(), 0, 16);			
			return new String(desEKeyId + byte2hex(data));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return id;		
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
	
	private PrivateKey getPrivateKey(byte[] keys) throws Exception { 
 		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
 		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keys); 
 		RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(privateKeySpec); 
        return privateKey;  
    } 
	
	public String rsaDesDeCrypt(String id) {
		if (id == null || id.length() <= 34) {
			return id;
		}
		
		String kid = id.substring(0, 2);
		byte[] rsaPriKey = rsaPriKeys.get(kid);
		if (rsaPriKey == null) {
			return null;
		}
		int keylen = id.length() - 32 - 2;
		
		
		try {
			// make the rsa private key
			PrivateKey privateKey = getPrivateKey(rsaPriKey);
			
			Cipher rsacipher = Cipher.getInstance("RSA");
			rsacipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] deBytes = rsacipher.doFinal(hex2byte(id.substring(2, 2+keylen)));
			
			// make the des key
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(deBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);

			// encrypt
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			byte[] data = cipher.doFinal(hex2byte(id.substring(2+keylen)));			
			return new String(data).substring(3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String MobileNumberEnCrypt(String id) {
		return desEnCrypt(id); // only support des
	}
	
	public String MobileNumberDeCrypt(String id) {
		if (id.length() == 34) {
			return desDeCrypt(id);
		} 
		else if (id.length() < 34) {
			return id;
		}
		else {
			return rsaDesDeCrypt(id);
		}
	}
	
	/**
	 * @param args
	 */
	public void test(String[] args) {
		MobileNumberCrypto mnc = new MobileNumberCrypto();
		mnc.loadDesKeys("/Users/wenyong/dev/7g/dc/file_sync");
		//mnc.loadRSAPriKeys("/Users/wenyong/dev/7g/dc/file_sync");
		System.out.println(mnc);
		MobileNumberCrypto mnc1 = new MobileNumberCrypto();
		mnc1.fromString(mnc.toString());
		System.out.println(mnc1);
		System.out.println(mnc.desKeys.lastKey());
		
		String id = mnc.MobileNumberEnCrypt("13401035360");
		System.out.println(id);
		String id1 = mnc.MobileNumberDeCrypt(id);
		System.out.println(id1);
		
		//System.out.println(mnc.MobileNumberDeCrypt("006586c4b67d7650153ddbee7e7ad3e36bcc1fd80ef046ba58"));
	}

}
