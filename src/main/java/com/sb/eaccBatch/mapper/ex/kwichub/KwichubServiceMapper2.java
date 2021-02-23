package com.sb.eaccBatch.mapper.ex.kwichub;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KwichubServiceMapper2 {
	
	int nextVal();
	int insertKHCardAcquireList(Map<String, Object> map);
	int insertKHCardApprList(Map<String, Object> map);
	int insertKHCardBillList(Map<String, Object> map);
	int insertKHCardLimitList(Map<String, Object> map);
	int insertKHCardList(Map<String, Object> map);
	int insertKHCashList(Map<String, Object> map);
	int insertKHTaxItemList(Map<String, Object> map);
	int insertKHTaxList(Map<String, Object> map);
	
	String KHListUrl(Map<String, Object> map);
}
