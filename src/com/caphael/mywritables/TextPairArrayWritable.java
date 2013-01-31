package com.caphael.mywritables;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

public class TextPairArrayWritable extends ArrayWritable{

	private int SIZE=0;
	private static String SEPSTR = ",";
	
	public TextPairArrayWritable() {
		super(TextPairWritable.class);
		// TODO Auto-generated constructor stub
	}
	
	public TextPairArrayWritable(String[][] values) {
		super(TextPairWritable.class);
		// TODO Auto-generated constructor stub
		setStringPairArray(values);
	} 
	
	public void setStringPairArray(String[][] values){
		SIZE =  values.length;
		TextPairWritable[] pairs = new TextPairWritable[SIZE];
		for (int i=0;i<SIZE;i++){
			pairs[i]= new TextPairWritable(values[i]);
		}
		set(pairs);
	}
	
	public String[][] toStringArray(){
		Writable[] writables =  get();
		SIZE = writables.length;
		String[][] strs = new String[SIZE][2];
		for (int i=0;i<strs.length;i++){
			strs[i][0] = ((TextPairWritable)writables[i]).getFirst().toString();
			strs[i][1] = ((TextPairWritable)writables[i]).getSecond().toString();
		}
		return strs;
	}
	
	public String toString(){
		String str = "";
		Writable[] writables =  get();
		for (Writable writable: writables){
			str+=writable.toString();
			str+=SEPSTR;
		}
		return str.substring(0,str.length()-1);
		
	}
}
