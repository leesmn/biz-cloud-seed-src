package com.arcplus.fm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.config.ExceptionHandlerAdvice;
import com.arcplus.fm.entity.CoOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoPriorityLevelService;
import com.arcplus.fm.entity.CoPriorityLevel;
import com.arcplus.fm.mapper.CoPriorityLevelMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;

/**
 * 共用-优先级别
 *
 * @author jv_team
 * @email 
 * @date 2020-04-20 14:20:07
 */
@Service
public class CoPriorityLevelServiceImpl implements CoPriorityLevelService  {
    @Autowired
    CoPriorityLevelMapper instCoPriorityLevelMapper;

    @Override
    public CoPriorityLevel save(CoPriorityLevel entity) {

        HashMap<String, Object> selectParam = new HashMap<>();
        selectParam.put("levelName", entity.getLevelName());

        if (0 != instCoPriorityLevelMapper.search(selectParam).size()) {
            throw new ExceptionHandlerAdvice.CustomerArgumentException(400, "优先级名重复");
        }

        instCoPriorityLevelMapper.save(entity);
        return entity;
    }

    @Override
    public int update(CoPriorityLevel entity) {
        return instCoPriorityLevelMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instCoPriorityLevelMapper.delete(id);
    }

    @Override
    public PageInfo<CoPriorityLevel> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<CoPriorityLevel> pageResult = instCoPriorityLevelMapper.selectAll();
        PageInfo<CoPriorityLevel> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public CoPriorityLevel getById(int id) {
        return instCoPriorityLevelMapper.getById(id);
    }

    @Override
    public PageInfo<CoPriorityLevel> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<CoPriorityLevel> pageResult = instCoPriorityLevelMapper.search(param);

        PageInfo<CoPriorityLevel> result = new PageInfo(pageResult);
        return result;
    }
}