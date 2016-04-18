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
public class JsonParser {
	private static Logger logger=Logger.getLogger(JsonParser.class);
	private String el; 
	private Object accessor;
	 
	private Map<String,String> mappings;

	public void addMapping(String column,String expression){
		mappings.put(column, expression);
	}
	
	public void removeMapping(String column){
		mappings.remove(column);
	}
	public JsonParser() throws OgnlException {
		 this(new HashMap<String,String>());
	}
	
	public JsonParser(Map<String, String> mappings) throws OgnlException {
		super();
		el="__row";
		this.mappings=mappings; 
	}
	


	/**
	 * 预编译解析器
	 * @throws OgnlException
	 */
	public void compile() throws OgnlException {
		if(accessor == null){
			String expression="";
			for(Entry<String, String> mapping:mappings.entrySet()){
				expression+=el+"."+mapping.getKey()+"="+mapping.getValue()+",";
			}
			expression+="1";
			logger.info(expression);
			accessor=Ognl.parseExpression(expression);
			logger.info(accessor);
		}
	}
	
	/**
	 * 映射
	 * @param row
	 * @param root
	 * @return
	 * @throws OgnlException 
	 */
	public <T> T parse(T row,T root) throws OgnlException {
		compile();
		if(accessor != null && row != null){
			logger.info("parsing the root");
			try {
				Ognl.setValue(this.el, root, row);
				Ognl.getValue(this.accessor, root);
			} catch (OgnlException e) {
				logger.error(e);
			}
		}else{
			logger.error("accessor or row not ok");
		}
		return row;
	}
}