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

public class ShTaxPageProcessor implements PageProcessor {

	public static final String URL_LIST = "http://www.tax.sh.gov.cn/xbwz/tycx/TYCXqjsknsrmdCtrl-getQjsknsrmd.pfv";

	private Site site = Site.me().setDomain("http://www.tax.sh.gov.cn").setSleepTime(700)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			.addHeader("Content-Type", "text/html; charset=UTF-8");

	public static final Integer PAGE = 400;
	public static Integer m = 0;
	public static Integer n = 0;
	public static List<Taxes> listtaxes = new ArrayList<>();
	public static List<Taxes> list1 = new ArrayList<>();
	public static List<String> companyAll = new ArrayList<>();
	public static List<String> numAll = new ArrayList<>();
	public static List<String> nameAll = new ArrayList<>();
	public static List<String> idTypeAll = new ArrayList<>();
	public static List<String> idNoAll = new ArrayList<>();
	public static List<String> taxAll = new ArrayList<>();
	public static List<String> debtAll = new ArrayList<>();
	public static List<String> amountAll = new ArrayList<>();
	public static List<String> sourceAll = new ArrayList<>();
	public static List<String> camountAll = new ArrayList<>();
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
		++n;
		List<String> list1 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[1]/text()").all();
		List<String> list2 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[2]/text()").all();
		List<String> list3 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[3]/text()").all();
		List<String> list4 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[4]/text()").all();
		List<String> list5 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[5]/text()").all();
		List<String> list6 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[6]/text()").all();
		List<String> list7 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[7]/text()").all();
		List<String> list8 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[8]/text()").all();
		List<String> list9 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[9]/text()").all();
		List<String> list10 = page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[10]/text()").all();
		List<String> company=subList1(list1);
		List<String> num=subList(list2);
		List<String> name=subList(list3);
		List<String> idType=subList(list4);
		List<String> idNo=subList(list5);
		List<String> tax=subList(list6);
		List<String> debt=subList(list7);
		List<String> amount=subList(list8);
		List<String> camount=subList(list9);
		List<String> source=subList(list10);
		companyAll.addAll(company);
		numAll.addAll(num);
		nameAll.addAll(name);
		idTypeAll.addAll(idType);
		idNoAll.addAll(idNo);
		taxAll.addAll(tax);
		debtAll.addAll(debt);
		amountAll.addAll(amount);
		camountAll.addAll(camount);
		sourceAll.addAll(source);
		if(n>=100){
			for (int index = 0; index < companyAll.size(); index++) {
				Taxes taxes = new Taxes();
				if("".equals(companyAll.get(index))&&
						"".equals(numAll.get(index))&&"".equals(nameAll.get(index))&&
								"".equals(idTypeAll.get(index))&&"".equals(idNoAll.get(index))){
					companyAll.set(index, companyAll.get(index-1));
					numAll.set(index, numAll.get(index-1));
					nameAll.set(index, nameAll.get(index-1));
					idTypeAll.set(index, idTypeAll.get(index-1));
					idNoAll.set(index, idNoAll.get(index-1));
				}
				taxes.setCompany(companyAll.get(index));
				taxes.setNum(numAll.get(index));
				taxes.setName(nameAll.get(index));
				taxes.setIdtype(idTypeAll.get(index));
				taxes.setIdNum(idNoAll.get(index));
				taxes.setTax(taxAll.get(index));
				taxes.setDebt(debtAll.get(index));
				taxes.setAmount(amountAll.get(index));
				taxes.setSource(sourceAll.get(index));
				taxes.setCamount(camountAll.get(index));
				taxes.setLabel("上海地税");
				taxes.setUpdated(new Date());
				listtaxes.add(taxes);
			}
			SqlsessionInsert taxesInsert=new TaxesInsert();
			System.out.println("ok");
			taxesInsert.setList(listtaxes);
			try {
				taxesInsert.insert();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// List<String> listta =
		// page.getHtml().xpath("//table[@id='tblShow']/tbody/tr/td[6]/text()")
		// .all();
		// for (String string : listta) {
		// listtax.add(string);
		// }
		
	}

	private List<String> subList(List<String> list12) {
		List<String> subList =null;
		if(n==100){
			
			subList = list12.subList(list12.size() - 10, list12.size());
		}else{
			
			subList = list12.subList(list12.size() - 20, list12.size());
		}
		return subList;
	}
	private List<String> subList1(List<String> list12) {
		List<String> subList =null;
		if(n==100){
			
		subList = list12.subList(list12.size() - 11, list12.size() - 1);
		}else{
			
		subList = list12.subList(list12.size() - 21, list12.size() - 1);
		}
		return subList;
	}



	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new ShTaxPageProcessor());
		// List<Request> ls=new ArrayList<Request>(1000);
		for (m = 301; m <= PAGE; m++) {

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
