import com.caphael.fsconf.HDFSInputStreamConf;
import com.caphael.fsconf.HDFSInputStreamConf.NoHadoopUserSetException;
import com.caphael.fsconf.StdioInputStreamConf;
import com.caphael.jcrypto.HCrypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdioDecrypto {
	
	static String KEYFNAME = "hdfs://mytest:9000/user/devuser/test/";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		if (1!=args.length){
			System.out.println("Please give the only parameter KEYFILENAME");
			System.exit(1);
		}
		
		KEYFNAME = args[0];
		
		HDFSInputStreamConf hconf = new HDFSInputStreamConf();
		StdioInputStreamConf stdconf = new StdioInputStreamConf();
		
		HCrypto hcrypto = new HCrypto();
		
		hcrypto.loadDesKeys(KEYFNAME, hconf);
		
		stdconf.initInputStream();
		
		InputStreamReader isr = stdconf.getInputStream();
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		String[] field;
		
		while (true) {
			line = br.readLine();
			if (null==line){
				break;
			}
			field = line.split("[|]");
			field[0] = hcrypto.desDeCrypt(field[0]);
			System.out.println(field[0]+"|"+field[1]);		
		}
		
	}

}
