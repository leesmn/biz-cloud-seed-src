package com.arcplus.fm.service;

import com.arcplus.fm.entity.vo.ContractVo;
import com.arcplus.fm.entity.CoContract;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 共同-合同
 *
 * @author jv_team
 * @email 
 * @date 2020-05-07 15:02:03
 */
public interface CoContractService  {
    CoContract save(CoContract entity);

    int update(CoContract entity);

    int delete(int id);

    PageInfo<CoContract> selectAllPage(int page,int size);

    PageInfo<ContractVo> search(Map<String, Object> param, int page, int size);
}