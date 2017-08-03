package us.codecraft.webmagic.test;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.domain.Exchange;
import us.codecraft.webmagic.domain.Taxes;

public class JubiInsert extends SqlsessionInsert {
	private List<Exchange> list=new ArrayList<>();
	
	public List<Exchange> getList() {
		return  list;
	}


	@Override
	public void domethod() {
		for (Exchange exchange : list) {
			sqlSession.insert("us.codecraft.webmagic.dao.ExchangeMapper.insert", exchange);
		}
		
	}

	@Override
	public void setList(List<Exchange> list1) {
		this.list = list1;
		
	}

}
