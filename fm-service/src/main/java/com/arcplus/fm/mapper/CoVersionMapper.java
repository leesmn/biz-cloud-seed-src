package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.CoVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



/**
 * 共同-系统版本
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-07 18:13:05
 */
@Mapper
public interface CoVersionMapper  {
    int save(CoVersion entity);

    int update(CoVersion entity);

    int delete(int id);

    List<CoVersion> selectAll();



    @Select("<script>" +
            "   select * from co_version " +
            "   where version_id = #{id} " +
            "</script>")
    CoVersion getById(int id);

    @Select("<script>" +
            " SELECT * from co_version " +

            " WHERE data_status = 'N' " +

            " <if test='fromDate != null'>" +
            "     and " +
            "       release_at &gt;= #{fromDate} " +
            " </if>" +

            " <if test='toDate != null'>" +
            "     and " +
            "       release_at &lt;= #{toDate} " +
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
    List<CoVersion> search(Map<String, Object> prama);

}
