package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.BillVo;
import com.arcplus.fm.entity.CoBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 共同-账单
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-07 15:02:03
 */
@Mapper
public interface CoBillMapper  {
    int save(CoBill entity);

    int update(CoBill entity);

    int delete(int id);

    List<CoBill> selectAll();

    @Select("<script>" +
            " SELECT" +
            "     bill.*," +
            "     org.org_name customer_name, " +
            "     ct.create_at contract_create_time, " +
            "     ct.contract_code contract_code " +

            " FROM co_bill bill" +
            " LEFT JOIN co_contract ct ON bill.contract_id=ct.contract_id " +
            " LEFT JOIN co_organization org ON ct.contract_target=org.org_id " +

            " WHERE bill.data_status = 'N'" +

            " <if test='fromDate != null'>" +
            "     and " +
            "       str_to_date(concat(bill.bill_period,'-01'), '%Y-%m-%d') &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       str_to_date(concat(bill.bill_period,'-01'), '%Y-%m-%d') &lt;= #{toDate} " +
            " </if>" +

            " <if test='inSearch != null'>" +
            "     and " +
            "     <foreach collection='inSearch' item='value' index='key'" +
            "              open=' ' separator=' and ' close=' '>" +
            "            ${key} in " +
            "        <foreach collection='value' item='v' index='i'" +
            "              open=' (' separator=',' close=') '>" +
            "            #{v}" +
            "        </foreach>" +
            "     </foreach>" +
            " </if>" +
            " <if test='fuzzySearch != null'>" +
            "     and " +
            "     <foreach collection='fuzzySearch' item='value' index='key'" +
            "              open=' ' separator=' and ' close=' '>" +
            "         ${key} like #{value} " +
            "     </foreach>" +
            " </if>" +
            " <if test='orderBy != null'>" +
            "     order by ${orderBy}" +
            " </if>" +
            "</script>")
    List<BillVo> search(Map<String, Object> param);
}
