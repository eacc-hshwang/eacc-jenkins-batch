package com.sb.eaccBatch.mapper.ex.kwichub;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KwichubSetupMapper {
	
	int saveDerFile(Map<String, Object> map);
	int saveKeyFile(Map<String, Object> map);
	int saveDerId(Map<String, Object> map);
	int saveDerPw(Map<String, Object> map);
	int saveDerPPw(Map<String, Object> map);
	int saveDerToken(Map<String, Object> map);
	int saveDerIv(Map<String, Object> map);
	int saveDerCard(Map<String, Object> map);
	int saveCertId(Map<String, Object> map);
	
	HashMap<String, String> selectEncIv(Map map);
	HashMap<String, String> selectEncKey(Map map);
	HashMap<String, String> selectScrapId(Map map);
	HashMap<String, String> selectScrapPw(Map map);
	HashMap<String, String> selectCertName(Map map);
	HashMap<String, String> selectCertPw(Map map);
	HashMap<String, String> selectCertDer(Map map);
	HashMap<String, String> selectCertKey(Map map);
	HashMap<String, String> selectDerToken(Map map);
	HashMap<String, String> selectHTId(Map map);
	HashMap<String, String> selectHTPw(Map map);

}
