package com.arcplus.fm.service;

import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.vo.RepairVo;
import com.arcplus.fm.entity.vo.RepairWoVo;
import com.arcplus.fm.entity.BoRepair;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 业务-保修
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public interface BoRepairService  {
    BoRepair save(BoRepair entity);

    int update(BoRepair entity);

    int updateByCode(BoRepair entity);

    int delete(int id);

    PageInfo<BoRepair> selectAllPage(int page,int size);

    BoRepair getById(int id);

    PageInfo<RepairVo> search(Map<String, Object> prama, int page, int size);

    RepairVo getRepairByCode(String repairCode);

    PageInfo<RepairWoVo> selectRepairWizWo(int page,int size,int buildId,int floorId);

    List<FloorCountVo> countByFloor(int buildId);
}