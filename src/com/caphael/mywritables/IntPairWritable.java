package com.caphael.mywritables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

public class IntPairWritable implements WritableComparable<IntPairWritable> {

	private IntWritable FIRST=null;
    private IntWritable SECOND=null;
    private static String SEPSTR = ",";
    
    public IntPairWritable() {
        set(new IntWritable(), new IntWritable());
    }
    
    public IntPairWritable(int first, int second) {
        set(new IntWritable(first), new IntWritable(second));
    }
    
 

    public IntPairWritable(int[] values) {
        set(new IntWritable(values[0]), new IntWritable(values[1]));
    }
    

    
    public IntPairWritable(IntWritable first, IntWritable second) {
	    set(first, second);
	}


	public void set(IntWritable first, IntWritable second) {
    	FIRST=first;
    	SECOND=second;
    }

    public IntWritable getFirst() {
        return FIRST;
    }

    public IntWritable getSecond() {
        return SECOND;
    }

    public void write(DataOutput out) throws IOException {
        FIRST.write(out);
        SECOND.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        FIRST.readFields(in);
        SECOND.readFields(in);
    }

    @Override
    public int hashCode() {
        return FIRST.hashCode() * 163 + SECOND.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntPairWritable) {
            IntPairWritable tp = (IntPairWritable) o;
            return (FIRST.equals(tp.FIRST) && SECOND.equals(tp.SECOND))||(FIRST.equals(tp.SECOND) && SECOND.equals(tp.FIRST));
        }
        return false;
    }

    @Override
    public String toString() {
        return FIRST + SEPSTR + SECOND;
    }

    public int compareTo(IntPairWritable ip) {
        int cmp = FIRST.compareTo(ip.FIRST);
        if (cmp != 0) {
            return cmp;
        }
        return SECOND.compareTo(ip.SECOND);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IntPairWritable ip1 = new IntPairWritable(123,243);
		IntPairWritable ip2 = new IntPairWritable(7645,324);

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));
		String f="";
		System.out.println(Integer.parseInt(""==f?"0":f));


	}
}
