package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class Tjsat implements PageProcessor {

//	public static final String URL_LIST = "https://www\\.itjuzi\\.com/investevents?prov=[\\u4e00-\\u9fa5]+&location=[\\w]+&scope=[\\d]+&currency=[\\d]+&date=[\\d]+&page=[\\d]+";
	public static final String URL_LIST = "http://wzcx\\.tjsat\\.gov\\.cn/ssggcx\\.action\\?cxgglx=8";
	
	
	public static final String URL_POST = "http://wzcx\\.tjsat\\.gov\\.cn/ssxxcx/qsgg\\d+\\.html";
	
	
	private Site site = Site.me().setDomain("http://wzcx.tjsat.gov.cn").setSleepTime(3000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31").addCookie("MEIQIA_EXTRA_TRACK_ID", "75a235e2232811e7af9f06528f332652").addCookie("acw_sc", "58f4233c0a5f0fd09d95811978e5fd6f4e4d1c12").addCookie("acw_tc", "AQAAAM5pXmf7lgcAYtn32qtluaorLRc4");

	private List<String> getList(){
		List<String> list=new ArrayList<>();
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg01.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg201401.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg02.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg03.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg04.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg201405.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1501.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1502.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1503.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1504.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1505.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1506.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1601.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1602.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1603.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1604.html");
		list.add("http://wzcx.tjsat.gov.cn/ssxxcx/qsgg1701.html");
		return list;
	}
	@Override
	public void process(Page page) {// /div[3]/div[2]/div[1]/ul/li[2]/a
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {//page.getUrl().regex(URL_LIST).match()
		/*	List<String> list=new ArrayList<String>();
			List<String> all = page.getHtml().xpath("//*[@id='qsgglb']/table/tbody/tr/td[@class='text998C']").all();
			for (int i = 0; i < all.size(); i++) {
				Pattern p = Pattern.compile("(ssxxcx/qsgg\\d+\\.html)");
				Matcher m = p.matcher(all.get(i));
				while(m.find()) {
					list.add("http://wzcx.tjsat.gov.cn/"+m.group(1));
				}
			}
			System.out.println(list);*/
			page.addTargetRequests(getList());
			
		} else  {
			List<String> all = page.getHtml().xpath("//tr/td[contains(@class, 'text02')]/text()").all();
			
			page.putField("detail", page.getHtml().xpath("//tr/td[contains(@class, 'text02')]/text()").all());
			page.putField("url", page.getUrl().get());
			page.putField("Period", page.getHtml().xpath("//*[@id='textContent']/table/tbody/tr[1]/td/table/tbody/tr[1]/td/text()"));
			page.putField("Time", page.getHtml().xpath("//*[@id='textContent']/table/tbody/tr[1]/td/table/tbody/tr[4]/td/text()"));
			page.putField("Source", page.getHtml().xpath("//*[@id='textContent']/table/tbody/tr[1]/td/table/tbody/tr[3]/td/text()"));
		}
	}

//	private Map<String,Map<String,String>> toMapList(List<String> all) {
//		Map<String,Map<String,String>> map=new HashMap<>();
//		Map<String,String> innner=new HashMap<>();
//		for (int i = 0; i < all.size(); i++) {
//			if(i%7==0){
//				innner.put(String.valueOf(i%7),all.get(i));
//			}
//			
//		}
//	}
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

	public static void main(String[] args) {
//		String str="https://www.itjuzi.com/investevents?prov=北京&location=in&scope=1&round=12&currency=1&date=2016&page=1";
//		str.regex(URL_LIST).match();
				Spider.create(new Tjsat()).addUrl("http://wzcx.tjsat.gov.cn/ssggcx.action?cxgglx=8").addPipeline(new FilePipeline("D:/test12")).run();
				
//		String str="https://www.itjuzi.com/investevents?prov=北京&location=in&scope=1&round=12&currency=1&date=2016&page=1";
		/*String[] location = { "in", "out" };
		String[] pro = { "北京", "上海", "广东", "浙江", "江苏", "天津", "福建", "湖北", "湖南", "四川", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "安徽", "江西", "山东", "河南", "广西", "海南", "重庆", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "香港", "澳门", "台湾" };
		String[] pro2 = { "亚洲", "北美洲", "南美洲", "大洋洲", "欧洲", "非洲" };
		String[] scope = { "1", "12", "28", "38", "47", "57", "70", "80", "95", "103", "115", "126", "135", "145", "161", "210", "211" };
		String[] round = { "13", "1", "12", "2", "14", "15", "3", "16", "4", "5", "6", "7", "8", "17", "18", "11", "10" };
		String[] currency = { "1", "2", "6", "7", "8", "4", "5", "9", "3" };
		String[] date = { "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999" };
		for (int i = 0; i < location.length; i++) {
			for (int j = 0; j < pro.length; j++) {
				for (int j2 = 0; j2 < scope.length; j2++) {
					for (int k = 0; k < round.length; k++) {
						for (int k2 = 0; k2 < currency.length; k2++) {
							for (int l = 0; l < date.length; l++) {
								if (i == 0){
//									System.out.println("https://www.itjuzi.com/investevents?page=1&location=" + location[i] + "&prov=" + pro[j] + "&scope=" + scope[j2] + "&round=" + round[k] + "&currency=" + currency[k2] + "&date=" + date[l]);
									Spider.create(new InvesteventsPageProcessor2()).addUrl("https://www.itjuzi.com/investevents?page=1&location=" + location[i] + "&prov=" + pro[j] + "&scope=" + scope[j2] + "&round=" + round[k] + "&currency=" + currency[k2] + "&date=" + date[l]+"&page=1").addPipeline(new FilePipeline("D:/itjuzi")).run();
									
								}
								else
									Spider.create(new InvesteventsPageProcessor2()).addUrl("https://www.itjuzi.com/investevents?page=1&location=" + location[i] + "&prov=" + pro2[j] + "&scope=" + scope[j2] + "&round=" + round[k] + "&currency=" + currency[k2] + "&date=" + date[l]+"&page=1").addPipeline(new FilePipeline("D:/itjuzi")).run();
							}
						}
					}
				}
			}
		}*/
		// Spider.create(new
		// ZbggPageProcessor()).addUrl("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index.htm").addPipeline(new
		// FilePipeline("D:/string/"))

	}
}
