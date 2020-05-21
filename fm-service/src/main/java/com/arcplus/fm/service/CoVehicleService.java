package com.arcplus.fm.service;

import com.arcplus.fm.entity.vo.CoVehicleVo;
import com.arcplus.fm.entity.CoVehicle;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * 
 *
 * @author jv_team
 * @email 
 * @date 2020-05-08 10:15:32
 */
public interface CoVehicleService  {
    CoVehicle save(CoVehicle entity);

    int update(CoVehicle entity);

    int delete(int id,int delete_uid);

    PageInfo<CoVehicle> selectAllPage(int page,int size);


    CoVehicle getById(int id);

    PageInfo<CoVehicleVo> search(Map<String, Object> prama, int page, int size);

    PageInfo<CoVehicleVo> selectCoVehicleVo(int page, int size, int buildingId, int floorId);
}