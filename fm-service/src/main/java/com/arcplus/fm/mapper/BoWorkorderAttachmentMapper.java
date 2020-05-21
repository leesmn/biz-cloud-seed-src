package com.arcplus.fm.mapper;

import java.util.List;
import com.arcplus.fm.entity.BoWorkorderAttachment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 工单附件表
 * 
 * @author jv_team
 * @email 
 * @date 2020-05-09 17:19:20
 */
@Mapper
public interface BoWorkorderAttachmentMapper  {
    int save(BoWorkorderAttachment entity);

    int update(BoWorkorderAttachment entity);

    int delete(int id);

    List<BoWorkorderAttachment> selectAll();

    List<BoWorkorderAttachment> selectByWoCode(String woCode);
}
