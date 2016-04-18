package com.zhuogg.paged.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 计数/去重 结构
 * @author zhuogg
 * 2016-1-13
 */
public class Tag {
	private static final String Unkown = "unkown";
	static Map<String, Tag> tags = new TreeMap<String, Tag>();
	public static void printAll() {
		for (Entry<String, Tag> ent : tags.entrySet()) {
			System.out.println("[" + ent.getKey() + "]");
			ent.getValue().print();
		}
	}

	public Map<String, Integer> numDist = new TreeMap<String, Integer>();
	private Map<String, Set<String>> userDist = new HashMap<String, Set<String>>();

	public static Tag getTag(String val) {
		Tag tag = tags.get(val);
		if (tag == null) {
			tag = new Tag();
			tags.put(val, tag);
		}
		return tag;
	}

	public void addNum(String key) {
		if(key == null){
			key=Unkown;
		}
		Integer count = numDist.get(key);
		if (null == count) {
			count = 0;
		}
		numDist.put(key, count + 1);
	}

	public void addUser(String key, String uin) {
		Set<String> set = userDist.get(key);
		if (null == set) {
			set = new HashSet<String>();
			userDist.put(key, set);
		}
		set.add(uin);
	}

	private void setSize() {
		for (Entry<String, Set<String>> ent : userDist.entrySet()) {
			Set<String> set = ent.getValue();
			Integer size = set == null ? 0 : set.size();
			numDist.put(ent.getKey(), size);
		}
		userDist = null;
	}

	public void print() {
		setSize();
		for (Entry<String, Integer> ent : numDist.entrySet()) {
			String key = ent.getKey();
			Integer num = ent.getValue();
			System.out.println(key + ":" + num);
		}
		System.out.println();
	}
}
