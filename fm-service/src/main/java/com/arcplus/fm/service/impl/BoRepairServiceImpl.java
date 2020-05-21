package com.arcplus.fm.service.impl;

import com.arcplus.fm.common.GlobalError;
import com.arcplus.fm.common.ServiceException;
import com.arcplus.fm.constant.EDataStatus;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EDealWith;
import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.vo.RepairWoVo;
import com.arcplus.fm.util.NumberProduceUtil;

import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.RepairVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.BoRepairService;
import com.arcplus.fm.entity.BoRepair;
import com.arcplus.fm.mapper.BoRepairMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 业务-保修
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Service
public class BoRepairServiceImpl implements BoRepairService  {
    @Autowired
    BoRepairMapper instBoRepairMapper;

    @Override
    public BoRepair save(BoRepair entity) {
        entity.setRepairCode(NumberProduceUtil.getNum("CP",4));

        entity.setDealWith(EDealWith.O);
        entity.setDealStatus(EDealStatus.W);
        entity.setDataStatus(EDataStatus.N);
        //entity.setCreateAt(new Date());
        instBoRepairMapper.save(entity);
        return entity;
    }

    @Override
    public int update(BoRepair entity) {
        return instBoRepairMapper.update(entity);
    }

    @Override
    public int updateByCode(BoRepair entity) {
        if(entity.getRepairCode()==null){
            throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(),"报修单号不能为空");
        }

        //改变报修记录处理状态和处理方式
        entity.setDealStatus(EDealStatus.F);
        entity.setDealWith(EDealWith.D);
        return instBoRepairMapper.updateByCode(entity);
    }

    @Override
    public int delete(int id) {
        return instBoRepairMapper.delete(id);
    }

    @Override
    public PageInfo<BoRepair> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<BoRepair> pageResult = instBoRepairMapper.selectAll();
        PageInfo<BoRepair> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public BoRepair getById(int id) {
        return instBoRepairMapper.getById(id);
    }

    @Override
    public PageInfo<RepairVo> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<RepairVo> pageResult = instBoRepairMapper.search(param);

        PageInfo<RepairVo> result = new PageInfo(pageResult);
        return result;
    }

    @Override
    public RepairVo getRepairByCode(String repairCode) {
        return instBoRepairMapper.getRepairByCode(repairCode);
    }

    @Override
    public PageInfo<RepairWoVo> selectRepairWizWo(int page, int size,int buildId,int floorId) {
        PageHelper.startPage(page, size);
        List<RepairWoVo> pageResult = instBoRepairMapper.selectRepairWizWo(buildId,floorId);

        PageInfo<RepairWoVo> result = new PageInfo(pageResult);
        return result;
    }

    @Override
    public  List<FloorCountVo> countByFloor(int buildId) {
        return instBoRepairMapper.countByFloor(buildId);
    }
}