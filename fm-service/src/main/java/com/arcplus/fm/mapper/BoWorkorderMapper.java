package com.arcplus.fm.mapper;

import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.BoWorkorder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 业务-设备资产
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Mapper
public interface BoWorkorderMapper  {
    int save(BoWorkorder entity);

    int update(BoWorkorder entity);

    int updateByCode(BoWorkorder entity);

    int delete(int id);

    BoWorkorder getWorkorderByCode(String woCode);

    List<BoWorkorder> selectAll();

    @Select("<script>" +
            "   select * from bo_workorder " +
            "   where wo_id = #{id} " +
            "</script>")
    BoWorkorder getById(int id);

    @Select("<script>" +
            " SELECT" +
            "     * " +
            " FROM bo_workorder wo" +

            " WHERE wo.data_status = 'N'" +

            " <if test='statusArray != null'>" +
            "     and " +
            "     <foreach collection='statusArray' item='value' index='key'" +
            "              open=' ' separator=' or ' close=' '>" +
            "         wo.deal_status = #{value} " +
            "     </foreach>" +
            " </if>" +

            " <if test='fromDate != null'>" +
            "     and " +
            "       wo.create_at &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       wo.create_at &lt;= #{toDate} " +
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
    List<BoWorkorder> search(Map<String, Object> prama);


    List<BoWorkorder> selectWorkorderComplex(Map<String, Object> prama);

    List<FloorCountVo> countByFloor(int buildId);

    List<BoWorkorder> selectByFloor(int buildId,int floorId);
}
