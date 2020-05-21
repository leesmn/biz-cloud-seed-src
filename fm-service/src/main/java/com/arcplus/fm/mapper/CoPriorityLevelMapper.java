package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.CoOrganization;
import com.arcplus.fm.entity.CoPriorityLevel;
import com.arcplus.fm.entity.TpFailure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 共用-优先级别
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-20 14:20:07
 */
@Mapper
public interface CoPriorityLevelMapper  {
    int save(CoPriorityLevel entity);

    int update(CoPriorityLevel entity);

    int delete(int id);

    List<CoPriorityLevel> selectAll();

    @Select("<script>" +
            "   select * from co_priority_level " +
            "   where priority_id = #{id} " +
            "</script>")
    CoPriorityLevel getById(int id);

    @Select("<script>" +
            " SELECT" +
            "     * " +
            " FROM co_priority_level pri" +

            " WHERE pri.data_status = 'N'" +

            " <if test='priorityId != null'>" +
            "   AND pri.priority_id = #{priorityId}" +
            " </if>" +
            " <if test='levelValue != null'>" +
            "   AND pri.level_value = #{levelValue}" +
            " </if>" +
            " <if test='levelName != null'>" +
            "   AND pri.level_name = #{levelName}" +
            " </if>" +
            " <if test='responeTime != null'>" +
            "   AND pri.respone_time = #{responeTime}" +
            " </if>" +
            " <if test='remark != null'>" +
            "   AND pri.remark = #{remark}" +
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
    List<CoPriorityLevel> search(Map<String, Object> prama);
}
