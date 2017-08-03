package us.codecraft.webmagic.processor.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

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
//		boolean matches = "http://wzcx.tjsat.gov.cn/ssggcx.action?cxgglx=8".matches("http://wzcx\\.tjsat\\.gov\\.cn/ssxxggcx\\.action?cxgglx=\\d+");
//		boolean matches = "天津市博文特科技发展有限公司".matches("[\u4e00-\u9fa5]+");
		/*int i=24;
		boolean endsWith = String.valueOf("i").endsWith("4");
		System.out.println(endsWith);
		boolean matches = "居民身份证".matches("居民身份证");
		System.out.println(Math.ceil(95/10.0));
		System.out.println(matches);
		System.out.println("===========");
		getXixuegui();*/
		System.out.println(Integer.MAX_VALUE);
		// boolean startsWith = s1.startsWith("附件");
		// System.out.println(startsWith);

	}
	/*public String num2Voice(int i){
		String[] read={"十","百","千","万","亿","万亿"};
		String str=String.valueOf(i);
		for (int j=0;j<str.length();j++) {
			
		}
		switch (c) {
		case "1":
			"一";
			break;

		default:
			break;
		}
		return null;
		
	}*/
	/**
	 * 吸血鬼数字是指位数为偶数的数字，可以由一对数字相乘得到，而这对数字
	 */
	@Test
	public static void getXixuegui(){
		for(int i=11;i<100;i++){
			for(int j=11;j<100;j++){
				if(i*j>=1000&&i*j<10000){
					char[] charArray = String.valueOf(i*j).toCharArray();
					Arrays.sort(charArray);
					char[] charArray2 = (String.valueOf(i)+String.valueOf(j)).toCharArray();
					Arrays.sort(charArray2);
					
					if(charArray2[0]==charArray[0]&&charArray2[1]==charArray[1]&&charArray2[2]==charArray[2]&&charArray2[3]==charArray[3]){
						System.out.println(i);
						System.out.println(j);
						System.out.println(i*j);
					}
				}
			}
		}
	}
}
