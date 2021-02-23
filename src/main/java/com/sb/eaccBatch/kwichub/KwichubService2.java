package com.sb.eaccBatch.kwichub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.eaccBatch.mapper.ex.kwichub.KwichubServiceMapper;
import com.sb.eaccBatch.mapper.ex.kwichub.KwichubServiceMapper2;
import com.sb.eaccBatch.mapper.ex.kwichub.KwichubSetupMapper;

@Service
public class KwichubService2 {

	@Autowired
	private KwichubServiceMapper2 kwichubServiceMapper2;
	@Autowired
	private KwichubServiceMapper kwichubServiceMapper;
	@Autowired
	private KwichubSetupMapper kwichubSetupMapper;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	// 카드 리스트 삽입
	@Transactional
	public List<Map<String, Object>> insert_Basic() throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		
		json.put("CHANNEL", "EAC");
		json.put("CORP_BIZ_NO", "2108601425");

		map.put("KH_LIST", "KH_CARD_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); 
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token bada266366b64146a0bf2f58856f91d284b7ba90");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);

		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				int hub_if_key = kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).put("HUB_IF_KEY", hub_if_key);
					
					
				}
				System.out.println(list.size() + "개 처리완료 되었습니다.");
			} else {
				System.out.println(jObject.get("ERR_MSG"));
			}
		} else {
			System.out.println(jObject.get("errMsg"));
		}
		connection.disconnect();
		return list;
	}

	// 카드매입조회 삽입
	@Transactional
	public void insert_Acquire(String nextDay) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
		Date time = new Date();
		String currDate = dateFormat.format(time);
		
		json.put("CHANNEL", "EAC");
		json.put("CORP_BIZ_NO", "2108601425");
		
		if(nextDay != null) json.put("ST_DT", nextDay);
		else json.put("ST_DT", "20200101");
		
		json.put("ED_DT", currDate);

		map.put("KH_LIST", "KH_ACQU_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); 
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token bada266366b64146a0bf2f58856f91d284b7ba90");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);

		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				int hub_if_key = kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).put("HUB_IF_KEY", hub_if_key);
					kwichubServiceMapper2.insertKHCardAcquireList(list.get(i));
				}
				System.out.println(list.size() + "개 처리완료 되었습니다.");
			} else {
				System.out.println(jObject.get("ERR_MSG"));
			}
		} else {
			System.out.println(jObject.get("errMsg"));
		}
		connection.disconnect();
	}

	// 카드승인내역 삽입
	@Transactional
	public void insert_Approval(String nextDay) throws JSONException, IOException {
		
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
		Date time = new Date();
		String currDate = dateFormat.format(time);
		
		json.put("CHANNEL", "EAC");
		json.put("CORP_BIZ_NO", "2108601425");
		
		if(nextDay != null) json.put("ST_DT", nextDay);
		else json.put("ST_DT", "20200101");
		
		json.put("ED_DT", currDate);

		map.put("KH_LIST", "KH_APPR_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); 
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token bada266366b64146a0bf2f58856f91d284b7ba90");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);

		if (jObject.get("result").equals("SUCCESS")) {
			System.out.println("jObject.get(\"result\").equals(\"SUCCESS\"):  " + jObject.get("result"));
			jObject = jObject.getJSONObject("data");
			System.out.println("jObject.get(\"ERR_MSG\"):  " + jObject.get("ERR_MSG"));
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				int hub_if_key = kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).put("HUB_IF_KEY", hub_if_key);
					kwichubServiceMapper2.insertKHCardApprList(list.get(i));
				}
				
				System.out.println(list.size() + "개 처리완료 되었습니다.");
			} else {
				System.out.println(jObject.get("ERR_MSG"));
			}
		} else {
			System.out.println(jObject.get("errMsg"));
		}
		connection.disconnect();
	}

	// 카드�?구내?�� ?��?��
	@Transactional
	public void insert_Bill(String nextMonth) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		
		json.put("CHANNEL", "EAC");
		json.put("CORP_BIZ_NO", "2108601425");
		json.put("BILL_MONTH", nextMonth);

		map.put("KH_LIST", "KH_BILL_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); 
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token Token bada266366b64146a0bf2f58856f91d284b7ba90");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);

		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				int hub_if_key =kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).put("HUB_IF_KEY", hub_if_key);
					kwichubServiceMapper2.insertKHCardBillList(list.get(i));
				}
				System.out.println(list.size() + "개 처리완료 되었습니다.");
			} else {
				System.out.println(jObject.get("ERR_MSG"));
			}
		} else {
			System.out.println(jObject.get("errMsg"));
		}
		connection.disconnect();
	}

	// 홈텍스 세금 매입매출내역 삽입
	@Transactional
	public void insert_eTax(String nextDay) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyyMMdd");
		Date time = new Date();
		String currDate = dateFormat.format(time);
		
		json.put("CHANNEL", "EAC");
		json.put("CORP_BIZ_NO", "2108601425");
		
		if(nextDay != null) json.put("ST_DT", nextDay);
		else json.put("ST_DT", "20200101");
		
		json.put("ED_DT", currDate);
		map.put("KH_LIST", "KH_HTAX_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); 
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token Token Token bada266366b64146a0bf2f58856f91d284b7ba90");
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);
		
		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				int hub_if_key =kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).put("HUB_IF_KEY", hub_if_key);
					kwichubServiceMapper2.insertKHTaxList(list.get(i));
				}
				System.out.println(list.size() + "개 처리완료 되었습니다.");
			} else {
				System.out.println(jObject.get("ERR_MSG"));
			}
		} else {
			System.out.println(jObject.get("errMsg"));
		}
		connection.disconnect();
	}

	// 법인카드 한도조회
	@Transactional
	public Map<String, Object> insert_limit(Map<String, String> paramMap) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;

		map.put("KH_LIST", "KH_LIMIT_LIST");
		postUrl = new URL(kwichubServiceMapper2.KHListUrl(map));

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
		connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token " + kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE"));
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);
		
		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERR_MSG").equals("정상")) {
				JSONArray jArray = jObject.getJSONArray("LIST");
				list = getListMapFromJsonArray(jArray);

				kwichubServiceMapper2.nextVal();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).putAll(paramMap);
					kwichubServiceMapper2.insertKHCardLimitList(list.get(i));
				}
				map.put("msg", list.size() + "개의 데이터가 처리 되었습니다.");
			} else {
				map.put("msg", jObject.get("ERR_MSG").toString());
			}
		} else {
			map.put("msg", jObject.get("errMsg"));
		}
		connection.disconnect();
		return map;
	}

	// 법인카드 전카드조회
	@Transactional
	public Map<String, Object> insert_ScrapBasic(Map<String, String> paramMap) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		if (kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE").equals("") || kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE").equals("")
				|| kwichubSetupMapper.selectScrapPw(paramMap).get("CONFIG_VALUE").equals("")) {
			map.put("msg", "데이터를 불러올 수 없습니다. (인증서 필수 정보 누락)");
			return map;
		}
		json.put("FCODE", paramMap.get("fcode1"));
		json.put("LOGINID", kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE"));
		json.put("LOGINPWD", kwichubSetupMapper.selectScrapPw(paramMap).get("CONFIG_VALUE"));

		postUrl = new URL("https://datahub-dev.scraping.co.kr/scrap/fm/card/CardInquiryID");

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
		connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token " + kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE"));
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);
		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERRMSG").equals("")) {
				if (jObject.has("LIST") == true) {
					JSONArray jArray = jObject.getJSONArray("LIST");
					list = getListMapFromJsonArray(jArray);

					kwichubServiceMapper2.nextVal();
					for (int i = 0; i < list.size(); i++) {
						list.get(i).put("SEQ_ROWNUM", i + 1);
						list.get(i).put("FCODE", paramMap.get("fcode1"));
						list.get(i).put("LOGINID", kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE"));
						kwichubServiceMapper.insertCardScrapBasic(list.get(i));
					}
					map.put("msg", list.size() + "개의 데이터가 처리 되었습니다.");
				} else {
					map.put("msg", "법인카드 정보가 없습니다.");
				}
			} else {
				map.put("msg", jObject.get("ERRMSG"));
			}
		} else {
			map.put("msg", jObject.get("errMsg"));
		}
		connection.disconnect();
		return map;
	}

	// 법인카드 카드승인내역조회
	@Transactional
	public Map<String, Object> insert_ScrapAppr(Map<String, String> paramMap) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		if (kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE").equals("") || paramMap.get("fcode") == "" || kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE").equals("")
				|| kwichubSetupMapper.selectScrapPw(paramMap).get("CONFIG_VALUE").equals("") || paramMap.get("cardnum") == "") {
			map.put("msg", "데이터를 불러올 수 없습니다. (인증서 필수 정보 누락)");
			return map;
		}

		json.put("FCODE", paramMap.get("fcode"));
		json.put("LOGINID", kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE"));
		json.put("LOGINPWD", kwichubSetupMapper.selectScrapPw(paramMap).get("CONFIG_VALUE"));
		json.put("CARDNUM", paramMap.get("cardnum"));
		json.put("STARTDATE", paramMap.get("SEARCH_FR_DT3").toString().replaceAll("[^\\d]", ""));
		json.put("ENDDATE", paramMap.get("SEARCH_TO_DT3").toString().replaceAll("[^\\d]", ""));
		json.put("APPHISTORYVIEW", "1");

		postUrl = new URL("https://datahub-dev.scraping.co.kr/scrap/fm/card/ApprovalHistoryInquiryID");

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
		connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token " + kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE"));
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);
		if (jObject.get("result").equals("SUCCESS")) {
			jObject = jObject.getJSONObject("data");
			if (jObject.get("ERRMSG").equals("")) {
				if (jObject.has("LIST") == true) {
					JSONArray jArray = jObject.getJSONArray("LIST");
					list = getListMapFromJsonArray(jArray);

					kwichubServiceMapper2.nextVal();
					for (int i = 0; i < list.size(); i++) {
						list.get(i).put("SEQ_ROWNUM", i + 1);
						list.get(i).put("FCODE", paramMap.get("fcode"));
						list.get(i).put("LOGINID", kwichubSetupMapper.selectScrapId(paramMap).get("CONFIG_VALUE"));
						kwichubServiceMapper.insertCardScrapAppr(list.get(i));
					}
					map.put("msg", list.size() + "개의 데이터가 처리 되었습니다.");
				} else {
					map.put("msg", "해당 기간의 법인카드 승인내역이 없습니다.");
				}
			} else {
				map.put("msg", jObject.get("ERRMSG"));
			}
		} else {
			map.put("msg", jObject.get("errMsg"));
		}
		connection.disconnect();
		return map;
	}

	// 기웅 인증서 ID 조회
	@Transactional
	public Map<String, Object> search_CertId(Map<String, Object> paramMap) throws JSONException, IOException {
		Map<String, Object> map = new HashMap();

		JSONObject json = new JSONObject();
		URL postUrl = null;
		if (kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE").equals("") || kwichubSetupMapper.selectCertName(paramMap).get("CONFIG_VALUE").equals("") || kwichubSetupMapper.selectCertPw(paramMap).get("CONFIG_VALUE").equals("")
				|| kwichubSetupMapper.selectCertDer(paramMap).get("CONFIG_VALUE").equals("") || kwichubSetupMapper.selectCertKey(paramMap).get("CONFIG_VALUE").equals("")) {
			map.put("rs", "fail");
			return map;
		}
		json.put("P_TYPE", "CERT");
		json.put("P_CERTNAME", kwichubSetupMapper.selectCertName(paramMap).get("CONFIG_VALUE"));
		json.put("P_CERTPWD", kwichubSetupMapper.selectCertPw(paramMap).get("CONFIG_VALUE"));
		json.put("P_SIGNCERT_DER", kwichubSetupMapper.selectCertDer(paramMap).get("CONFIG_VALUE"));
		json.put("P_SIGNPRI_KEY", kwichubSetupMapper.selectCertKey(paramMap).get("CONFIG_VALUE"));

		postUrl = new URL("https://datahub-dev.scraping.co.kr/sapi/certificate/create");

		String body = json.toString();
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true); // xml내용을 전달하기 위해서 출력 스트림을 사용
		connection.setInstanceFollowRedirects(false); // Redirect처리 하지 않음
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Token " + kwichubSetupMapper.selectDerToken(paramMap).get("CONFIG_VALUE"));
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		OutputStream os = connection.getOutputStream();
		os.write(body.getBytes());
		os.flush();
		os.close();

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
		String output;
		output = br.readLine();
		JSONObject jObject = new JSONObject(output);
		if (jObject.get("result").equals("SUCCESS")) {
			map.put("cert_id", jObject.get("CERT_ID"));
		} else {
			map.put("msg", jObject.get("errMsg"));
		}
		connection.disconnect();
		return map;
	}

	// JsonObject -> Map
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) {
		Map<String, Object> map = null;

		try {
			map = new ObjectMapper().readValue(jsonObj.toString(), Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	// JsonArray -> List<Map>
	private List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) throws JSONException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (jsonArray != null) {
			int jsonSize = jsonArray.length();
			for (int i = 0; i < jsonSize; i++) {
				Map<String, Object> map = getMapFromJsonObject((JSONObject) jsonArray.get(i));
				list.add(map);
			}
		}
		return list;
	}
}
