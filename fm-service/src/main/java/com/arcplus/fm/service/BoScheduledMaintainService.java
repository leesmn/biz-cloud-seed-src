package com.arcplus.fm.service;

import com.arcplus.fm.entity.vo.BoScheduledMaintainMonthVo;
import com.arcplus.fm.entity.vo.ScheduledMaintainStaticVo;
import com.arcplus.fm.entity.vo.ScheduledMaintainVo;
import com.arcplus.fm.entity.BoScheduledMaintain;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * 业务-定期维护
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
public interface BoScheduledMaintainService  {
    int save(BoScheduledMaintain entity);

    int update(BoScheduledMaintain entity);

    int delete(int id);

    PageInfo<BoScheduledMaintain> selectAllPage(int page,int size);

    Map<String,List<BoScheduledMaintainMonthVo>> selectMonth(Integer year,Integer month);

    BoScheduledMaintain getDetail(Integer scheduledId);

    BoScheduledMaintain getByGroupUuid(String groupUuid);
    int updateByGroupUuid(BoScheduledMaintain entity);
    int deleteByGroupUuid(String groupUuid);

    PageInfo<BoScheduledMaintain> selectByAssignment(int page,int size,int uid);

    int workerDeal(int id);

    PageInfo<ScheduledMaintainVo> selectScheduledMaintainComplex(int page,int size,int uid,int dealState);

    PageInfo<ScheduledMaintainStaticVo> search(Map<String, Object> prama, int page, int size);
}