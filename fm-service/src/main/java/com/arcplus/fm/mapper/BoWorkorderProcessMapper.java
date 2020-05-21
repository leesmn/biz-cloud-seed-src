package com.arcplus.fm.mapper;

import java.util.List;
import com.arcplus.fm.entity.BoWorkorderProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 业务-保修处理过程
 * 
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Mapper
public interface BoWorkorderProcessMapper  {
    int save(BoWorkorderProcess entity);

    int update(BoWorkorderProcess entity);

    int delete(int id);

    List<BoWorkorderProcess> selectAll();

    @Select("<script>" +
            "   select * from bo_workorder_process " +
            "   where process_id = #{id} " +
            "</script>")
    BoWorkorderProcess getById(int id);

    List<BoWorkorderProcess> selectByWoCode(String woCode);
}
