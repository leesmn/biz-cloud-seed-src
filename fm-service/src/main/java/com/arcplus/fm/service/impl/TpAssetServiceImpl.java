package com.arcplus.fm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.TpAssetService;
import com.arcplus.fm.entity.TpAsset;
import com.arcplus.fm.mapper.TpAssetMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 共同-资产分类
 *
 * @author jv_team
 * @email 
 * @date 2020-04-17 10:31:12
 */
@Service
public class TpAssetServiceImpl implements TpAssetService  {
    @Autowired
    TpAssetMapper instTpAssetMapper;

    @Override
    public TpAsset save(TpAsset entity) {
        instTpAssetMapper.save(entity);
        return entity;
    }

    @Override
    public int update(TpAsset entity) {
        return instTpAssetMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instTpAssetMapper.delete(id);
    }

    @Override
    public PageInfo<TpAsset> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<TpAsset> pageResult = instTpAssetMapper.selectAll();
        PageInfo<TpAsset> reslut = new PageInfo(pageResult);
        return reslut;
    }
}