package com.zhuogg.paged.json;




public class Config {
	public String url;
	private Integer pageNo;
	private Integer pageSize;
	public Integer maxRequestNum=-1;
	

	public Config(String url, Integer pageNo, Integer pageSize) {
		super();
		this.url = url;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Config(String url, Integer pageNo, Integer pageSize,Integer maxRequestNum) {
		 this(url, pageNo, pageSize);
		 this.maxRequestNum=maxRequestNum;
	}

	public String getUrl() {
		String replaceUrl = url.replaceAll("\\$\\{pageSize\\}", this.pageSize+"").replaceAll("\\$\\{pageNo\\}", this.pageNo+"");
//		System.out.println(replaceUrl);
		return  replaceUrl;
	}


	public void resetParam() {
		 this.pageNo ++;
	}


}