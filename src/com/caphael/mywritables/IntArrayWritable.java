package com.caphael.mywritables;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class IntArrayWritable extends ArrayWritable{

	int SIZE;
	
	public IntArrayWritable() {
		super(IntWritable.class);
		// TODO Auto-generated constructor stub
	}
	
	public IntArrayWritable(int[] values) {
		super(IntWritable.class);
		// TODO Auto-generated constructor stub
		setIntArray(values);
	} 
	
	public void setIntArray(int[] values){
		IntWritable[] writables = new IntWritable[SIZE];
		for (int i=0;i<SIZE;i++){
			writables[i]= new IntWritable(values[i]);
		}
		set(writables);
	}
	
	public void setBinaryInt(int value){
		String binstr = Integer.toBinaryString(value);
		int[] ints = new int[SIZE];
		int orginStrLen = binstr.length();
		for (int i=0;i<SIZE-orginStrLen;i++){
			binstr = "0"+binstr;
		}
		char[] binchars = binstr.toCharArray();
		for (int i=0;i<SIZE;i++){
			ints[i] = ('1'==binchars[i]?1:0);
		}
		
		setIntArray(ints);
		}
	
	public int[] toIntArray(){
		Writable[] writables =  get();
		int[] ints = new int[writables.length];
		for (int i=0;i<ints.length;i++){
			ints[i] = ((IntWritable)writables[i]).get();
		}
		return ints;
	}
}
