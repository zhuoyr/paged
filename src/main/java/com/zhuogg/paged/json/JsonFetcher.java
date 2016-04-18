package com.zhuogg.paged.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.log4j.Logger;



/**
 * 解析请求结果
 * @author zhuogg
 * 2016-3-17
 */
public class JsonFetcher {
	private static Logger logger=Logger.getLogger(JsonFetcher.class);
	public static final String OK="ok";
	public static final String ROWS="rows";
	public static final String PAGE_NO="pageNo";
	public static final String OPT_PAGE_SIZE="pageSize";
	public static final String OPT_PAGE_Count="pageCount";
	
	private Map<String,String> mappings;
	private String el; 
	private Object accessor;
 
	public JsonFetcher() throws OgnlException {
		this(new HashMap<String,String>());
	}
	
	public JsonFetcher(Map<String, String> mappings) throws OgnlException {
		super();
		el="cc_result";
		this.mappings=mappings; 
	}
	
	public void addMapping(String column,String expression){
		mappings.put(column, expression);
	}
	
	public void removeMapping(String column){
		mappings.remove(column);
	}
	
	/**
	 * 预编译解析器
	 * @throws OgnlException
	 */
	public void compile() throws OgnlException {
		if(accessor == null){
			logger.info("prepare for expression");
			String expression="";
			for(Entry<String, String> mapping:mappings.entrySet()){
				expression+=el+"."+mapping.getKey()+"="+mapping.getValue()+",";
			}
			expression+="1";
			
			logger.info(expression);
			logger.info(Ognl.parseExpression(expression));
			accessor=Ognl.parseExpression(expression);
			if(accessor ==null) logger.error("accessor is null");
		}
	}
	 

	/**
	 * 解析结果
	 * @param root
	 * @return
	 * @throws OgnlException 
	 */
	public <T>JsonResult<T> parse(T root) throws OgnlException {
		compile();
		JsonResult<T> result = new JsonResult<T>();
		if(accessor != null){
			logger.info("parsing the result");
			try {
				Ognl.setValue(this.el, root, result);
				Ognl.getValue(this.accessor, root);
			} catch (OgnlException e) {
				logger.error(e);
			}
		}else{
			logger.error("accessor not ok");
		}
		
		return result;
	}
}