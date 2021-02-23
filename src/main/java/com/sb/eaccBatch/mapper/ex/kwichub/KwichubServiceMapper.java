package com.sb.eaccBatch.mapper.ex.kwichub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KwichubServiceMapper {
	
	int insertKwichubCardAcquire(Map<String, Object> map);
	int insertKwichubCardApproval(Map<String, Object> map);
	int insertKwichubCardBasic(Map<String, Object> map);
	int insertKwichubCardBill(Map<String, Object> map);
	int insertKwichubeTax(Map<String, Object> map);
	int insertCardScrapBasic(Map<String, Object> map);
	int insertCardScrapAppr(Map<String, Object> map);
	int nextVal();
	
	int updateKwichubCardAcquire(Map<String, Object> map);
	int updateKwichubCardApproval(Map<String, Object> map);
	int updateKwichubCardBasic(Map<String, Object> map);
	int updateKwichubCardBill(Map<String, Object> map);
	int updateKwichubeTax(Map<String, Object> map);
	
	int deleteKwichubCardAcquire(Map<String, Object> map);
	int deleteKwichubCardApproval(Map<String, Object> map);
	int deleteKwichubCardBasic(Map<String, Object> map);
	int deleteKwichubCardBill(Map<String, Object> map);
	int deleteKwichubeTax(Map<String, Object> map);
	
	List<HashMap<String , String >> searchKwichubCardAcquire(Map map);
	List<HashMap<String , String >> searchKwichubCardApproval(Map map);
	List<HashMap<String , String >> searchKwichubCardBasic(Map map);
	List<HashMap<String , String >> searchKwichubCardBill(Map map);
	List<HashMap<String , String >> searchKwichubeTax(Map map);
}
