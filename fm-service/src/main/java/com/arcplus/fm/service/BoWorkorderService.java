package com.arcplus.fm.service;

import com.arcplus.fm.entity.dto.WorkorderDetailDto;
import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.vo.WorkorderVo;
import com.arcplus.fm.entity.BoWorkorder;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * 业务-设备资产
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public interface BoWorkorderService  {
    BoWorkorder save(BoWorkorder entity,String prefix);

    int update(BoWorkorder entity);

    int delete(int id);

    BoWorkorder getById(int id);

    WorkorderDetailDto getDetailById(int id);

    PageInfo<BoWorkorder> selectAllPage(int page,int size);

    int processWorkOrder(Object entity,int step,int oprId,String oprName);

    PageInfo<WorkorderVo> selectWorkorderComplex(int page,int size,int adminRole,int uid,int dealState);

    PageInfo<BoWorkorder> selectByFloor(int page,int size,int buildId,int floorId);

    List<FloorCountVo> countByFloor(int buildId);

    PageInfo<BoWorkorder> search(Map<String, Object> prama, int page, int size);
}