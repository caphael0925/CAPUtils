package com.caphael.mywritables;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

public class IntPairArrayWritable extends ArrayWritable{

	private int SIZE=0;
	private static String SEPSTR = ",";
	
	public IntPairArrayWritable() {
		super(IntPairWritable.class);
		// TODO Auto-generated constructor stub
	}
	
	public IntPairArrayWritable(int[][] values) {
		super(IntPairWritable.class);
		// TODO Auto-generated constructor stub
		setIntPairArray(values);
	} 
	
	public void setIntPairArray(int[][] values){
		SIZE =  values.length;
		IntPairWritable[] pairs = new IntPairWritable[SIZE];
		for (int i=0;i<SIZE;i++){
			pairs[i]= new IntPairWritable(values[i]);
		}
		set(pairs);
	}
	
	public int[][] toIntArray(){
		Writable[] writables =  get();
		SIZE = writables.length;
		int[][] ints = new int[SIZE][2];
		for (int i=0;i<ints.length;i++){
			ints[i][0] = ((IntPairWritable)writables[i]).getFirst().get();
			ints[i][1] = ((IntPairWritable)writables[i]).getSecond().get();
		}
		return ints;
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
