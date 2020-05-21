package com.arcplus.fm.service;

import com.arcplus.fm.entity.TpAsset;
import com.github.pagehelper.PageInfo;

/**
 * 共同-资产分类
 *
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:31:12
 */
public interface TpAssetService  {
    TpAsset save(TpAsset entity);

    int update(TpAsset entity);

    int delete(int id);

    PageInfo<TpAsset> selectAllPage(int page, int size);
}