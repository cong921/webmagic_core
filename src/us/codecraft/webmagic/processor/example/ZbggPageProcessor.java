package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class ZbggPageProcessor implements PageProcessor{

    public static final String URL_LIST = "http://www\\.ccgp\\.gov\\.cn/cggg/zygg/zbgg/index[_\\d+]*\\.htm";

    public static final String URL_POST = "http://www.ccgp.gov.cn/cggg/zygg/zbgg/\\d+/t\\d+_\\d+\\.\\w+";

    private Site site = Site
            .me()
            .setDomain("www.ccgp.gov.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    public List<String> getList(){
    	List list=new ArrayList<String>();
//    	for (int i = 1; i < 25; i++) {
   	for (int i = 1; i < 25; i++) {
			list.add("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index_"+i+".htm");
		}
    	return list;
    }
    @Override
    public void process(Page page) {///div[3]/div[2]/div[1]/ul/li[2]/a
        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
//        	System.out.println(page.getHtml());//div[3]/div[2]/div[1]/ul/li[2]/a
            page.addTargetRequests(page.getHtml().links().regex(URL_POST).all());
            page.addTargetRequests(getList());
//            System.out.println("===============");
//            System.out.println(page.getHtml().xpath("").links().all().get(page.getHtml().links().all().size()-5));
//            System.out.println("===============");//div[3]/div[2]/div[2]/p/a[7]//body > div:nth-child(5) > div.vT_list_main > div.pagigation > p > a.next
            //div[3]/div[2]/div[2]/p/a[8]
            //文章页
        } else {
//        	page.putField("标题", page.getHtml().xpath("//*div[3]/div[2]/div[1]/h2/text()"));///div[3]/div[2]/div[2]/table/tbody/tr[2]/td[2]
//            page.putField("采购项目名称", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[2]/td[2]/text()"));///div[3]/div[2]/div[2]/table/tbody/tr[2]/td[2]
//            page.putField("品目", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[3]/td[2]/p/text()"));
//            page.putField("采购单位	", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[4]/td[2]/text()"));
//            
//            page.putField("行政区域", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[5]/td[2]/text()"));
//            page.putField("公告时间", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[5]/td[4]/text()"));
//            page.putField("本项目招标公告日期", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[6]/td[2]/text()"));
//            page.putField("中标日期", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[6]/td[4]/text()"));
//            page.putField("评审专家名单", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[7]/td[2]/text()"));
//            page.putField("总中标金额", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[8]/td[2]/text()"));
//            page.putField("项目联系人", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[10]/td[2]/text()"));
//            page.putField("项目联系电话", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[11]/td[2]/text()"));
//            page.putField("采购单位", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[12]/td[2]/text()"));
//            page.putField("采购单位地址", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[13]/td[2]/text()"));
//            page.putField("采购单位联系方式", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[14]/td[2]/text()"));
//            page.putField("代理机构名称", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[15]/td[2]/text()"));
//            page.putField("代理机构地址", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[16]/td[2]/text()"));
//            page.putField("代理机构联系方式", page.getHtml().xpath("//*div[3]/div[2]/div[2]/table/tbody/tr[17]/td[2]/text()"));
//            page.putField("代理机构联系方式", page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/table/tbody/tr"));
            page.putField("title", page.getHtml().xpath("//h2[@class='tc']/text()"));
            page.putField("显示公告正文", page.getHtml().xpath("//div[contains(@class, 'vT_detail_content w760c')]"));
            
//          List<String> field1=page.getHtml().xpath("//tr/td[1]/text()").all();
//          List<String> field1=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td[@class='title']/text()").all();
//          System.out.println("field1"+field1.size());
//          List<String> trs1=page.getHtml().xpath("//table/tbody/tr/td[position()=2 and position()=4]/text()").all();
//          List<String> trs1=page.getHtml().xpath("//td[@class='title']/next()/text()").all();
//          List<String> trs1=page.getHtml().xpath("//td[@class='title']:following-sibling/text()").all();
//          List<String> trs1=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td[2]/text()").all();
//          List<String> trs2=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td[4]/text()").all();
          List<String> trs3=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td/p/text()").all();
          List<String> trs4=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td//text()").all();
//          List<String> trs5=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td/a[@href]/text()").all();
//          List<String> trs6=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td/text()").all();
          List<String> trs6=page.getHtml().xpath("//div[contains(@class,'table')]/table/tbody/tr/td/a").all();
//          List<String> trs6=page.getHtml().xpath("//div[contains(@class, 'table')]/table/tbody/tr/td[attribute::*]/text()").all();
//          List<String> trs6=page.getHtml().links().all();
          for (int i=0;i<trs4.size();i++) {
        	  if(trs4.get(i).equals("品目"))
        		  trs4.set(i+1, trs3.get(0));
        	 
        	  for (int j=0;j<trs6.size();j++) {
        		  if(trs4.get(i).equals("附件"+(j+1))){
        			  trs4.set(i+1, trs6.get(j));
      				
      			}
        	  }
        	  
			
		}
          List<String> filterList = filterList(trs4);
         for (int i = 0; i < filterList.size(); i+=2) {
			page.putField(filterList.get(i), filterList.get(i+1));
		}
//          System.out.println("href"+trs6.size()+trs6);
//          System.out.println("trs4"+trs4);
//          trs1.addAll(trs2);
//          trs1.addAll(trs3);
//          trs1=filterList(trs1);
//          trs1.add("品目");
//          System.out.println(trs1);
//          System.out.println("trs1"+trs1.size());
       /*   System.out.println(trs1.size());
        
          System.out.println(trs1.size());
          System.out.println(trs1);*/
//           ResultItems resultItems = page.getResultItems();
//           System.out.println("============");
//           System.out.println(resultItems.getAll());
//           System.out.println("============");
//            page.putField("date",
//                    page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
           
        }
    }
    public List<String> filterList(List<String> trs1){
    	 for(int i=0;i<trs1.size();i++){
       	  if(trs1.get(i)!=null&&"".equals(trs1.get(i).trim())){
       		  trs1.remove(i);
       		  i=i-1;
       		  if(i<0)
       			  i=0;
//       		  if(trs1.get(i).startsWith("附件")||trs1.get(i).startsWith("品目"))
//       			  trs1.remove(i);
       	  }
		}
		return trs1;
    }
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new ZbggPageProcessor()).addUrl("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index.htm").addPipeline(new FilePipeline("D:/string/"))
        Spider.create(new ZbggPageProcessor()).addUrl("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index.htm").addPipeline(new FilePipeline("D:/zbgg/"))
                .run();
       
    }}
