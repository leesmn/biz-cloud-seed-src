package com.arcplus.fm.service;

import com.arcplus.fm.entity.CoOrganization;
import com.arcplus.fm.entity.CoPriorityLevel;
import com.arcplus.fm.entity.TpFailure;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共用-优先级别
 *
 * @author jv_team
 * @email 
 * @date 2020-04-20 14:20:07
 */
public interface CoPriorityLevelService  {
    CoPriorityLevel save(CoPriorityLevel entity);

    int update(CoPriorityLevel entity);

    int delete(int id);

    PageInfo<CoPriorityLevel> selectAllPage(int page,int size);

    CoPriorityLevel getById(int id);

    PageInfo<CoPriorityLevel> search(Map<String, Object> param, int page, int size);
}