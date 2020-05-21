package com.arcplus.fm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.config.ExceptionHandlerAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoOrganizationService;
import com.arcplus.fm.entity.CoOrganization;
import com.arcplus.fm.mapper.CoOrganizationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;

/**
 * 共同-集团公司架构
 *
 * @author jv_team
 * @email
 * @date 2020-04-17 10:32:06
 */
@Service
public class CoOrganizationServiceImpl implements CoOrganizationService {
    @Autowired
    CoOrganizationMapper instCoOrganizationMapper;

    @Override
    public CoOrganization save(CoOrganization entity) {

        HashMap<String, Object> selectParam = new HashMap<>();
        selectParam.put("orgName", entity.getOrgName());
        if (0 != instCoOrganizationMapper.search(selectParam).size()) {
            throw new ExceptionHandlerAdvice.CustomerArgumentException(400, "公司名重复");
        }

        instCoOrganizationMapper.save(entity);
        return entity;
    }

    @Override
    public int update(CoOrganization entity) {
        return instCoOrganizationMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instCoOrganizationMapper.delete(id);
    }

    @Override
    public PageInfo<CoOrganization> selectAllPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<CoOrganization> pageResult = instCoOrganizationMapper.selectAll();
        PageInfo<CoOrganization> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public CoOrganization getById(int id) {
        return instCoOrganizationMapper.getById(id);
    }

    @Override
    public PageInfo<CoOrganization> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<CoOrganization> pageResult = instCoOrganizationMapper.search(param);

        PageInfo<CoOrganization> result = new PageInfo(pageResult);
        return result;
    }
}