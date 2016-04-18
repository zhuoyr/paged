package com.zhuogg.paged;

import java.util.Map;

import com.zhuogg.paged.json.Config;
import com.zhuogg.paged.json.JsonFetcher;
import com.zhuogg.paged.json.JsonParser;
import com.zhuogg.paged.json.JsonRequester;
import com.zhuogg.paged.json.JsonRunner;
import com.zhuogg.paged.util.Tag;

public class LaGou extends JsonRunner{
	static String URL = "http://www.lagou.com/jobs/positionAjax.json?"
			+ "city=%E7%A6%8F%E5%B7%9E&first=false&pn=${pageNo}&kd=java";

	public LaGou(Config config) {
		super(config);
	}

	public static void main(String[] args) {
		Config config=new Config(URL, 1, 15);
		new LaGou(config).run();
		
		Tag.printAll();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onHandle(Map raw) {
		
		Tag.getTag((String) raw.get("positionType")).addNum( (String) raw.get("workYear")+","+(String) raw.get("salary"));
	}

	@Override
	protected void onInit(JsonRequester mRequester, JsonFetcher mFetcher,
			JsonParser mParser) {
		//请求设置
		mRequester.addMapping("Connection","keep-alive");
		mRequester.addMapping("Pragma","no-cache");
		mRequester.addMapping("Cache-Control","no-cache");
		mRequester.addMapping("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		mRequester.addMapping("Upgrade-Insecure-Requests","1");
		mRequester.addMapping("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36");
		mRequester.addMapping("Accept-Encoding","gzip, deflate, sdch");
		mRequester.addMapping("Accept-Language","zh-CN,zh;q=0.8");
		
		//分页信息设置
		mFetcher.addMapping(JsonFetcher.OK, "code == 0" );
		mFetcher.addMapping(JsonFetcher.PAGE_NO, "content.pageNo"  );
		mFetcher.addMapping(JsonFetcher.OPT_PAGE_SIZE, "content.pageSize");
		mFetcher.addMapping(JsonFetcher.OPT_PAGE_SIZE, "content.pageCount" );
		mFetcher.addMapping(JsonFetcher.ROWS, "content.result" );
		
		//列表项映射： 结果（onHandle的参数）列名  -> 用ognl表达式从返回的json提取信息 （onHandle里返回map，可以通过这个作key获取值）
		mParser.addMapping("companyId", "companyId");
		mParser.addMapping("positionName", "positionName");
		mParser.addMapping("positionType", "positionType");
		mParser.addMapping("workYear", "workYear");
		mParser.addMapping("salary", "salary");
		mParser.addMapping("formatCreateTime", "formatCreateTime");
		mParser.addMapping("positionFirstType", "positionFirstType");
		mParser.addMapping("companyLabelFirst", "companyLabelList.{^true}");
		
	}
}
