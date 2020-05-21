package com.arcplus.fm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.config.ExceptionHandlerAdvice;
import com.arcplus.fm.entity.CoVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.TpFailureService;
import com.arcplus.fm.entity.TpFailure;
import com.arcplus.fm.mapper.TpFailureMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;

/**
 * 共同-故障类型
 *
 * @author jv_team
 * @email 
 * @date 2020-04-15 16:13:22
 */
@Service
public class TpFailureServiceImpl implements TpFailureService  {
    @Autowired
    TpFailureMapper instTpFailureMapper;

    @Override
    public TpFailure save(TpFailure entity) {

        HashMap<String, Object> selectParam = new HashMap<>();
        selectParam.put("failureName", entity.getFailureName());

        if (0 != instTpFailureMapper.search(selectParam).size()) {
            throw new ExceptionHandlerAdvice.CustomerArgumentException(400, "故障类型名重复");
        }

        instTpFailureMapper.save(entity);
        return entity;
    }

    @Override
    public int update(TpFailure entity) {
        return instTpFailureMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instTpFailureMapper.delete(id);
    }

    @Override
    public PageInfo<TpFailure> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<TpFailure> pageResult = instTpFailureMapper.selectAll();
        PageInfo<TpFailure> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public TpFailure getById(int id) {
        return instTpFailureMapper.getById(id);
    }

    @Override
    public PageInfo<TpFailure> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<TpFailure> pageResult = instTpFailureMapper.search(param);

        PageInfo<TpFailure> result = new PageInfo(pageResult);
        return result;
    }
}