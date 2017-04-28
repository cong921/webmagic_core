package us.codecraft.webmagic.pipeline;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.domain.TaxesWithBLOBs;
import us.codecraft.webmagic.processor.example.ConnectionPool;
import us.codecraft.webmagic.selector.AbstractSelectable;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.test.SqlsessionInsert;
import us.codecraft.webmagic.test.TaxesInsert;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * Store results in files.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class FilePipeline extends FilePersistentBase implements Pipeline {
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ConnectionPool connectionPool=new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/spider", "root", "root");
    /**
     * create a FilePipeline with default path"/data/webmagic/"
     */
    public FilePipeline() {
        setPath("/data/webmagic/");
    }

    public FilePipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
    	List<String> list = resultItems.get("list");
    	SqlsessionInsert taxesInsert =new TaxesInsert();
		List<TaxesWithBLOBs> list1=new ArrayList<>();
		int num=list.size();
	for (int i=0;i<num;i++) {
		TaxesWithBLOBs taxesWithBLOBs=new TaxesWithBLOBs();
		taxesWithBLOBs.setCompany(list.get(i*10+0));
		taxesWithBLOBs.setNum(list.get(i*10+1));
		taxesWithBLOBs.setName(list.get(i*10+2));
		taxesWithBLOBs.setIdtype(list.get(i*10+3));
		taxesWithBLOBs.setIdNum(list.get(i*10+4));
		taxesWithBLOBs.setTax(list.get(i*10+5));
		taxesWithBLOBs.setDebt(list.get(i*10+6));
		taxesWithBLOBs.setAmount(list.get(i*10+7));
		taxesWithBLOBs.setCamount(list.get(i*10+8));
		taxesWithBLOBs.setSource(list.get(i*10+9));
		taxesWithBLOBs.setUpdated(new Date());
		list1.add(taxesWithBLOBs);
	}
		taxesInsert.setList(list1);	
		taxesInsert.insert();
		Map<String,String> map=new HashMap<String, String>();
		for (String str : json.keySet()) {
			map.put(str, json.getString(str));
		}
    	
    	/*
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
//        try {
//            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")),"UTF-8"));
//            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    printWriter.println(entry.getKey() + ":");
                    for (Object o : value) {
                        printWriter.println(o);
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                }
            }
//            System.out.println(resultItems);
//            System.out.println(resultItems.getAll().size());
            try {
				connectionPool.createPool();
				Connection conn = connectionPool.getConnection();
				JSONObject json = JSONObject.parseObject(JSON.toJSONString(resultItems.getAll()));
//				System.out.println(json);
//				String list=json.getString("detail");
//				System.out.println(list);
				List<String> list = resultItems.get("detail");
				String url=json.get("url").toString();
				String period=mapToString(json, "Period");
				String Period=period.substring(period.indexOf("（")+1).replace("）", "年");
				
				String Time=mapToString(json, "Time");
				String Source=mapToString(json,"Source");
				
//				Statement createStatement = conn.createStatement();
				int num = list.size();
					for (int i = 0; i < num / 7; i++) {
						System.out.println(
								"INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (null,"
										+ "'" + list.get(1 + i * 7) + "',NULL," + "'" + list.get(0 + i * 7)
										+ "','" + list.get(2 + i * 7) + "',NULL,'" + list.get(3 + i * 7)
										+ "','" + list.get(4 + i * 7) + "','" + list.get(5 + i * 7)
										+ "',NULL,NULL,'" + list.get(6 + i * 7) + "',NULL,'" + url + "','"
										+ Time + "','" + Period + "','" + Source + "','" + "天津国税" + "',NULL,"
										+ "NOW()" + ");");
						//																																																																	INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
						
						if (i < num / 7&&(7+i*7)<num&&list.get(7 + i * 7)!=null&&!list.get(7 + i * 7).equals("") && list.get(7 + i * 7).matches("[\u4e00-\u9fa5]+")) {
							System.out.println(list.get(7+i*7));
							System.out.println(6 + i * 7);
							System.out.println(5 + i * 7);
							list.remove(6 + i * 7);
							list.remove(5 + i * 7);
							--num;
							--num;
							--i;
					}
					int num = list.size();
					for (int i = 0; i < num / 7; i++) {
						System.out.println(
								"INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (null,"
										+ "'" + list.get(1 + i * 7) + "',NULL," + "'" + list.get(0 + i * 7)
										+ "','" + list.get(2 + i * 7) + "',NULL,'" + list.get(3 + i * 7)
										+ "','" + list.get(4 + i * 7) + "','" + list.get(5 + i * 7)
										+ "',NULL,NULL,'" + list.get(6 + i * 7) + "',NULL,'" + url + "','"
										+ Time + "','" + Period + "','" + Source + "','" + "天津国税" + "',NULL,"
										+ "NOW()" + ");");
						//																																																																	INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
						createStatement.execute(
								"INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (null,"
										+ "'" + list.get(1 + i * 7) + "',NULL," + "'" + list.get(0 + i * 7)
										+ "','" + list.get(2 + i * 7) + "',NULL,'" + list.get(3 + i * 7)
										+ "','" + list.get(4 + i * 7) + "','" + list.get(5 + i * 7)
										+ "',NULL,NULL,'" + list.get(6 + i * 7) + "',NULL,'" + url + "','"
										+ Time + "','" + Period + "','" + Source + "','" + "天津国税" + "',NULL,"
										+ "NOW()" + ");");
						
						if (i < num / 7&&(7+i*7)<num&&list.get(7 + i * 7)!=null&&!list.get(7 + i * 7).equals("") && list.get(7 + i * 7).matches("[\u4e00-\u9fa5]+")) {
							System.out.println(list.get(7+i*7));
							System.out.println(6 + i * 7);
							System.out.println(5 + i * 7);
							list.remove(6 + i * 7);
							list.remove(5 + i * 7);
							--num;
							--num;
							--i;
					}
            		SqlsessionInsert taxesInsert =new TaxesInsert();
            		List<TaxesWithBLOBs> list1=new ArrayList<>();
            		TaxesWithBLOBs taxesWithBLOBs=new TaxesWithBLOBs();
            		taxesWithBLOBs.setAddress("1324");
            		list1.add(taxesWithBLOBs);
            		taxesInsert.setList(list1);	
            		taxesInsert.insert();
					Map<String,String> map=new HashMap<String, String>();
					for (String str : json.keySet()) {
						map.put(str, json.getString(str));
					}
					Statement createStatement = conn.createStatement();
					System.out.println("INSERT INTO `spider`.`investevents` (`Id`, `title`, `company's_complete_financing_history`) VALUES (null,"+"'"+map.get("企业名称")+"','"+map.get("content")+"'"+");");
					createStatement.execute("INSERT INTO `spider`.`investevents` (`Id`, `title`, `company's_complete_financing_history`) VALUES (null,"+"'"+map.get("企业名称")+"','"+map.get("content")+"'"+");");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
          
//            	printWriter.println(json);
//            printWriter.close();
//        } catch (IOException e) {
//        	logger.warn("下载详情失败", resultItems.getRequest().getUrl());
//            logger.warn("write file error", e);
//        }
//    }
    public void process(ResultItems resultItems, Task task) {
    	String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
    	try {
    		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")),"UTF-8"));
    		printWriter.println("url:\t" + resultItems.getRequest().getUrl());
    		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
    			if (entry.getValue() instanceof Iterable) {
    				Iterable value = (Iterable) entry.getValue();
    				printWriter.println(entry.getKey() + ":");
    				for (Object o : value) {
    					printWriter.println(o);
    				}
    			} else {
    				printWriter.println(entry.getKey() + ":\t" + entry.getValue());
    			}
    		}
    		printWriter.close();
    	} catch (IOException e) {
    		logger.warn("write file error", e);
    	}
    }
*/}
    private String mapToString(JSONObject json,String str){
    	Map<String,String> Period=json.getObject(str,Map.class);
		String string = Period.get("firstSourceText").replace(" ", "");
		return string.trim();
    }
    public  void insert() throws IOException{
		String resource="config/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		TaxesWithBLOBs taxes=new TaxesWithBLOBs();
		taxes.setAddress("address");
		int selectList = session.insert("us.codecraft.webmagic.dao.TaxesMapper.insert", taxes);
		System.out.println(selectList);
		session.commit();
		session.close();
	}
}
