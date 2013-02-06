package com.caphael.mywritables;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import com.caphael.utils.StringUtils;

public class TextArrayWritable extends ArrayWritable{

	int SIZE;
	
	public TextArrayWritable() {
		super(Text.class);
		// TODO Auto-generated constructor stub
	}
	
	public TextArrayWritable(String[] values) {
		super(Text.class);
		// TODO Auto-generated constructor stub
		setStringArray(values);
	} 
	
	public void setStringArray(String[] values){
		SIZE = values.length;
		Text[] writables = new Text[values.length];
		for (int i=0;i<SIZE;i++){
			writables[i]= new Text(values[i]);
		}
		set(writables);
	}
	
	public String[] toStringArray(){
		Writable[] writables =  get();
		String[] strs = new String[writables.length];
		for (int i=0;i<strs.length;i++){
			strs[i] = ((Text)writables[i]).toString();
		}
		return strs;
	}
	
	public static void main(String[] args){
		String[] a={"23rsfsdf","sfewdsfds"};
		String[] b={"e9jsdjfsd","3o28hsdf"};
		TextArrayWritable m,n;
		m= new TextArrayWritable(a);
		n= new TextArrayWritable(b);
		System.out.println(m.toString());			
	}
	
	public String toString(){
		return StringUtils.join(toStringArray());
	}
}
