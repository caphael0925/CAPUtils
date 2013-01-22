package com.caphael.mywritables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;


public class LongPairWritable implements WritableComparable<LongPairWritable> {

	private LongWritable FIRST=null;
    private LongWritable SECOND=null;
    private static String SEPSTR = ",";

    public LongPairWritable() {
        set(new LongWritable(), new LongWritable());
    }

    public LongPairWritable(Long first, Long second) {
        set(new LongWritable(first), new LongWritable(second));
    }

    public LongPairWritable(LongWritable first, LongWritable second) {
        set(first, second);
    }
    
    public LongPairWritable(Long[] values) {
        set(new LongWritable(values[0]), new LongWritable(values[1]));
    }
    
    public void set(LongWritable first, LongWritable second){
      	FIRST=first;
    	SECOND=second;
    }

    public LongWritable getFirst() {
        return FIRST;
    }

    public LongWritable getSecond() {
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
        if (o instanceof LongPairWritable) {
            LongPairWritable tp = (LongPairWritable) o;
            return (FIRST.equals(tp.FIRST) && SECOND.equals(tp.SECOND))||(FIRST.equals(tp.SECOND) && SECOND.equals(tp.FIRST));
        }
        return false;
    }

    @Override
    public String toString() {
        return FIRST + SEPSTR + SECOND;
    }

    public int compareTo(LongPairWritable ip) {
        int cmp = FIRST.compareTo(ip.FIRST);
        if (cmp != 0) {
            return cmp;
        }
        return SECOND.compareTo(ip.SECOND);
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LongPairWritable ip1 = new LongPairWritable(Long.parseLong("98"),Long.parseLong("32"));
		LongPairWritable ip2 = new LongPairWritable(Long.parseLong("34"),Long.parseLong("646"));

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));

	}
}