package com.arcplus.fm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoVersionService;
import com.arcplus.fm.entity.CoVersion;
import com.arcplus.fm.mapper.CoVersionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 共同-系统版本
 *
 * @author jv_team
 * @email 
 * @date 2020-05-07 18:13:05
 */
@Service
public class CoVersionServiceImpl implements CoVersionService  {
    @Autowired
    CoVersionMapper instCoVersionMapper;

    @Override
    public CoVersion save(CoVersion entity) {
        instCoVersionMapper.save(entity);
        return entity;
    }

    @Override
    public int update(CoVersion entity) {
        return instCoVersionMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instCoVersionMapper.delete(id);
    }

    @Override
    public PageInfo<CoVersion> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<CoVersion> pageResult = instCoVersionMapper.selectAll();
        PageInfo<CoVersion> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public CoVersion getById(int id) {
        return instCoVersionMapper.getById(id);
    }

    @Override
    public PageInfo<CoVersion> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<CoVersion> pageResult = instCoVersionMapper.search(param);

        PageInfo<CoVersion> result = new PageInfo(pageResult);
        return result;
    }

}