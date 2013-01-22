package com.caphael.mywritables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;


public class OrderIntPairWritable extends IntPairWritable{
    public enum ORDER{ASC,DESC,DEF};

	private IntWritable FIRST=null;
    private IntWritable SECOND=null;
    
    public OrderIntPairWritable() {
        set(new IntWritable(), new IntWritable(),null);
    }
    
    public OrderIntPairWritable(int first, int second) {
        set(new IntWritable(first), new IntWritable(second),null);
    }
    
    public OrderIntPairWritable(int first, int second, ORDER order) {
        set(new IntWritable(first), new IntWritable(second),order);
    }

    public OrderIntPairWritable(int[] values) {
        set(new IntWritable(values[0]), new IntWritable(values[1]), ORDER.DEF);
    }
    
    public OrderIntPairWritable(int[] values, ORDER order) {
        set(new IntWritable(values[0]), new IntWritable(values[1]), order);
    }
    
    public OrderIntPairWritable(IntWritable first, IntWritable second) {
	    set(first, second,null);
	}

	public OrderIntPairWritable(IntWritable first, IntWritable second, ORDER order) {
	    set(first, second, order);
	}

	public void set(IntWritable first, IntWritable second, ORDER order) {
    	if (ORDER.ASC == order){
            this.FIRST = first.compareTo(second)<=0 ? first:second;
            this.SECOND = first.compareTo(second)>0 ? first:second;
    	} else if(ORDER.DESC == order)
    	{
    	    this.FIRST = first.compareTo(second)>=0 ? first:second;
            this.SECOND = first.compareTo(second)<0 ? first:second;
    	}else{
    		this.FIRST=first;
    		this.SECOND=second;
    	}
    }
    
    public void set(IntWritable first, IntWritable second){
    	set(first,second,ORDER.DEF);
    }

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OrderIntPairWritable ip1 = new OrderIntPairWritable(123,243,ORDER.ASC);
		OrderIntPairWritable ip2 = new OrderIntPairWritable(7645,324,ORDER.DESC);

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));

	}
}