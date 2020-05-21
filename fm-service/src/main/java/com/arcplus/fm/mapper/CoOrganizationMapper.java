package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.CoOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 共同-集团公司架构
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:32:06
 */
@Mapper
public interface CoOrganizationMapper  {
    int save(CoOrganization entity);

    int update(CoOrganization entity);

    int delete(int id);

    List<CoOrganization> selectAll();

    @Select("<script>" +
            "   select * from co_organization " +
            "   where org_id = #{id} " +
            "</script>")
    CoOrganization getById(int id);


    @Select("<script>" +
            " SELECT" +
            "     * " +
            " FROM co_organization org" +

            " WHERE org.data_status = 'N'" +

            " <if test='orgId != null'>" +
            "   AND org.org_id = #{orgId}" +
            " </if>" +
            " <if test='orgName != null'>" +
            "   AND org.org_name = #{orgName}" +
            " </if>" +
            " <if test='orgDescribe != null'>" +
            "   AND org.org_describe = #{orgDescribe}" +
            " </if>" +
            " <if test='parentId != null'>" +
            "   AND org.parent_id = #{parentId}" +
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
    List<CoOrganization> search(Map<String, Object> prama);
}
