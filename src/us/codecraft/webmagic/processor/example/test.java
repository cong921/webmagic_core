package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		/*
		 * List<String> list=new ArrayList<String>(); list.add("12");
		 * List<String> list2=new ArrayList<String>(); list2.add("23");
		 * list.addAll(list2); System.out.println(list);
		 */
		// String s1="附件1";
	/*	String[] s = {"中国疾控中心慢病中心关于采购便携式肺功能仪的中标公告", "中国疾控中心慢病中心", "北京市", "2017年03月16日", "宾萍、王宝华、王宁、蒋炜", "￥6.46 万元（人民币）", "丛舒", "010-63026625", "中国疾控中心慢病中心", "北京市西城区南纬路27号", "吴昱 010-63038055", "详见公告正文", "详见公告正文", "详见公告正文", "2017年04月13日 15:35", "2017年04月11日", "货物/其他货物/其他不另分类的物品" };
		List<String > list=new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			list.add(s[i]);
		}*/
		boolean matches = "http://wzcx.tjsat.gov.cn/ssggcx.action?cxgglx=8".matches("http://wzcx\\.tjsat\\.gov\\.cn/ssxxggcx\\.action?cxgglx=\\d+");
		System.out.println(matches);
		// boolean startsWith = s1.startsWith("附件");
		// System.out.println(startsWith);

	}
}
