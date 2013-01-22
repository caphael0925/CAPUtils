package com.caphael.mywritables;

public class OrderLongPairWritable extends LongPairWritable {
    public enum ORDER{DEF,ASC,DESC};

    public OrderLongPairWritable() {
        set(new LongWritable(), new LongWritable(),null);
    }

    public OrderLongPairWritable(Long first, Long second) {
        set(new LongWritable(first), new LongWritable(second),null);
    }

    public OrderLongPairWritable(LongWritable first, LongWritable second) {
        set(first, second,null);
    }
    
    public OrderLongPairWritable(Long first, Long second, ORDER order) {
        set(new LongWritable(first), new LongWritable(second),order);
    }

    public OrderLongPairWritable(LongWritable first, LongWritable second, ORDER order) {
        set(first, second, order);
    }
    
    public void set(LongWritable first, LongWritable second, ORDER order) {
    	if (ORDER.DEF == order){
       		this.FIRST=first;
    		this.SECOND=second;
    	}
 


    	
    	
    	if (ORDER.ASC == order){
            this.FIRST = first.compareTo(second)<=0 ? first:second;
            this.SECOND = first.compareTo(second)>0 ? first:second;
    	} else if(ORDER.DESC == order)
    	{
    	    this.FIRST = first.compareTo(second)>=0 ? first:second;
            this.SECOND = first.compareTo(second)<0 ? first:second;
    	}else{
    		
    	}
    }
    
    public void set(LongWritable first, LongWritable second){
    	set(first,second,null);
    }

    public static void main(String[] args) {
		// TODO Auto-generated method stub
		LongPairWritable ip1 = new LongPairWritable(Long.parseLong("98"),Long.parseLong("32"),ORDER.ASC);
		LongPairWritable ip2 = new LongPairWritable(Long.parseLong("34"),Long.parseLong("646"),ORDER.DEF);

		System.out.println(ip1);
		System.out.println(ip2);
		System.out.println(ip1.equals(ip1));
		System.out.println(ip1.equals(ip2));
		System.out.println(ip1.compareTo(ip1));
		System.out.println(ip1.compareTo(ip2));

	}
}

}
