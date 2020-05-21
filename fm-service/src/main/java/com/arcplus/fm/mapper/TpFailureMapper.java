package com.arcplus.fm.mapper;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.CoOrganization;
import com.arcplus.fm.entity.CoVersion;
import com.arcplus.fm.entity.TpFailure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 共同-故障类型
 * 
 * @author jv
 * @email 
 * @date 2020-04-15 16:13:22
 */
@Mapper
public interface TpFailureMapper  {
    int save(TpFailure entity);

    int update(TpFailure entity);

    int delete(int id);

    List<TpFailure> selectAll();

    @Select("<script>" +
            "   select * from tp_failure " +
            "   where failure_id = #{id} " +
            "</script>")
    TpFailure getById(int id);

    @Select("<script>" +
            " SELECT" +
            "     * " +
            " FROM tp_failure fail" +

            " WHERE fail.data_status = 'N'" +

            " <if test='failureId != null'>" +
            "   AND fail.failure_id = #{failureId}" +
            " </if>" +
            " <if test='failureName != null'>" +
            "   AND fail.failure_name = #{failureName}" +
            " </if>" +
            " <if test='failureDescribe != null'>" +
            "   AND fail.failure_describe = #{failureDescribe}" +
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
    List<TpFailure> search(Map<String, Object> prama);
}
