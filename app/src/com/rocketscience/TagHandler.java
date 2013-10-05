package com.rocketscience;

import java.util.HashMap;
import java.util.Map;

public class TagHandler {

	private static TagHandler instance;
	private Map<String, Integer> map;
	private int count;
	
	private TagHandler()
	{
		map = new HashMap<String, Integer>();
		count = 0;
	}
	
	public static TagHandler getInstance()
	{
		if(instance == null)
		{
			instance = new TagHandler();
		}
		return instance;
	}
	
	public boolean addTag(String name)
	{
		if(map.containsKey(name)) return false;
		map.put(name, count);
		count++;
		return true;
	}
	
	public int getTag(String name)
	{
		if(!map.containsKey(name)) return -1;
		return map.get(name);
	}
	
	
}
