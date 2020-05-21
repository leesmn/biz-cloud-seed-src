package com.arcplus.fm.service;

import com.arcplus.fm.entity.CoVersion;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共同-系统版本
 *
 * @author jv_team
 * @email 
 * @date 2020-05-07 18:13:05
 */
public interface CoVersionService  {
    CoVersion save(CoVersion entity);

    int update(CoVersion entity);

    int delete(int id);

    PageInfo<CoVersion> selectAllPage(int page,int size);

    CoVersion getById(int id);

    PageInfo<CoVersion> search(Map<String, Object> prama, int page, int size);
}