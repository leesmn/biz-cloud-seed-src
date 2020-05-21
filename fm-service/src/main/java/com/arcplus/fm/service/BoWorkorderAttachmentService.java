package com.arcplus.fm.service;

import com.arcplus.fm.entity.BoWorkorderAttachment;
import com.github.pagehelper.PageInfo;

/**
 * 工单附件表
 *
 * @author jv_team
 * @email 
 * @date 2020-05-09 17:19:20
 */
public interface BoWorkorderAttachmentService  {
    BoWorkorderAttachment save(BoWorkorderAttachment entity);

    int update(BoWorkorderAttachment entity);

    int delete(int id);

    PageInfo<BoWorkorderAttachment> selectAllPage(int page,int size);
}