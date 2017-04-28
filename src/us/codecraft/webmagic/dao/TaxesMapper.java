package us.codecraft.webmagic.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import us.codecraft.webmagic.domain.Taxes;
import us.codecraft.webmagic.domain.TaxesExample;

public interface TaxesMapper {
    int countByExample(TaxesExample example);

    int deleteByExample(TaxesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Taxes record);

    int insertSelective(Taxes record);

    List<Taxes> selectByExampleWithBLOBs(TaxesExample example);

    List<Taxes> selectByExample(TaxesExample example);

    Taxes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Taxes record, @Param("example") TaxesExample example);

    int updateByExampleWithBLOBs(@Param("record") Taxes record, @Param("example") TaxesExample example);

    int updateByExample(@Param("record") Taxes record, @Param("example") TaxesExample example);

    int updateByPrimaryKeySelective(Taxes record);

    int updateByPrimaryKeyWithBLOBs(Taxes record);

    int updateByPrimaryKey(Taxes record);
}