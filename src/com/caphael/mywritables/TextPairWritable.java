package com.caphael.mywritables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextPairWritable implements WritableComparable<TextPairWritable> {
    private Text FIRST=null;
    private Text SECOND=null;
    private static String SEPSTR = ",";

    public TextPairWritable() {
        set(new Text(), new Text());
    }

    public TextPairWritable(String first, String second) {
        set(new Text(first), new Text(second));
    }

    public TextPairWritable(Text first, Text second) {
        set(first, second);
    }

    public TextPairWritable(String[] values) {
        set(new Text(values[0]), new Text(values[1]));
    }
    
    public void set(Text first, Text second) {
        this.FIRST = first;
        this.SECOND = second;
    }

    public Text getFirst() {
        return FIRST;
    }

    public Text getSecond() {
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
        if (o instanceof TextPairWritable) {
            TextPairWritable tp = (TextPairWritable) o;
            return FIRST.equals(tp.FIRST) && SECOND.equals(tp.SECOND);
        }
        return false;
    }

    @Override
    public String toString() {
        return FIRST + SEPSTR + SECOND;
    }

    public int compareTo(TextPairWritable tp) {
        int cmp = FIRST.compareTo(tp.FIRST);
        if (cmp != 0) {
            return cmp;
        }
        return SECOND.compareTo(tp.SECOND);
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextPairWritable ip1 = new TextPairWritable("aaa","bsd");
		TextPairWritable ip2 = new TextPairWritable("ser","swe");

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));



	}
}
