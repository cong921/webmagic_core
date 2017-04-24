package us.codecraft.webmagic.pipeline;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.processor.example.ConnectionPool;
import us.codecraft.webmagic.selector.AbstractSelectable;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.PlainText;
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
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
//        try {
//            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")),"UTF-8"));
//            printWriter.println("url:\t" + resultItems.getRequest().getUrl());
            /*for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    printWriter.println(entry.getKey() + ":");
                    for (Object o : value) {
                        printWriter.println(o);
                    }
                } else {
                    printWriter.println(entry.getKey() + ":\t" + entry.getValue());
                }
            }*/
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
				Statement createStatement = conn.createStatement();
				for (int i = 0; i < list.size()/7; i++) {
					System.out.println("INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (null,"+"'"+list.get(1)+"',NULL,"+"'"+list.get(0+i*7)+"','"+list.get(2+i*7)+"',NULL,'"+list.get(3+i*7)+"','"+list.get(4+i*7)+"','"+list.get(5+i*7)+"','"+list.get(6+i*7)+"',NULL,NULL,NULL,'"+url+"','"+Time+"','"+Period+"','"+Source+"','"+"天津国税"+"',NULL,"+"NOW()"+");");
					//																																																																	INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
					createStatement.execute("INSERT INTO `spider`.`taxes` (`id`, `Company`, `Type`, `Num`, `Name`, `Idtype`, `Id_num`, `Address`, `Tax`, `Amount`, `Camount`, `Debt`, `Detail`, `Url`, `Time`, `Period`, `Source`, `label`, `hash_md5`, `Updated`) VALUES (null,"+"'"+list.get(1+i*7)+"',NULL,"+"'"+list.get(0+i*7)+"','"+list.get(2+i*7)+"',NULL,'"+list.get(3+i*7)+"','"+list.get(4+i*7)+"','"+list.get(5+i*7)+"',NULL,NULL,'"+list.get(6+i*7)+"',NULL,'"+url+"','"+Time+"','"+Period+"','"+Source+"','"+"天津国税"+"',NULL,"+"NOW()"+");");
					if(i<list.size()/7&&list.get(18+i*7).matches("\\d\\+(\\.)*\\d+")){
						list.remove(5+i*7);
						list.remove(6+i*7);
						--i;
					}
				}
				/*Map<String,String> map=new HashMap<String, String>();
				for (String str : json.keySet()) {
					map.put(str, json.getString(str));
				}
				Statement createStatement = conn.createStatement();
				System.out.println("INSERT INTO `spider`.`investevents` (`Id`, `title`, `company's_complete_financing_history`) VALUES (null,"+"'"+map.get("企业名称")+"','"+map.get("content")+"'"+");");
				createStatement.execute("INSERT INTO `spider`.`investevents` (`Id`, `title`, `company's_complete_financing_history`) VALUES (null,"+"'"+map.get("企业名称")+"','"+map.get("content")+"'"+");");*/
			} catch (SQLException e) {
				e.printStackTrace();
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
/*    public void process(ResultItems resultItems, Task task) {
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
}
