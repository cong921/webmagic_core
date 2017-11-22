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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

public class IshadowxPageprocessor implements PageProcessor {

	public static final String URL_LIST = "http://www\\.ccgp\\.gov\\.cn/cggg/zygg/zbgg/index[_\\d+]*\\.htm";

	public static final String URL_POST = "http://www.ccgp.gov.cn/cggg/zygg/zbgg/\\d+/t\\d+_\\d+\\.\\w+";

	private Site site = Site.me().setDomain("ss.ishadowx.com/").setSleepTime(100).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			Document parse = Jsoup.parse(page.getHtml().toString());
			Elements hovers = parse.getElementsByClass("hover-text");
			List<List<String>> listall = new ArrayList<List<String>>();
			for (Element element : hovers) {
				List<String> list = new ArrayList<String>();
				Elements select = element.select("h4");
//				if(select.text().indexOf("Method")!=-1) {
//					System.out.println(select.text());
//				}else {
//					Elements select2 = select.select("span");
//					for (Element element2 : select2) {
//						System.out.println(element2.select("span").get(0).text());
//					}
//				}
				list.add(select.select("span").get(0).text());
				list.add(select.select("span").get(2).text());
				list.add(select.select("span").get(4).text());
				list.add(select.get(3).text().substring(7));
				listall.add(list);
			}
			// System.out.println(listall);
			File file = new File("C:\\Users\\taihao\\Desktop\\Shadowsocks-3.4.2.1\\gui-config.json");
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuffer sb = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			Json json = new Json(sb.toString());
			// System.out.println(sb.toString());
			JSONObject jsonObject = JSONObject.parseObject(sb.toString());
			int num = jsonObject.getJSONArray("configs").toArray().length;
			System.out.println(listall.size());
			for (int index = 0; index < listall.size(); index++) {
				jsonObject.getJSONArray("configs").getJSONObject(index).put("server", listall.get(index).get(0));
				jsonObject.getJSONArray("configs").getJSONObject(index).put("server_port", listall.get(index).get(1));
				jsonObject.getJSONArray("configs").getJSONObject(index).put("password", listall.get(index).get(2));
				jsonObject.getJSONArray("configs").getJSONObject(index).put("method", listall.get(index).get(3));
			}
			if (file.exists())
				file.delete();
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					new File("C:\\Users\\taihao\\Desktop\\Shadowsocks-3.4.2.1\\gui-config.json"))));
			bw.write(jsonObject.toJSONString());
			// System.out.println(jsonObject);
			bw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void process() {
		Document parse = Jsoup.parse("\r\n" + 
				"<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"<title>iShadow</title>\r\n" + 
				"<meta name=\"description\" content=\"iShadow has been providing free SS information for a long time. Supported on most common devices like Windows/iPhone/iPad/Android/Macbook. Only two steps to surfing any websites you like.\">\r\n" + 
				"<meta name=\"keywords\" content=\"ss,iss,ishadow,free ss\" />\r\n" + 
				"<meta name=\"author\" content=\"\">\r\n" + 
				"\r\n" + 
				"<!-- Favicons\r\n" + 
				"    ================================================== -->\r\n" + 
				"<link rel=\"shortcut icon\" href=\"img/favicon.ico\" type=\"image/x-icon\">\r\n" + 
				"<link rel=\"apple-touch-icon\" href=\"img/apple-touch-icon.png\">\r\n" + 
				"<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"img/apple-touch-icon-72x72.png\">\r\n" + 
				"<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"img/apple-touch-icon-114x114.png\">\r\n" + 
				"\r\n" + 
				"<!-- Bootstrap -->\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\"  href=\"https://cat.iyeku.com/css/bootstrap.css\">\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cat.iyeku.com/fonts/font-awesome/css/font-awesome.css\">\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\"  href=\"https://cat.iyeku.com/css/primer.css\">\r\n" + 
				"\r\n" + 
				"<!-- Stylesheet\r\n" + 
				"    ================================================== -->\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\"  href=\"https://cat.iyeku.com/css/style.css\">\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cat.iyeku.com/css/nivo-lightbox/nivo-lightbox.css\">\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cat.iyeku.com/css/nivo-lightbox/default.css\">\r\n" + 
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cat.iyeku.com/css/googlefonts.css\">\r\n" + 
				"<!-- <link href=\"https://fonts.googleapis.com/css?family=Raleway:300,400,500,600,700\" rel=\"stylesheet\">\r\n" + 
				"<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700\" rel=\"stylesheet\">\r\n" + 
				"<link href=\"https://fonts.googleapis.com/css?family=Dancing+Script:400,700\" rel=\"stylesheet\"> -->\r\n" + 
				"\r\n" + 
				"<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\r\n" + 
				"<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n" + 
				"<!--[if lt IE 9]>\r\n" + 
				"      <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\r\n" + 
				"      <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n" + 
				"    <![endif]-->\r\n" + 
				"</head>\r\n" + 
				"<body id=\"page-top\" data-spy=\"scroll\" data-target=\".navbar-fixed-top\">\r\n" + 
				"<!-- Navigation\r\n" + 
				"    ==========================================-->\r\n" + 
				"<nav id=\"menu\" class=\"navbar navbar-default navbar-fixed-top\">\r\n" + 
				"  <div class=\"container\"> \r\n" + 
				"    <!-- Brand and toggle get grouped for better mobile display -->\r\n" + 
				"    <div class=\"navbar-header\">\r\n" + 
				"      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\"> <span class=\"sr-only\">Toggle navigation</span> <span class=\"icon-bar\"></span> <span class=\"icon-bar\"></span> <span class=\"icon-bar\"></span> </button>\r\n" + 
				"      <a class=\"navbar-brand page-scroll\" href=\"#page-top\">iShadow</a> </div>\r\n" + 
				"    \r\n" + 
				"    <!-- Collect the nav links, forms, and other content for toggling -->\r\n" + 
				"    <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">\r\n" + 
				"      <ul class=\"nav navbar-nav navbar-right\">\r\n" + 
				"        <li><a href=\"#top\" class=\"page-scroll\">Main</a></li>\r\n" + 
				"        <li><a href=\"#about\" class=\"page-scroll\">Howto</a></li>\r\n" + 
				"        <li><a href=\"#restaurant-menu\" class=\"page-scroll\">Download</a></li>\r\n" + 
				"        <li><a href=\"#portfolio\" class=\"page-scroll\">Free SS</a></li>\r\n" + 
				"        <li><a href=\"#team\" class=\"page-scroll\">Premium</a></li>\r\n" + 
				"        <li><a href=\"#call-reservation\" class=\"page-scroll\">Contact</a></li>\r\n" + 
				"        <li><a href=\"index_cn.html\" class=\"page-scroll\"><img src=\"img/cn.png\" width=\"20\"> 中文</a></li>\r\n" + 
				"      </ul>\r\n" + 
				"    </div>\r\n" + 
				"    <!-- /.navbar-collapse --> \r\n" + 
				"  </div>\r\n" + 
				"</nav>\r\n" + 
				"<!-- Header -->\r\n" + 
				"<header id=\"header\">\r\n" + 
				"  <div class=\"intro\">\r\n" + 
				"    <div class=\"overlay\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <div class=\"row\">\r\n" + 
				"          <div class=\"intro-text\">\r\n" + 
				"            <h1>iShadow</h1>\r\n" + 
				"            <p>Life is short. The world is big.</p>\r\n" + 
				"            <a href=\"#portfolio\" class=\"btn btn-custom btn-lg page-scroll\">Get Free SS</a> </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</header>\r\n" + 
				"<!-- About Section -->\r\n" + 
				"<div id=\"about\">\r\n" + 
				"  <div class=\"container\">\r\n" + 
				"    <div class=\"row\">\r\n" + 
				"      <div class=\"col-xs-12 col-md-6 \">\r\n" + 
				"        <div class=\"about-img\"><img src=\"img/about.jpg\" class=\"img-responsive\" alt=\"\"></div>\r\n" + 
				"      </div>\r\n" + 
				"      <div class=\"col-xs-12 col-md-6\">\r\n" + 
				"        <div class=\"about-text\">\r\n" + 
				"          <h2>How to use it</h2>\r\n" + 
				"          <hr>\r\n" + 
				"          <p>This software supported on most common devices like Windows/iPhone/iPad/Android/Macbook. There are only two steps to get it working. First, download and install it. Second, open it and scan QR image. Servers will be configured automatically. Then surfing any websites you like.</p>\r\n" + 
				"          <a href=\"https://wiki.all404.com/ss-howto-all/\" class=\"btn btn-custom btn-lg\" target=\"_blank\">Tutorials</a>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"<!-- Restaurant Menu Section -->\r\n" + 
				"<div id=\"restaurant-menu\">\r\n" + 
				"  <div class=\"section-title text-center center\">\r\n" + 
				"    <div class=\"overlay\">\r\n" + 
				"      <h2>Download</h2>\r\n" + 
				"      <hr>\r\n" + 
				"      <p>Please choose the download link that fits for your devices.</p>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"container\">\r\n" + 
				"    <div class=\"row\">\r\n" + 
				"      <div class=\"col-xs-12 col-sm-6\">\r\n" + 
				"        <div class=\"menu-section\">\r\n" + 
				"          <h2 class=\"menu-section-title\"><i class=\"fa fa-windows\"></i> Windows</h2>\r\n" + 
				"          <hr>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SS for Windows </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://160.16.231.71/ss-3.4.3.zip\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SS 3.4.3</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> All windows series supported. .NET may required on WinXP. </div>\r\n" + 
				"          </div>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SSR for Windows </div>\r\n" + 
				"            <div class=\"menu-item-price\"> <a href=\"http://160.16.231.71/ssr-4.1.4-win.7z\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SSR 4.1.4</a> </div>\r\n" + 
				"            <div class=\"menu-item-description\"> All windows series supported. .NET may required on WinXP. </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"      <div class=\"col-xs-12 col-sm-6\">\r\n" + 
				"        <div class=\"menu-section\">\r\n" + 
				"          <h2 class=\"menu-section-title\"><i class=\"fa fa-apple\"></i> iPhone/iPad</h2>\r\n" + 
				"          <hr>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> Shadowrocket($2.99) </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://dwz.cn/3BvX4t\" class=\"btn btn-custom btn-lg\" target=\"_blank\">AppStore</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> No jailbreak needed. Requires iOS 9.0 or later. SS and SSR supported.</div>\r\n" + 
				"          </div>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> Wingy(free) </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://dwz.cn/5vCPvz\" class=\"btn btn-custom btn-lg\" target=\"_blank\">AppStore</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> No jailbreak needed. Requires iOS 9.0 or later. SS and SSR supported</div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"row\">\r\n" + 
				"      <div class=\"col-xs-12 col-sm-6\">\r\n" + 
				"        <div class=\"menu-section\">\r\n" + 
				"          <h2 class=\"menu-section-title\"><i class=\"fa fa-android\"></i> Android</h2>\r\n" + 
				"          <hr>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SS for Andoird </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://160.16.231.71/ss-nightly-4.1.3.apk\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SS V4.1.3</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> No need to ROOT. SS only. Official version. For latest version, please <a href=\"http://dwz.cn/sk7E1\" target=\"_blank\">click here</a>.</div>\r\n" + 
				"          </div>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SSR for Android </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://160.16.231.71/ssr-3.3.5.apk\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SSR V3.3.5</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> No need to ROOT. </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"      <div class=\"col-xs-12 col-sm-6\">\r\n" + 
				"        <div class=\"menu-section\">\r\n" + 
				"          <h2 class=\"menu-section-title\"><i class=\"fa fa-laptop\"></i> Macbook</h2>\r\n" + 
				"          <hr>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SS for OSX </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://160.16.231.71/ssx-2.6.3.dmg\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SSX V2.6.3</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> Released on 6 Mar 2015. For SS only, it's enough.</div>\r\n" + 
				"          </div>\r\n" + 
				"          <div class=\"menu-item\">\r\n" + 
				"            <div class=\"menu-item-name\"> SSR for OSX </div>\r\n" + 
				"            <div class=\"menu-item-price\"><a href=\"http://160.16.231.71/SSX-NG-R8.dmg\" class=\"btn btn-custom btn-lg\" target=\"_blank\">SSR-NG-R8</a></div>\r\n" + 
				"            <div class=\"menu-item-description\"> The new version developed by non-official team. SS and SSR supported. </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"<!-- Portfolio Section -->\r\n" + 
				"<div id=\"portfolio\">\r\n" + 
				"  <div class=\"section-title text-center center\">\r\n" + 
				"    <div class=\"overlay\">\r\n" + 
				"      <h2>Free SS Info</h2>\r\n" + 
				"      <hr>\r\n" + 
				"      <p>All servers will be reset and change its password in every 6 hours.</p>\r\n" + 
				"      <p>0/6/12/24 GMT+8</p>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"container\">\r\n" + 
				"    <div class=\"row\">\r\n" + 
				"      <div class=\"categories\">\r\n" + 
				"        <ul class=\"cat\">\r\n" + 
				"          <li>\r\n" + 
				"            <ol class=\"type\">\r\n" + 
				"              <li><a href=\"#\" data-filter=\"*\" class=\"active\">All</a></li>\r\n" + 
				"              <li><a href=\"#\" data-filter=\".us\">United States</a></li>\r\n" + 
				"              <li><a href=\"#\" data-filter=\".jp\">Japan</a></li>\r\n" + 
				"              <li><a href=\"#\" data-filter=\".sg\">Singapore</a></li>\r\n" + 
				"              <li><a href=\"#\" data-filter=\".ssr\">SSR</a></li>\r\n" + 
				"            </ol>\r\n" + 
				"          </li>\r\n" + 
				"        </ul>\r\n" + 
				"        <div class=\"clearfix\"></div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"row\">\r\n" + 
				"      <div class=\"portfolio-items\">\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 us\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\"> \r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipusa\">a.isxb.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipusa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portusa\">17658\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portusa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwusa\">69033386\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwusa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/usaxxoo.png\" title=\"United Stated-Linode\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/01-small.jpg\" class=\"img-responsive\" alt=\"Project Title\">  </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 us\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipusb\">b.isxb.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipusb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portusb\">16583\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portusb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwusb\">48520375\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwusb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/usbxxoo.png\" title=\"United Stated-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/02-small.jpg\" class=\"img-responsive\" alt=\"Project Title\">  </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 us\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipusc\">c.isxb.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipusc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portusc\">13099\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portusc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwusc\">63191929\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwusc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/uscxxoo.png\" title=\"United Stated-Sharktech\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/03-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 jp\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipjpa\">a.isxa.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipjpa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portjpa\">11176\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portjpa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwjpa\">53898854\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwjpa\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/jpaxxoo.png\" title=\"Japan-Linode1\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/04-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 jp\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipjpb\">b.isxa.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipjpb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portjpb\">12977\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portjpb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwjpb\">35060462\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwjpb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/jpbxxoo.png\" title=\"Japan-Linode2\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/05-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 jp\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipjpc\">c.isxa.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipjpc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portjpc\">14957\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portjpc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwjpc\">79037253\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwjpc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/jpcxxoo.png\" title=\"Japan-GMO\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/06-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 sg\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipsga\">a.isxc.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipsga\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portsga\">11238\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portsga\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwsga\">80809313\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwsga\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/sgaxxoo.png\" title=\"Singapore-Linode\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/07-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 sg\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipsgb\">b.isxc.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipsgb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portsgb\">11088\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portsgb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwsgb\">72072272\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwsgb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/sgbxxoo.png\" title=\"Singapore-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/08-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 sg\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipsgc\">c.isxc.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipsgc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portsgc\">16063\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portsgc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwsgc\">39553203\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwsgc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4><a href=\"img/qr/sgcxxoo.png\" title=\"Singapore-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/09-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 ssr\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipssra\">a.isxc.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipssra\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portssra\">11238\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portssra\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwssra\">80809313\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwssra\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4>auth_sha1_v4 tls1.2_ticket_auth</h4>\r\n" + 
				"                <!--<h4><a href=\"img/qr/ssra.png\" title=\"United Stated-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>-->\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/10-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 ssr\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipssrb\">b.isxa.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipssrb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portssrb\">12977\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portssrb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwssrb\">35060462\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwssrb\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4>auth_sha1_v4 tls1.2_ticket_auth</h4>\r\n" + 
				"                <!--<h4><a href=\"img/qr/ssrb.png\" title=\"United Stated-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>-->\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/11-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-sm-6 col-md-4 col-lg-4 ssr\">\r\n" + 
				"          <div class=\"portfolio-item\">\r\n" + 
				"            <div class=\"hover-bg\">\r\n" + 
				"              <div class=\"hover-text\">\r\n" + 
				"                <h4>IP Address:<span id=\"ipssrc\">c.isxb.bid</span>  <span class=\"copybtn\" data-clipboard-target=\"#ipssrc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Port:<span id=\"portssrc\">13099\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#portssrc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Password:<span id=\"pwssrc\">63191929\r\n" + 
				"</span>  <span class=\"copybtn\" data-clipboard-target=\"#pwssrc\"><i class=\"fa fa-copy\"></i></span></h4>\r\n" + 
				"                <h4>Method:aes-256-cfb</h4>\r\n" + 
				"                <h4>auth_sha1_v4 tls1.2_ticket_auth</h4>\r\n" + 
				"                <!--<h4><a href=\"img/qr/ssrc.png\" title=\"United Stated-Digital Ocean\" data-lightbox-gallery=\"gallery1\">Click to view QR Code</a></h4>-->\r\n" + 
				"              </div>\r\n" + 
				"              <img src=\"https://cat.iyeku.com/img/portfolio/12-small.jpg\" class=\"img-responsive\" alt=\"Project Title\"> </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"row text-center center\">\r\n" + 
				"      <a href=\"http://ss8.pm\" class=\"btn btn-custom btn-lg\" target=\"_blank\">More Free</a>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"<!-- Team Section -->\r\n" + 
				"<div id=\"team\" class=\"text-center\">\r\n" + 
				"  <div class=\"overlay\">\r\n" + 
				"    <div class=\"container\">\r\n" + 
				"      <div class=\"col-md-10 col-md-offset-1 section-title\">\r\n" + 
				"        <h2>Reliable Providers</h2>\r\n" + 
				"        <hr>\r\n" + 
				"        <p>If you need premium service, there are a few providers that opereated for less than two years and worth to have a try. They all have free trial policy.</p>\r\n" + 
				"      </div>\r\n" + 
				"      <div id=\"row\">\r\n" + 
				"        <div class=\"col-md-4 team\">\r\n" + 
				"          <div class=\"thumbnail\">\r\n" + 
				"            <div class=\"team-img\"><img src=\"https://cat.iyeku.com/img/portfolio/01-small.jpg\" alt=\"...\"></div>\r\n" + 
				"            <div class=\"caption\">\r\n" + 
				"              <h3>FoTiaoQiang</h3>\r\n" + 
				"              <p>3 days free trial</p>\r\n" + 
				"              <p>US/HK/JP/SG/TW/KR</p>\r\n" + 
				"              <p>SS/SSR and V supported</p>\r\n" + 
				"              <p>Unlimited plan,lower than $1.2/m</p>\r\n" + 
				"              <a href=\"http://ftq.pm\" class=\"btn btn-custom btn-lg\" target=\"_blank\">Get Started</a>\r\n" + 
				"            </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-md-4 team\">\r\n" + 
				"          <div class=\"thumbnail\">\r\n" + 
				"            <div class=\"team-img\"><img src=\"https://cat.iyeku.com/img/portfolio/02-small.jpg\" alt=\"...\"></div>\r\n" + 
				"            <div class=\"caption\">\r\n" + 
				"              <h3>CloudSS</h3>\r\n" + 
				"              <p>$0.72/3GB trial plan</p>\r\n" + 
				"              <p>US/HK/JP/SG/TW/KR/CN2</p>\r\n" + 
				"              <p>SS/SSR. Scan to auto-setup</p>\r\n" + 
				"              <p>Unlimited plan. Auto provision</p>\r\n" + 
				"              <a href=\"https://get.cloudss.biz\" class=\"btn btn-custom btn-lg\" target=\"_blank\">Get Started</a>\r\n" + 
				"            </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col-md-4 team\">\r\n" + 
				"          <div class=\"thumbnail\">\r\n" + 
				"            <div class=\"team-img\"><img src=\"https://cat.iyeku.com/img/portfolio/03-small.jpg\" alt=\"...\"></div>\r\n" + 
				"            <div class=\"caption\">\r\n" + 
				"              <h3>V2SS</h3>\r\n" + 
				"              <p>$0.14/1GB trial plan</p>\r\n" + 
				"              <p>Azure/Gcloud/Aliyun/SK/Hinet</p>\r\n" + 
				"              <p>SS/SSR. Scan to auto-setup</p>\r\n" + 
				"              <p>Premium plan. Auto provision</p>\r\n" + 
				"              <a href=\"https://i.v2ss.info\" class=\"btn btn-custom btn-lg\" target=\"_blank\">Get Started</a>\r\n" + 
				"            </div>\r\n" + 
				"          </div>\r\n" + 
				"        </div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"<!-- Call Reservation Section -->\r\n" + 
				"<div id=\"call-reservation\" class=\"text-center\">\r\n" + 
				"  <div class=\"container\">\r\n" + 
				"    <h2>We provide free service only.</h2>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"<div id=\"footer\">\r\n" + 
				"  <div class=\"container text-center\">\r\n" + 
				"    <div class=\"col-md-4\">\r\n" + 
				"      <h3>Terms of Use</h3>\r\n" + 
				"      <div class=\"contact-item\">\r\n" + 
				"        <p>Information provided on this page is only for experimental purpose.</p>\r\n" + 
				"        <p>All contents not allowed in China have been limited from our servers.</p>\r\n" + 
				"        <p>Any acts in violation of Chinese law are not allowed.</p>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"col-md-4\">\r\n" + 
				"      <h3>Alternate URL</h3>\r\n" + 
				"      <div class=\"contact-item\">\r\n" + 
				"        <p><a href=\"http://isx.yt\">isx.yt</a></p>\r\n" + 
				"        <p><a href=\"http://isx.tn\">isx.tn</a></p>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    <div class=\"col-md-4\">\r\n" + 
				"      <h3>Get back to iShadow</h3>\r\n" + 
				"      <div class=\"contact-item\">\r\n" + 
				"        <p>Send any text email to the following address to get the latest link.</p>\r\n" + 
				"        <p>Email: <img src=\"img/email.png\" width=\"200\" alt=\"\"></p>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"  <div class=\"container-fluid text-center copyrights\">\r\n" + 
				"    <div class=\"col-md-8 col-md-offset-2\">\r\n" + 
				"      <p>&copy; 2014-2017 iShadow. All rights reserved.</p>\r\n" + 
				"    </div>\r\n" + 
				"  </div>\r\n" + 
				"</div>\r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/jquery.1.11.1.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/bootstrap.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/SmoothScroll.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/nivo-lightbox.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/jquery.isotope.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/jqBootstrapValidation.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/contact_me.js\"></script> \r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/main.js\"></script>\r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/dist/clipboard.min.js\"></script>\r\n" + 
				"<script type=\"text/javascript\" src=\"https://cat.iyeku.com/js/tooltips.js\"></script>\r\n" + 
				"<script>\r\n" + 
				"var clipboard = new Clipboard('.copybtn');\r\n" + 
				"\r\n" + 
				"clipboard.on('success', function(e) {\r\n" + 
				"    console.info('Action:', e.action);\r\n" + 
				"    console.info('Text:', e.text);\r\n" + 
				"    console.info('Trigger:', e.trigger);\r\n" + 
				"    showTooltip(e.trigger,'Copied!');\r\n" + 
				"    e.clearSelection();\r\n" + 
				"});\r\n" + 
				"\r\n" + 
				"clipboard.on('error', function(e) {\r\n" + 
				"    console.error('Action:', e.action);\r\n" + 
				"    console.error('Trigger:', e.trigger);\r\n" + 
				"    showTooltip(e.trigger,fallbackMessage(e.action));\r\n" + 
				"});\r\n" + 
				"</script>\r\n" + 
				"<script>\r\n" + 
				"  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\r\n" + 
				"  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\r\n" + 
				"  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\r\n" + 
				"  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\r\n" + 
				"\r\n" + 
				"  ga('create', 'UA-62083646-2', 'auto');\r\n" + 
				"  ga('send', 'pageview');\r\n" + 
				"\r\n" + 
				"</script>\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"");
		Elements hovers = parse.getElementsByClass("hover-text");
		for (Element element : hovers) {
			Elements select = element.select("h4");
//			if(select.text().indexOf("Method")!=-1) {
//				System.out.println(select.text());
//			}else {
//				Elements select2 = select.select("span");
//				for (Element element2 : select2) {
//					System.out.println(element2.select("span").get(0).text());
//				}
//			}
			System.out.println(select.select("span").get(0).text());
			System.out.println(select.select("span").get(2).text());
			System.out.println(select.select("span").get(4).text());
			System.out.println(select.get(3).text().substring(7));
		}
//		String elementsByClass = parse.getElementsByClass("hover-text").get(0).children().select("h4").select("span")
//				.get(0).text();
//		System.out.println(elementsByClass);
	}

	@Override
	public Site getSite() {
		return site;
	}

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

	public static void main(String[] args) {
		// Spider.create(new
		// ZbggPageProcessor()).addUrl("http://www.ccgp.gov.cn/cggg/zygg/zbgg/index.htm").addPipeline(new
		// FilePipeline("D:/string/"))
		 Spider.create(new
		 IshadowxPageprocessor()).addUrl("http://ss.ishadowx.com/index.html").addPipeline(new
		 ConsolePipeline())
		 .run();
//		new IshadowxPageprocessor().process();

	}
}
