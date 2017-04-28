package us.codecraft.webmagic.test;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.domain.Taxes;

public class TaxesInsert extends SqlsessionInsert {
	private List<Taxes> list=new ArrayList<>();
	
	public List<Taxes> getList() {
		return list;
	}

	public void setList(List<Taxes> list) {
		this.list = list;
	}

	@Override
	public void domethod() {
		for (Taxes taxesWithBLOBs : list) {
			sqlSession.insert("us.codecraft.webmagic.dao.TaxesMapper.insert", taxesWithBLOBs);
		}
		
	}

}
