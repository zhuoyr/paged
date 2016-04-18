package com.zhuogg.paged.json;

import java.util.Iterator;

import org.apache.log4j.Logger;

public class URLIterator implements Iterator<String> {
	private static Logger logger=Logger.getLogger(URLIterator.class);
	private Config config ;
	private int count;
  
	 
	public URLIterator(Config config) {
		super();
		this.config = config;
	}
  
	public boolean hasNext() {
		return  (config.maxRequestNum <0 || count<config.maxRequestNum);
	}

	public String next() {
		count++;
		System.out.println("count now:"+count);
		logger.info("count now:"+count);
		String url = this.config.getUrl();
		this.config.resetParam();
		return url;
	}

	public void remove() {
		 System.out.println(" Not Implemention ");
	}

	 

}
