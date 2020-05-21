package com.arcplus.fm.service;

import com.arcplus.fm.entity.CoOrganization;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共同-集团公司架构
 *
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:32:06
 */
public interface CoOrganizationService  {
    CoOrganization save(CoOrganization entity);

    int update(CoOrganization entity);

    int delete(int id);

    PageInfo<CoOrganization> selectAllPage(int page, int size);

    CoOrganization getById(int id);

    PageInfo<CoOrganization> search(Map<String, Object> param, int page, int size);
}