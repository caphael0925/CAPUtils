package com.caphael.myjson;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JSONTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject root = new JSONObject();
		JSONObject joc = new JSONObject();
		JSONObject[] jocarray = new JSONObject[3];
		
		joc.put("name", "SendCount");
		joc.put("type", "Integer");
		
		jocarray[0]=joc;
		
		joc = new JSONObject();
		joc.put("name", "UserCount");
		joc.put("type", "Long");
		
		jocarray[1]=joc;

		joc = new JSONObject();
		joc.put("name", "AvgCount");
		joc.put("type", "Double");
		
		jocarray[2]=joc;
		
		root.put("columns", jocarray);
		
		System.out.println(root);
	}

}
