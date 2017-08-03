package us.codecraft.webmagic.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import us.codecraft.webmagic.domain.Exchange;
import us.codecraft.webmagic.domain.SuperBean;
import us.codecraft.webmagic.domain.Taxes;

public abstract class SqlsessionInsert {
	protected SqlSession sqlSession;
	{sqlSession=getSqlSession();}
	public SqlSession getSqlSession() {
		String resource="config/mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory.openSession();
	}
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	@Test    
	public  void insert() throws IOException{
		
		/*TaxesWithBLOBs taxes=new TaxesWithBLOBs();
		taxes.setAddress("address");
		int selectList = session.insert("us.codecraft.webmagic.dao.TaxesMapper.insert", taxes);*/
		domethod();
		this.sqlSession.commit();
		this.sqlSession.close();
	}
	public abstract  void domethod();
	public abstract void setList(List<Exchange> list1) ;
}
