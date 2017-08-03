package us.codecraft.webmagic.pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.domain.Exchange;
import us.codecraft.webmagic.processor.example.ConnectionPool;
import us.codecraft.webmagic.test.JubiInsert;
import us.codecraft.webmagic.test.SqlsessionInsert;
import us.codecraft.webmagic.utils.FilePersistentBase;

/**
 * Store results in files.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class JubiPipleline extends FilePersistentBase implements Pipeline {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static ConnectionPool connectionPool = new ConnectionPool("com.mysql.jdbc.Driver",
			"jdbc:mysql://localhost:3306/spider", "root", "root");

	/**
	 * create a FilePipeline with default path"/data/webmagic/"
	 */
	public JubiPipleline() {
		setPath("/data/webmagic/");
	}

	public JubiPipleline(String path) {
		setPath(path);
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		List<List<Object>> list = resultItems.get("list");
		System.out.println(list);
		SqlsessionInsert taxesInsert = new JubiInsert();
		List<Exchange> list1 = new ArrayList<>();
		for (List<Object> listStr : list) {
			Exchange exchange = new Exchange();
			exchange.setDate(listStr.get(0).toString());
			exchange.setPrice((float) listStr.get(1));
			exchange.setAmout((float) listStr.get(2));
			exchange.setTid((Integer) listStr.get(3));
			exchange.setType(listStr.get(4).toString());
			list1.add(exchange);
		}
		taxesInsert.setList(list1);
		try {
			taxesInsert.insert();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
