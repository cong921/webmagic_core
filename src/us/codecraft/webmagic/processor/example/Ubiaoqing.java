package us.codecraft.webmagic.processor.example;

import java.io.IOException;
import java.util.List;

import us.codecraft.webmagic.HttpDownloadUtility;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Ubiaoqing implements PageProcessor{

    public static final String URL_LIST = "http://www\\.ubiaoqing\\.com/search/.*";
    public static final String URL_POST = "http://ubq\\.ubiaoqing\\.com/ubiaoqing[a-z0-9]+\\.gif?attname=";
    

//    public static final String URL_POST = "http://www.ccgp.gov.cn/cggg/zygg/zbgg/\\d+/t\\d+_\\d+\\.\\w+";

    private Site site = Site
            .me()
            .setDomain("http://www.ubiaoqing.com/")
            .setSleepTime(1000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    @Override
    public void process(Page page) {///div[3]/div[2]/div[1]/ul/li[2]/a
        //列表页
        if (page.getHtml().links().regex(URL_LIST).match()) {
        	List<String> all = page.getHtml().links().regex(URL_LIST).all();
//        	System.out.println(page.getHtml());//div[3]/div[2]/div[1]/ul/li[2]/a
        	System.out.println(all);
            page.addTargetRequests(all);
//            System.out.println("===============");
//            System.out.println(page.getHtml().xpath("").links().all().get(page.getHtml().links().all().size()-5));
//            System.out.println("===============");//div[3]/div[2]/div[2]/p/a[7]//body > div:nth-child(5) > div.vT_list_main > div.pagigation > p > a.next
            //div[3]/div[2]/div[2]/p/a[8]
            //文章页
        } else if (page.getHtml().links().regex(URL_POST).match()) {
        	List<String> all = page.getHtml().links().regex(URL_POST).all();
        	for (String string : all) {
        		try {
					HttpDownloadUtility.downloadFile(string, "D:/gif");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//        	List<String> all = page.getHtml().links().all();
//        	page.addTargetRequests(all);
        	
        	
        }
    }
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Ubiaoqing()).addUrl("http://www.ubiaoqing.com/")
                .run();
       
    }}
