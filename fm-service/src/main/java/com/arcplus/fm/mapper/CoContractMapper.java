package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.ContractVo;
import com.arcplus.fm.entity.CoContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * 共同-合同
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-07 15:02:03
 */
@Mapper
public interface CoContractMapper  {

    int save(CoContract entity);

    int update(CoContract entity);

    int delete(int id);

    List<CoContract> selectAll();


    @Select("<script>" +
            " SELECT" +
            "     ct.*," +
            "     org.org_name contract_target_name " +

            " FROM co_contract ct" +
            " LEFT JOIN co_organization org ON ct.contract_target=org.org_id " +

            " WHERE ct.data_status = 'N'" +
            "   AND ct.parent_contract = 0 " +

            " <if test='contractStatus != null'>" +
            "     AND " +
            "       ct.contract_status = #{contractStatus} " +
            " </if>" +

            " <if test='fromDate != null'>" +
            "     and " +
            "       ct.from_date &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       ct.from_date &lt;= #{toDate} " +
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
    List<ContractVo> search(Map<String, Object> param);

    @Select("<script>" +
            " SELECT" +
            "     ct.*," +
            "     org.org_name contract_target_name " +

            " FROM co_contract ct" +
            " LEFT JOIN co_organization org ON ct.contract_target=org.org_id " +

            " WHERE ct.data_status = 'N'" +
            "" +
            "   AND ct.parent_contract = #{parentId}" +
            " ORDER BY ct.contract_id desc" +
            "</script>")
    List<ContractVo> searchAdditionalContractList(int parentId);


    @Update("<script>" +
            " UPDATE" +
            " co_contract " +
            " SET contract_status='effective' " +

            " WHERE contract_status='inactive' " +

            "   AND " +
            "       to_date &gt;= NOW() " +

            "   AND " +
            "       from_date &lt;= NOW() " +
            "</script>")
    int updateContractEffective();

    @Update("<script>" +
            " UPDATE" +
            " co_contract " +
            " SET contract_status='expire' " +

            " WHERE " +

            "       to_date &lt; NOW() " +
            "</script>")
    int updateContractExpire();
}
