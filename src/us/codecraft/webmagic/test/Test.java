package us.codecraft.webmagic.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.selector.Json;

class Test {

	static boolean foo(char c) {

		System.out.print(c);

		return true;

	}

	public static void main(String[] argv) throws IOException {/*

		int i = 0;

		for (foo('A'); foo('B') && (i < 2); foo('C')) {

			i++;

			foo('D');

		}

	*/
	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Users/Bob/Desktop/Shadowsocks-3.4.2.1/gui-config.json"))));
	StringBuffer sb=new StringBuffer();
	String str="";
	while((str=br.readLine())!=null){
		sb.append(str);
	}
	Json json = new Json(sb.toString());
	JSONObject jsonObject=JSONObject.parseObject(sb.toString());
	Object object = jsonObject.getJSONArray("configs").getJSONObject(0).put("server","234");
	String string = jsonObject.getJSONArray("configs").getJSONObject(0).getString("server");
	System.out.println(string);
	
	
	}
	

}