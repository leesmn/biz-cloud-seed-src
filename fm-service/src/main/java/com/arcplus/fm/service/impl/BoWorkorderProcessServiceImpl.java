package com.arcplus.fm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.BoWorkorderProcessService;
import com.arcplus.fm.entity.BoWorkorderProcess;
import com.arcplus.fm.mapper.BoWorkorderProcessMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 业务-保修处理过程
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Service
public class BoWorkorderProcessServiceImpl implements BoWorkorderProcessService  {
    @Autowired
    BoWorkorderProcessMapper instBoWorkorderProcessMapper;

    @Override
    public BoWorkorderProcess save(BoWorkorderProcess entity) {
        instBoWorkorderProcessMapper.save(entity);
        return entity;
    }

    @Override
    public int update(BoWorkorderProcess entity) {
        return instBoWorkorderProcessMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instBoWorkorderProcessMapper.delete(id);
    }

    @Override
    public PageInfo<BoWorkorderProcess> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<BoWorkorderProcess> pageResult = instBoWorkorderProcessMapper.selectAll();
        PageInfo<BoWorkorderProcess> reslut = new PageInfo(pageResult);
        return reslut;
    }
}