package com.arcplus.fm.service;

import com.arcplus.fm.entity.vo.BillVo;
import com.arcplus.fm.entity.CoBill;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共同-账单
 *
 * @author jv_team
 * @email 
 * @date 2020-05-07 15:02:03
 */
public interface CoBillService  {
    CoBill save(CoBill entity);

    int update(CoBill entity);

    int delete(int id);

    PageInfo<CoBill> selectAllPage(int page,int size);

    PageInfo<BillVo> search(Map<String, Object> param, int page, int size);
}