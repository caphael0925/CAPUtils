package com.caphael.mywritables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

public class TextTripleWritable implements WritableComparable<TextTripleWritable> {
    private static String SEPSTR = ",";
	private Text FIRST=null;
    private Text SECOND=null;
    private Text THIRD=null;

    public TextTripleWritable() {
        set(new Text(), new Text(), new Text());
    }

    public TextTripleWritable(String first, String second,String third) {
        set(new Text(first), new Text(second), new Text(third));
    }

    public TextTripleWritable(Text first, Text second,Text third) {
        set(first, second, third);
    }

    public TextTripleWritable(String[] values) {
        set(new Text(values[0]), new Text(values[1]), new Text(values[2]));
    }
    
    public void set(Text first, Text second, Text third) {
        FIRST = first;
        SECOND = second;
        THIRD = third;
    }

    public Text getFirst() {
        return FIRST;
    }

    public Text getSecond() {
        return SECOND;
    }

    public Text getThird(){
    	return THIRD;
    }
    
    public void write(DataOutput out) throws IOException {
        FIRST.write(out);
        SECOND.write(out);
        THIRD.write(out);
    }

    public void readFields(DataInput in) throws IOException {
        FIRST.readFields(in);
        SECOND.readFields(in);
        THIRD.readFields(in);
    }

    @Override
    public int hashCode() {
        return FIRST.hashCode() * 163 + SECOND.hashCode() + THIRD.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TextTripleWritable) {
            TextTripleWritable tp = (TextTripleWritable) o;
            return FIRST.equals(tp.FIRST) && SECOND.equals(tp.SECOND) && THIRD.equals(tp.THIRD);
        }
        return false;
    }

    @Override
    public String toString() {
        return FIRST + SEPSTR + SECOND + SEPSTR + THIRD;
    }

    public int compareTo(TextTripleWritable tt) {
        int cmp1 = FIRST.compareTo(tt.FIRST);
    	if ( cmp1 == 0) {
    		int cmp2 = SECOND.compareTo(tt.SECOND);
        	if(cmp2 == 0){
        		return THIRD.compareTo(tt.THIRD);
        	}else{
        		return cmp2;
        	}
        }else{
            return cmp1;
        }
    }
    
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextTripleWritable ip1 = new TextTripleWritable("aaa","bsd","swe");
		TextTripleWritable ip2 = new TextTripleWritable("aaa","bsd","pwg");

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));



	}
}
