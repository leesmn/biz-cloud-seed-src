package com.arcplus.fm.mapper;

import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.vo.RepairWoVo;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.RepairVo;
import com.arcplus.fm.entity.BoRepair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 业务-保修
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Mapper
public interface BoRepairMapper  {
    int save(BoRepair entity);

    int update(BoRepair entity);

    int updateByCode(BoRepair entity);

    int delete(int id);

    List<BoRepair> selectAll();

    RepairVo getRepairByCode(String repairCode);

    @Select("<script>" +
            "   select * from bo_repair " +
            "   where repair_id = #{id} " +
            "</script>")
    BoRepair getById(int id);

    @Select("<script>" +
            " SELECT" +
            "     repair.*, " +
            "     fail.failure_name failure_type_name, " +
            "     org.org_name branch_company_name " +
            " FROM bo_repair repair " +
            "   LEFT JOIN tp_failure fail ON repair.failure_type_id=fail.failure_id " +
            "   LEFT JOIN co_organization org ON repair.branch_company_id=org.org_id " +

            " WHERE repair.data_status = 'N' " +

            " <if test='fromDate != null'>" +
            "     and " +
            "       repair.create_at &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       repair.create_at &lt;= #{toDate} " +
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
    List<RepairVo> search(Map<String, Object> prama);



    List<RepairWoVo> selectRepairWizWo(int buildId,int floorId);

    List<FloorCountVo> countByFloor(int buildId);
}
