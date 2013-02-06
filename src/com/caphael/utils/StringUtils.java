package com.caphael.utils;

public class StringUtils {

	/**
	 * @param args
	 */
	
	public static String join(String[] strs,String sepstr){
		String ret="";
		int i;
		for (i=0;i<strs.length-1;i++){
			ret+= strs[i] + sepstr;
		}
		return ret+strs[i];
	}
	
	public static String join(String[] strs){
		return join(strs,",");
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] strs={"A","B","C","D","E"};
		System.out.println(join(strs));
	}

}
