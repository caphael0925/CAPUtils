package com.Caphael.JCrypto;

import java.net.URI;
import java.util.Random;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import javax.crypto.Cipher;

public class JCrypto {
	
	String DESKEY="";
	String KEY_PATH="/user/mnet/conf/";
	Configuration CONF;
	String[] RSA_CACHE;
	
	public JCrypto(){
		CONF = new Configuration();
		CONF.set("", );
	}
	
	public String ImportDesKey(String keyid){
		String deskeyuri = KEY_PATH+ "/"+"des." + keyid + ".key";
		FileSystem fs = FileSystem.get(CONF);
		FSDataInputStream fsinstream = fs.create(new Path(deskeyuri));
		
	}
	
	
	public String DeCrypt(String str){
		int size,keylen;
		String KeyId,DesData,rsadeskey;
		
		size= str.length();
		if (size < 32 + 2){
			return str;
		}
			     
		keylen = size - 32 - 2;
	    KeyId = str.substring(0, 2);
	    DesData = str.substring(2+keylen);
	    
//	    if (keylen > 0){
//	    	rsadeskey = str.substring(2,2_keylen).decode("hex")
//	    	        dk = RSA_CACHE.get(rsadeskey)
//	    	        if dk == None:            
//	    	            rsapri = ImportRsaPriKey(KeyId)
//	    	            dk = rsapri.decrypt(rsadeskey)
//	    	            RSA_CACHE[rsadeskey] = dk
//	    else:
        if (null==DES_KEY){
        	DESKEY = ImportDesKey(KeyId);

        	des = DES.new(DES_KEY, DES.MODE_ECB)
        	dd = des.decrypt(DesData.decode("hex"))
        	        
        	return dd.lstrip('0')
        }
            
	    }
	        

	}

	    
	    
	    

	    
	        des = DES.new(dk, DES.MODE_ECB)
	        dd = des.decrypt(DesData.decode("hex"))

	        return dd.lstrip('0')
	    else:
	        if not DES_KEY:
	            DES_KEY = ImportDesKey(KeyId)

	        des = DES.new(DES_KEY, DES.MODE_ECB)
	        dd = des.decrypt(DesData.decode("hex"))
	        
	        return dd.lstrip('0')
	
	
}
