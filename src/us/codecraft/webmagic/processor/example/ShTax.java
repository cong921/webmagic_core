package us.codecraft.webmagic.processor.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.domain.Taxes;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.test.SqlsessionInsert;
import us.codecraft.webmagic.test.TaxesInsert;
import us.codecraft.webmagic.utils.HttpConstant;

public class ShTax implements PageProcessor {

	public static final String URL_LIST = "http://www.tax.sh.gov.cn/xbwz/tycx/TYCXqjsknsrmdCtrl-getQjsknsrmd.pfv";

	private Site site = Site.me().setDomain("http://www.tax.sh.gov.cn").setSleepTime(450)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			.addHeader("Content-Type", "text/html; charset=UTF-8");

	public static final Integer PAGE = 100;
	public static Integer m = 0;
	public static Integer n = 0;
	public static List<Taxes> list1 = new ArrayList<>();
	public static List<String> allList = new ArrayList<>();
	public static SqlsessionInsert taxesInsert = new TaxesInsert();
	public static Set<String> listtax = new HashSet<>();
	static {
		listtax.add("营业税");
		listtax.add("城镇土地使用税");
		listtax.add("增值税");
		listtax.add("印花税");
		listtax.add("车船税");
		listtax.add("企业所得税");
		listtax.add("土地增值税");
		listtax.add("固定资产投资方向调节税（停征）");
		listtax.add("个人所得税");
		listtax.add("小计");
		listtax.add("城建税");
		listtax.add("房产税");
		listtax.add("契税");
		listtax.add("城市维护建设税");
		listtax.add("消费税");
	}

	@Override
	public void process(Page page) {

		List<String> xpath = page.getHtml().xpath("//*[@id='tblShow']/tbody/tr/td[@align='center']/text()")
				.all();
		// List<String> listta =
		// page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[6]/text()")
		// .all();
		// for (String string : listta) {
		// listtax.add(string);
		// }
		List<String> filterList = filterList(xpath);
		List<String> filterList2 = filterList2(filterList);
		allList.addAll(filterList2);
//		System.out.println(allList);
		++n;
		if (n >= 100) {
			System.out.println(PAGE);
			int num = allList.size();
			for (int i = 0; i < Math.ceil(num / 10.0); i++) {
//				System.out.println(i);

				if (i <= Math.ceil(num / 10.0) && (i * 10) < num && allList.get(i * 10) != null
						&& listtax.contains(allList.get(i * 10))) {
					allList.remove(i * 10 - 1);
					allList.remove(i * 10 - 2);
					allList.remove(i * 10 - 3);
					allList.remove(i * 10 - 4);
					allList.remove(i * 10 - 5);
					--i;
					num -= 5;
					// System.out.println(num);
				}
				Taxes taxesWithBLOBs = new Taxes();
				if(i * 10 + 9<num){
					
					taxesWithBLOBs.setCompany(allList.get(i * 10 + 0));
					taxesWithBLOBs.setNum(allList.get(i * 10 + 1));
					taxesWithBLOBs.setName(allList.get(i * 10 + 2));
					taxesWithBLOBs.setIdtype(allList.get(i * 10 + 3));
					taxesWithBLOBs.setIdNum(allList.get(i * 10 + 4));
					taxesWithBLOBs.setTax(allList.get(i * 10 + 5));
					// System.out.println(i*10+5);
					taxesWithBLOBs.setDebt(allList.get(i * 10 + 6));
					taxesWithBLOBs.setAmount(allList.get(i * 10 + 7));
					taxesWithBLOBs.setCamount(allList.get(i * 10 + 8));
					taxesWithBLOBs.setSource(allList.get(i * 10 + 9));
					taxesWithBLOBs.setUpdated(new Date());
					taxesWithBLOBs.setLabel("上海地税");
				}
				list1.add(taxesWithBLOBs);
			}
			System.out.println("+++++++++++++++++");
			taxesInsert.setList(list1);
			try {
				taxesInsert.insert();
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}

	private List<String> filterList2(List<String> filterList) {
		int num = filterList.size();
		for (int i = 0; i < num; i++) {
			if ("".equals(filterList.get(i))) {

				filterList.remove(i);
				num--;
				i--;
			}
		}
		return filterList;
	}

	public List<String> filterList(List<String> trs1) {
		trs1 = trs1.subList(0, trs1.size() / 2);
		int num1 = trs1.size();
		for (int i = 0; i < num1; i++) {
			if (String.valueOf(i).endsWith("4")&&trs1.get(i).equals("")&&trs1.get(i-1).matches("居民身份证")) {
//				System.out.println(i);
				trs1.set(i, null);
			}
			if (String.valueOf(i).endsWith("3")&&trs1.get(i).equals("")&&trs1.get(i-1).matches("[a-zA-Z0-9_\u4e00-\u9fa5\\(\\)]+")) {
//				System.out.println(i);
				trs1.set(i, null);
			}
			if (String.valueOf(i).endsWith("8") && trs1.get(i).equals(""))
				trs1.set(i, null);
			if (String.valueOf(i).endsWith("7") && trs1.get(i).equals(""))
				trs1.set(i, null);
			if (String.valueOf(i).endsWith("6") && trs1.get(i).equals(""))
				trs1.set(i, null);
			if (String.valueOf(i).endsWith("5") && trs1.get(i).equals("")) {
				trs1.remove(i);
				--i;
				--num1;

			}

		}
		for (int i = 0; i < trs1.size(); i++) {
			if (trs1.get(i) != null && "".equals(trs1.get(i).trim())) {
				trs1.remove(i);
				i = i - 1;
				if (i < 0)
					i = 0;
			}
		}
		return trs1;
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new ShTax());
		// List<Request> ls=new ArrayList<Request>(1000);
		for (m = 1; m <= PAGE; m++) {

			NameValuePair[] values = new NameValuePair[5];
			values[0] = new BasicNameValuePair("curPage", String.valueOf(m));
			values[1] = new BasicNameValuePair("type", "QY");
			values[2] = new BasicNameValuePair("nsrmc", null);
			values[3] = new BasicNameValuePair("swdjh", null);
			values[4] = new BasicNameValuePair("fzrxm", null);

			Map<String, Object> nameValuePair = new HashMap<String, Object>();
			nameValuePair.put("nameValuePair", values);

			Request request = new Request(
					"http://www.tax.sh.gov.cn/xbwz/tycx/TYCXqjsknsrmdCtrl-getQjsknsrmd.pfv?curPage="
							+ String.valueOf(m) + "&type=QY");
			request.setExtras(nameValuePair);

			request.setMethod(HttpConstant.Method.POST);
			spider.addRequest(request);
		}
		spider.run();

	}
}
