package us.codecraft.webmagic.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.annotation.ThreadSafe;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.processor.example.ConnectionPool;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * Store results in files.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class JsonFilePipeline extends FilePersistentBase implements Pipeline {
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ConnectionPool connectionPool=new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/spider", "root", "root");
    /**
     * create a FilePipeline with default path"/data/webmagic/"
     */
    public JsonFilePipeline() {
        setPath("/data/webmagic/");
    }

    public JsonFilePipeline(String path) {
        setPath(path);
    }

    @Override
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
    private String mapToString(JSONObject json,String str){
    	Map<String,String> Period=json.getObject(str,Map.class);
		String string = Period.get("firstSourceText").replace(" ", "");
		return string.trim();
    }
}
