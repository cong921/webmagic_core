package us.codecraft.webmagic.test;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.domain.Exchange;
import us.codecraft.webmagic.domain.Taxes;

public class TaxesInsert extends SqlsessionInsert {
	private List<Taxes> list=new ArrayList<>();
	
	public List<Taxes> getList() {
		return list;
	}


	@Override
	public void domethod() {
		for (Taxes taxesWithBLOBs : list) {
			sqlSession.insert("us.codecraft.webmagic.dao.TaxesMapper.insert", taxesWithBLOBs);
		}
		
	}


	@Override
	public void setList(List<Exchange> list1) {
		// TODO Auto-generated method stub
		
	}

/*	@Override
	public void setList(List<? extends Object> list1) {
		this.list=list;
		
	}


	@Override
	public void setList(List<Exchange> list1) {
		// TODO Auto-generated method stub
		
	}
*/
}
