package com.arcplus.fm.service.impl;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.BillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoBillService;
import com.arcplus.fm.entity.CoBill;
import com.arcplus.fm.mapper.CoBillMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 共同-账单
 *
 * @author jv_team
 * @email 
 * @date 2020-05-07 15:02:03
 */
@Service
public class CoBillServiceImpl implements CoBillService  {
    @Autowired
    CoBillMapper instCoBillMapper;

    @Override
    public CoBill save(CoBill entity) {
        instCoBillMapper.save(entity);
        return entity;
    }

    @Override
    public int update(CoBill entity) {
        return instCoBillMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instCoBillMapper.delete(id);
    }

    @Override
    public PageInfo<CoBill> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<CoBill> pageResult = instCoBillMapper.selectAll();
        PageInfo<CoBill> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public PageInfo<BillVo> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page,size);
        List<BillVo> pageResult = instCoBillMapper.search(param);
        PageInfo<BillVo> reslut = new PageInfo(pageResult);
        return reslut;
    }
}