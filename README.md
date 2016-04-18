# paged
 json分页接口拉取框架

# 推荐使用方式
  > 建议只依赖 JsonRunner模板类 所开放的东西,毕竟小框架的结构和内部的东西后续有可能再修改和设计。
  
  步骤描述：
  * 继承和实现jsonRunner的方法
  * main 入口，指定config的参数,构建runner实例，执行run方法（注意url串可带两个自动替换标识：${pageNo},${pageSize}）
  * onInit 实现：将回传的几个对象，加上自己的配置
  * onHandle 处理数据，根据自己的业务逻辑来写代码喽
  
## 示例：拉取拉勾网的职位列表，并做分组统计
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




##执行结果
	[前端开发]
	1-3年,5k-8k:1
	1-3年,6k-12k:1
	1-3年,7k-10k:1
	1-3年,8k-15k:2
	1-3年,8k-16k:1
	3-5年,10k-13k:1
	
	[后端开发]
	1-3年,10k-20k:1
	1-3年,15k-30k:1
	1-3年,1k-2k:1
	1-3年,3k-6k:3
	1-3年,4k-6k:2
	1-3年,4k-7k:2
	1-3年,4k-8k:5
	1-3年,5k-10k:8
	1-3年,5k-8k:7
	1-3年,5k-9k:2
	1-3年,5k以上:1
	1-3年,6k-10k:4
	1-3年,6k-12k:7
	1-3年,6k-8k:3
	1-3年,6k-9k:2
	1-3年,7k-12k:2
	1-3年,7k-13k:2
	1-3年,7k-14k:2
	1-3年,7k-9k:2
	1-3年,8k-10k:1
	1-3年,8k-11k:1
	1-3年,8k-12k:1
	1-3年,8k-15k:1
	1-3年,8k-16k:2
	1-3年,9k-18k:1
	3-5年,10k-13k:1
	3-5年,10k-15k:7
	3-5年,10k-18k:1
	3-5年,10k-20k:5
	3-5年,15k-30k:1
	3-5年,5k-10k:1
	3-5年,6k-10k:1
	3-5年,6k-12k:9
	3-5年,6k-8k:1
	3-5年,7k-11k:1
	3-5年,7k-12k:1
	3-5年,7k-13k:1
	3-5年,7k-14k:1
	3-5年,7k-9k:1
	3-5年,8k-11k:1
	3-5年,8k-12k:6
	3-5年,8k-15k:7
	3-5年,8k-16k:3
	3-5年,9k-15k:1
	3-5年,9k-16k:1
	3-5年,9k-18k:1
	5-10年,10k-15k:1
	5-10年,10k-20k:1
	5-10年,15k-30k:1
	5-10年,20k-40k:1
	5-10年,5k-10k:1
	5-10年,8k-13k:1
	5-10年,8k-15k:1
	5-10年,8k-16k:3
	5-10年,8k以上:1
	5-10年,9k-18k:1
	不限,3k-5k:1
	不限,4k-8k:2
	不限,5k-10k:1
	不限,6k-10k:1
	不限,6k-12k:2
	不限,6k-8k:2
	不限,7k-10k:1
	不限,7k-11k:1
	不限,8k-10k:1
	不限,8k-11k:2
	不限,8k-15k:1
	应届毕业生,2k-4k:1
	
	[移动开发]
	不限,6k-12k:1
	
	[高端技术职位]
	不限,8k-10k:1

# 依赖类库
	<dependencies>

		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>


		<dependency>
			<groupId>com.squareup.okhttp</groupId>
			<artifactId>okhttp</artifactId>
			<version>2.3.0</version>
		</dependency>

		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.5</version>
		</dependency>

		<dependency>
			<groupId>ognl</groupId>
			<artifactId>ognl</artifactId>
			<version>3.1.2</version>
		</dependency>
		
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-log4j12</artifactId>
			<version>1.7.7</version>
		</dependency>
	</dependencies>
