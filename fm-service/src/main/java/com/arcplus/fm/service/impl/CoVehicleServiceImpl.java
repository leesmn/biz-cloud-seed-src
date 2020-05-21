package com.arcplus.fm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.entity.vo.CoVehicleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoVehicleService;
import com.arcplus.fm.entity.CoVehicle;
import com.arcplus.fm.mapper.CoVehicleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 *
 * @author jv_team
 * @email 
 * @date 2020-05-08 10:15:32
 */
@Service
public class CoVehicleServiceImpl implements CoVehicleService  {
    @Autowired
    CoVehicleMapper instCoVehicleMapper;

    public CoVehicle save(CoVehicle entity) {

        entity.setDealStatus(EDealStatus.W);
        entity.setDataStatus(EDataStatus.N);
        entity.setCreateAt(new Date());
        instCoVehicleMapper.save(entity);
        return entity;
    }

    @Override
    public int update(CoVehicle entity) {
        return instCoVehicleMapper.update(entity);
    }

    @Override
    public int delete(int id,int delete_uid) {
        return instCoVehicleMapper.delete(id,delete_uid);
    }

    @Override
    public PageInfo<CoVehicle> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<CoVehicle> pageResult = instCoVehicleMapper.selectAll();
        PageInfo<CoVehicle> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public CoVehicle getById(int id) {
        return instCoVehicleMapper.getById(id);
    }

    @Override
    public PageInfo<CoVehicleVo> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<CoVehicleVo> pageResult = instCoVehicleMapper.search(param);

        PageInfo<CoVehicleVo> result = new PageInfo(pageResult);
        return result;
    }

    @Override
    public PageInfo<CoVehicleVo> selectCoVehicleVo(int page, int size, int buildingId, int floorId) {
        PageHelper.startPage(page, size);
        List<CoVehicleVo> pageResult = instCoVehicleMapper.selectCoVehicleVo(buildingId,floorId);

        PageInfo<CoVehicleVo> result = new PageInfo(pageResult);
        return result;
    }
}