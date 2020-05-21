package com.arcplus.fm.service;

import com.arcplus.fm.entity.BoWorkorderProcess;
import com.github.pagehelper.PageInfo;

/**
 * 业务-保修处理过程
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public interface BoWorkorderProcessService  {
    BoWorkorderProcess save(BoWorkorderProcess entity);

    int update(BoWorkorderProcess entity);

    int delete(int id);

    PageInfo<BoWorkorderProcess> selectAllPage(int page,int size);
}