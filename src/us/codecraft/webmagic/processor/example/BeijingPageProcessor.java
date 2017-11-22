package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class BeijingPageProcessor implements PageProcessor {

//	public static final String URL_LIST = "https://www\\.itjuzi\\.com/investevents?prov=[\\u4e00-\\u9fa5]+&location=[\\w]+&scope=[\\d]+&currency=[\\d]+&date=[\\d]+&page=[\\d]+";
	public static final String URL_LIST = "https://zfxxgk\\.beijing\\.gov/cn\\?prov=.*?&location=.*?&scope=(\\d)+&round=(\\d)+&currency=(\\d)+&date=(\\d)+(&page=(\\d)+)*";
	public static final String URL_POST = "https://www\\.itjuzi\\.com/investevents/\\d+";
	private Site site = Site.me().setDomain("http://zfxxgk.beijing.gov.cn").setSleepTime(3000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	@Override
	public void process(Page page) {// /div[3]/div[2]/div[1]/ul/li[2]/a
//		System.out.println(page.getUrl());
		// 列表页
		if (true) {
			// System.out.println(page.getHtml());//div[3]/div[2]/div[1]/ul/li[2]/a
			page.addTargetRequests(page.getHtml().links().regex(URL_POST).all());
			boolean contains = page.getHtml().xpath("//div[contains(@class, 'ui-pagechange for-sec-bottom')]/a/text()").all().contains("下一页 →");
			if(contains){
				List<String> list = new ArrayList<String>();
				Selectable url = page.getUrl().regex("(.*?page=)(\\d+)",1);
				Selectable pageNum = page.getUrl().regex("(.*?page=)(\\d+)",2);
				int i=Integer.parseInt(pageNum.get());
				list.add(url.get()+String.valueOf(++i));
				page.addTargetRequests(list);
			}
		} else  {
			page.putField("标题", page.getHtml().xpath("//h1/text()") + " " + page.getHtml().xpath("//h1/span/text()").get());
			if (page.getResultItems().get("标题").toString().indexOf("null")!=-1){
                //skip this page
                page.setSkip(true);
            }
			page.putField("企业名称", page.getHtml().xpath("//a/b[1]/text()").get());
			page.putField("企业简介", page.getHtml().xpath("//div[contains(@class, 'mart10')]/text()").get());
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("本地生活", page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[2]/div/p[2]/span[1]/text()").get());
            map.put("地址", page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[2]/div/p[3]/a/text()").get() + " · " + page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[2]/div/p[3]/span/text()").get());
            map.put("轮次", page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[3]/span[2]/text()").get());
            map.put("融资金额", page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[4]/span[2]/text()").get());
            map.put("股权占比", page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[1]/div/div[2]/table/tbody/tr/td[5]/span[2]/text()").get());
            map.put("投资方", toMap(page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/ul[1]/li/div/div[2]/h4/a/b/text()").all(),page.getHtml().xpath("//li/div/div[contains(@class, 'right')]/p[contains(@class, 'mart10 text-ellis')]/text()").all()));
            map.put("该公司完整融资历史", concatList(page.getHtml().xpath("/html/body/div[2]/div[3]/div[2]/div[3]/div[2]/table/tbody/tr/td/span/text()").all(), page.getHtml().xpath("//span/a/text()").all()));
            page.putField("content", map);
            

		}
	}

	private Map<String,String> toMap(List<String> all, List<String> all2) {
		Map<String,String> map=new HashMap<String,String>();
		if(all.size()==all2.size()){
			for (int i = 0; i < all.size(); i++) {
				map.put(all.get(i),all2.get(i));
			}
		}
		return map;
	}

	/**
	 * 将list2中的所有值,添加到list1中空的位置
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public Map<String,String> concatList(List<String> list1, List<String> list2) {
		List<String> list=new ArrayList<String>();
		Map<String,String> map=new HashMap<String, String>();
		if (list2.size() == 0){
			list=list1;
		}else{
			int j = 0;
			for (int i = 0; i < list1.size(); i++) {

				if ("".equals(list1.get(i))) {
					list1.set(i, list2.get(j));
					j++;
				}

			}
			list=list1;
		}

		
		return listToMap(list, map);
	}

	private Map<String,String> listToMap(List<String> list2, Map<String, String> map) {
		int index=1;
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < list2.size(); i++) {
			
			sb.append(list2.get(i)).append(",");
			
			if(list2.get(i).matches("(\\d)+(.(\\d)+)*")){
				map.put(String.valueOf(index++), sb.toString());
				sb=new StringBuilder();
			}
		}
		return map;
	}

	/**
	 * @param trs1
	 * @return
	 */
	public List<String> filterList(List<String> trs1) {
		for (int i = 0; i < trs1.size(); i++) {
			if (trs1.get(i) != null && "".equals(trs1.get(i).trim())) {
				trs1.remove(i);
				i = i - 1;
				if (i < 0)
					i = 0;
				// if(trs1.get(i).startsWith("附件")||trs1.get(i).startsWith("品目"))
				// trs1.remove(i);
			}
		}
		return trs1;
	}

	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Spider spider = Spider.create(new InvesteventsPageProcessor());
			Spider.create(new BeijingPageProcessor()).addUrl("http://zfxxgk.beijing.gov.cn/sfc/query.ejf?title=sousuo&method=json&qt=*&sort=dateDesc&CHANNELID1=62id1,62id2,62id3,62id4,62id5&page=1&pageSize=25").addPipeline(new FilePipeline("D:/test12")).run();

		/*String[] location = { "in", "out" };
		String[] pro = { "北京", "上海", "广东", "浙江", "江苏", "天津", "福建", "湖北", "湖南", "四川", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "安徽", "江西", "山东", "河南", "广西", "海南", "重庆", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "香港", "澳门", "台湾" };
		String[] pro2 = { "亚洲", "北美洲", "南美洲", "大洋洲", "欧洲", "非洲" };
		String[] scope = { "1", "12", "28", "38", "47", "57", "70", "80", "95", "103", "115", "126", "135", "145", "161", "210", "211" };
		String[] round = { "13", "1", "12", "2", "14", "15", "3", "16", "4", "5", "6", "7", "8", "17", "18", "11", "10" };
		String[] currency = { "1", "2", "6", "7", "8", "4", "5", "9", "3" };
		String[] date = { "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999" };
		
		for (int i = 0; i < location.length; i++) {
			if(i==0){
				for (int j = 0; j < pro.length; j++) {
					for (int j2 = 0; j2 < scope.length; j2++) {
						for (int k = 0; k < round.length; k++) {
							for (int k2 = 0; k2 < currency.length; k2++) {
								for (int l = 0; l < date.length; l++) {
//										System.out.println("https://www.itjuzi.com/investevents?page=1&location=" + location[i] + "&prov=" + pro[j] + "&scope=" + scope[j2] + "&round=" + round[k] + "&currency=" + currency[k2] + "&date=" + date[l]);
//										spider.addUrl("https://www.itjuzi.com/investevents?prov=&location=in&scope=1&round=12&currency=1&date=2016&page=1prov= + pro[j] + location=" + location[i] + "&scope=" + scope[j2] + "&round=" + round[k] + "&currency=" + currency[k2] + "&date=" + date[l]+"&page=1").addPipeline(new FilePipeline("D:/itjuzi")).run();
										spider.addUrl("https://www.itjuzi.com/investevents?prov="+pro[j]+"&location="+location[i]+"&scope="+scope[j2]+"&round="+round[k]+"&currency="+currency[k2]+"&date="+date[l]+"&page=1").addPipeline(new FilePipeline("D:/itjuzi")).run();
										
								}
							}
						}
					}
				}
			
			}else{

				for (int j = 0; j < pro2.length; j++) {
					for (int j2 = 0; j2 < scope.length; j2++) {
						for (int k = 0; k < round.length; k++) {
							for (int k2 = 0; k2 < currency.length; k2++) {
								for (int l = 0; l < date.length; l++) {
									spider.addUrl("https://www.itjuzi.com/investevents?prov="+pro2[j]+"&location="+location[i]+"&scope="+scope[j2]+"&round="+round[k]+"&currency="+currency[k2]+"&date="+date[l]+"&page=1").addPipeline(new FilePipeline("D:/itjuzi")).run();
								}
							}
						}
					}
				}
			
			
			}
			}*/

	}
}
