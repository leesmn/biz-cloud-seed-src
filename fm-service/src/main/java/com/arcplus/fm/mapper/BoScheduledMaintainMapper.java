package com.arcplus.fm.mapper;

import java.util.List;

import com.arcplus.fm.entity.vo.ScheduledMaintainStaticVo;
import com.arcplus.fm.entity.BoScheduledMaintain;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 业务-定期维护
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Mapper
public interface BoScheduledMaintainMapper  {
    int save(BoScheduledMaintain entity);

    int update(BoScheduledMaintain entity);

    int delete(int id);

    List<BoScheduledMaintain> selectAll();

    @Select("<script>" +
            "   select * from bo_scheduled_maintain " +
            "   where scheduled_id = #{id} " +
            "</script>")
    BoScheduledMaintain getById(int id);

    BoScheduledMaintain getByGroupUuid(String groupUuid);
    int updateByGroupUuid(BoScheduledMaintain entity);
    int deleteByGroupUuid(String groupUuid);

    List<BoScheduledMaintain> selectMonth(String monthFirstDay);

    List<BoScheduledMaintain> selectByAssignment(int uid);

    List<BoScheduledMaintain> selectScheduledMaintainComplex(Map<String, Object> prama);


    @Select("<script>" +
            " SELECT" +
            "     * " +
            " FROM bo_scheduled_maintain sm" +

            " WHERE sm.data_status = 'N'" +

            " <if test='dealStatus != null'>" +
            "     and " +
            "       sm.deal_status = #{dealStatus} " +
            " </if>" +

            " <if test='fromDate != null'>" +
            "     and " +
            "       sm.maintain_start_at &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       sm.maintain_start_at &lt;= #{toDate} " +
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
    List<ScheduledMaintainStaticVo> search(Map<String, Object> prama);
}
