package com.arcplus.fm.service;

import com.arcplus.fm.entity.CoOrganization;
import com.arcplus.fm.entity.CoVersion;
import com.arcplus.fm.entity.TpFailure;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共同-故障类型
 *
 * @author jv_team
 * @email 
 * @date 2020-04-15 16:13:22
 */
public interface TpFailureService  {
    TpFailure save(TpFailure entity);

    int update(TpFailure entity);

    int delete(int id);

    PageInfo<TpFailure> selectAllPage(int page,int size);

    TpFailure getById(int id);

    PageInfo<TpFailure> search(Map<String, Object> param, int page, int size);
}