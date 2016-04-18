package com.zhuogg.paged.json;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


/**
 * http://commons.apache.org/proper/commons-ognl/apidocs/index.html
 * 
 * OGNL
 * 
 * 
 * @author zhuogg
 * 2016-3-17
 */

public abstract class JsonRunner {
//	private static Logger logger=Logger.getLogger(JsonRequester.class);
	
	private Config config;
	
	public JsonRunner(Config config) {
		this.config = config;
	}
	
   
	 
	@SuppressWarnings("rawtypes")
	public void run(){
		try {
			
			
			JsonRequester mRequester=new JsonRequester();
			JsonFetcher mFetcher=new JsonFetcher();
			JsonParser mParser=new JsonParser();
			onInit(mRequester,
					mFetcher,mParser);
			
			Gson gson=new Gson();
			URLIterator ui=new URLIterator(config);
			while(ui.hasNext()){
				String result=mRequester.getContent(ui.next());
				//logger.info("content length:"+content.length());
				Map root = gson.fromJson(result, Map.class);
				JsonResult<Map> json = mFetcher.parse(root);
				if (json.isOk() && json.hasNext()) {
					for (Map raw : json.getRows()) {
						Map row=new HashMap();
						mParser.parse(row, raw);
						onHandle(row);
					}
				}else{
					//没有数据了哦
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 
	


	protected abstract void onInit(JsonRequester mRequester, JsonFetcher  mFetcher,JsonParser mParser);
	@SuppressWarnings("rawtypes")
	protected abstract void onHandle(Map raw);
}
