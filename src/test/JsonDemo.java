package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * { "name": "中国", "province": [{ "name": "黑龙江", "cities": { "city": ["哈尔滨",
 * "大庆"] } }, { "name": "广东", "cities": { "city": ["广州", "深圳", "珠海"] } }, {
 * "name": "台湾", "cities": { "city": ["台北", "高雄"] } }, { "name": "新疆", "cities":
 * { "city": ["乌鲁木齐"] } }] }
 * 
 * @author Bob
 *
 */
public class JsonDemo {
	public static void main(String[] args) {
		Map<String, List<String>> mapcity = new HashMap<>();
		Map<String, String> mapname = new HashMap<>();
		List<String> listCity = new ArrayList<>();
		listCity.add("广州");
		listCity.add("深圳");
		listCity.add("珠海");
		mapcity.put("city", listCity);
		System.out.println(mapcity);
		Map<String, Object> mapCityes = new HashMap<>();
		mapCityes.put("cities", mapcity);
		System.out.println(mapCityes);
		Object json = JSONObject.toJSON(mapCityes);
		System.out.println(json);
		
	}
}
