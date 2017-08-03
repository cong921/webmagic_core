package us.codecraft.webmagic.processor.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class IshadowxPageprocessor implements PageProcessor{

    public static final String URL_LIST = "http://www\\.ccgp\\.gov\\.cn/cggg/zygg/zbgg/index[_\\d+]*\\.htm";

    public static final String URL_POST = "http://www.ccgp.gov.cn/cggg/zygg/zbgg/\\d+/t\\d+_\\d+\\.\\w+";

    private Site site = Site
            .me()
            .setDomain("ss.ishadowx.com/").setSleepTime(100)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    @Override
    public void process(Page page){
    	BufferedReader br=null;
    	BufferedWriter bw=null;
    	try{
    	List<String> all = page.getHtml().xpath("//*[@id='portfolio']/div[2]/div[2]/div/div/div/div/div").all();
//    	System.out.println(all);
    	List<List<String>> listall=new ArrayList<List<String>>();
    	for (String str : all) {
    		String pattern = "<div.*?\\n.*<h4>IP Address:<span.*?>(.*?)<.*?</h4> \\n.*Port：(\\d+)<.*\\n.*<h4>Password:<span.*?>(\\d+).*\\n.*Method:(.*?)</h4>.*?\\n.*\\n.*?>(<a.*\n.*)*";

    		Pattern r = Pattern.compile(pattern);
    		Matcher m = r.matcher(str);
//    		System.out.println(m.matches());
    		if(m.matches())
    		{
    			List<String> list=new ArrayList<String>();
    			list.add(m.group(1));
    			list.add(m.group(2));
    			list.add(m.group(3));
    			list.add(m.group(4));
    			listall.add(list);
    		}
		}
//    	System.out.println(listall);
    	File file=new File("C:/Users/Bob/Desktop/Shadowsocks-3.4.2.1/gui-config.json");
		 br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		StringBuffer sb=new StringBuffer();
		String str="";
		while((str=br.readLine())!=null){
			sb.append(str);
		}
		Json json = new Json(sb.toString());
//		System.out.println(sb.toString());
		JSONObject jsonObject=JSONObject.parseObject(sb.toString());
		int num=jsonObject.getJSONArray("configs").toArray().length;
		System.out.println(listall.size());
		for (int index = 0; index < listall.size(); index++) {
			jsonObject.getJSONArray("configs").getJSONObject(index).put("server",listall.get(index).get(0));
			jsonObject.getJSONArray("configs").getJSONObject(index).put("server_port",listall.get(index).get(1));
			jsonObject.getJSONArray("configs").getJSONObject(index).put("password",listall.get(index).get(2));
			jsonObject.getJSONArray("configs").getJSONObject(index).put("method",listall.get(index).get(3));
		}
		if(file.exists())
			file.delete();
		 bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:/Users/Bob/Desktop/Shadowsocks-3.4.2.1/gui-config.json"))));
		bw.write(jsonObject.toJSONString());
//		System.out.println(jsonObject);
		bw.flush();
		
	} catch (IOException e) {
		e.printStackTrace();
	}finally {
		try {
			if(br!=null)
				br.close();
			if(bw!=null)
				bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
    }
    @Override
    public Site getSite() {
        return site;
    }
    public List<String> filterList(List<String> trs1){
   	 for(int i=0;i<trs1.size();i++){
      	  if(trs1.get(i)!=null&&"".equals(trs1.get(i).trim())){
      		  trs1.remove(i);
      		  i=i-1;
      		  if(i<0)
      			  i=0;
//      		  if(trs1.get(i).startsWith("附件")||trs1.get(i).startsWith("品目"))
//      			  trs1.remove(i);
      	  }
		}
		return trs1;
   }
    public static void main(String[] args) {
//        Spider.create(new ZbggPageProcessor()).addUrl("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index.htm").addPipeline(new FilePipeline("D:/string/"))
        Spider.create(new IshadowxPageprocessor()).addUrl("http://ss.ishadowx.com/index.html").addPipeline(new ConsolePipeline())
                .run();
       
    }}

