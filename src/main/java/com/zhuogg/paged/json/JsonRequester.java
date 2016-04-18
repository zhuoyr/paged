package com.zhuogg.paged.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ognl.OgnlException;

import org.apache.log4j.Logger;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 请求器
 * @author zhuogg
 * 2016-3-17
 */
public class JsonRequester {
	private static Logger logger=Logger.getLogger(JsonRequester.class);
	private Request.Builder mBuilder; 
	private Map<String,String> mappings;

	public void addMapping(String column,String expression){
		mappings.put(column, expression);
	}
	
	public void removeMapping(String column){
		mappings.remove(column);
	}
	
	public JsonRequester() {
		 this(new HashMap<String,String>());
	}
	
	public JsonRequester(Map<String, String> mappings){
		super();
		this.mappings=mappings; 
	}
	

	/**
	 * 预编译解析器
	 * @throws OgnlException
	 */
	public void prehandle() {
		if(mBuilder==null){
			mBuilder=new Request.Builder();
			for(Entry<String, String> mapping:mappings.entrySet()){
				mBuilder.addHeader(mapping.getKey(), mapping.getValue());
			}
		}
	}
	
	/**
	 * 请求并获取一下url的内容
	 * @param url
	 * @return
	 */
	public String getContent(String url) {
		prehandle();
		OkHttpClient client=new OkHttpClient();
		Request req=mBuilder.url(url).build();
		String content = "{}";
		try {
			Response resp = client.newCall(req).execute();
			content=resp.body().string();
			logger.info("content-length:"+content.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}