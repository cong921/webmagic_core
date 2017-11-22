package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JubiPipleline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class JubiPageprocessor implements PageProcessor{

    public static final String URL_LIST = "http://www\\.ccgp\\.gov\\.cn/cggg/zygg/zbgg/index[_\\d+]*\\.htm";

    public static final String URL_POST = "http://www.ccgp.gov.cn/cggg/zygg/zbgg/\\d+/t\\d+_\\d+\\.\\w+";

    private Site site = Site
            .me()
            .setDomain("http://www.jubi.com")
            .setSleepTime(30000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    @Override
    public void process(Page page){
    	Json json = page.getJson();
    	List<JSONObject> jsonList=json.toList(JSONObject.class);
    	List<List<Object>> list=new ArrayList<>();
    	for (JSONObject json2 : jsonList) {
    		List<Object> list1=new ArrayList<Object>();
    		list1.add(json2.getString("date"));
    		list1.add(json2.getFloat("price"));
    		list1.add(json2.getFloat("amount"));
    		list1.add(json2.getInteger("tid"));
    		list1.add(json2.getString("type"));
    		list.add(list1);
		}
    	page.putField("list", list);
    	
    }
    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
    	while(true){
    	Spider.create(new JubiPageprocessor()).addUrl("http://www.jubi.com/api/v1/orders?coin=ifc").addPipeline(new JubiPipleline())
    	.run();
    	
    }
       
    }}

