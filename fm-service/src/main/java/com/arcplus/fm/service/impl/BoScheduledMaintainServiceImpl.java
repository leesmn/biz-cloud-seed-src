package com.arcplus.fm.service.impl;

import com.arcplus.fm.common.GlobalError;
import com.arcplus.fm.common.ServiceException;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EScheduledRule;
import com.arcplus.fm.entity.vo.BoScheduledMaintainMonthVo;
import com.arcplus.fm.entity.vo.ScheduledMaintainStaticVo;
import com.arcplus.fm.entity.vo.ScheduledMaintainVo;
import com.arcplus.fm.entity.BoScheduledMaintain;
import com.arcplus.fm.feign.UserClient;
import com.arcplus.fm.mapper.BoScheduledMaintainMapper;
import com.arcplus.fm.service.BoScheduledMaintainService;
import com.arcplus.fm.util.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 业务-定期维护
 *
 * @author jv_team
 * @email
 * @date 2020-04-21 10:11:05
 */
@Service
public class BoScheduledMaintainServiceImpl implements BoScheduledMaintainService {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    BoScheduledMaintainMapper instBoScheduledMaintainMapper;

    @Autowired
    UserClient userClient;

    @Transactional
    @Override
    public int save(BoScheduledMaintain entity) {

        entity.setGroupUuid(UUIDUtils.getUUID32());
        if (entity.getScheduledRule() == EScheduledRule.O) {
            if (entity.getScheduledInterval() < 1) {
                throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "间隔时间不能小于1");
            }
        }

        return scheduledFun(entity);
    }

    @Override
    public int update(BoScheduledMaintain entity) {
        return instBoScheduledMaintainMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instBoScheduledMaintainMapper.delete(id);
    }

    @Override
    public PageInfo<BoScheduledMaintain> selectAllPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<BoScheduledMaintain> pageResult = instBoScheduledMaintainMapper.selectAll();
        PageInfo<BoScheduledMaintain> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public BoScheduledMaintain getDetail(Integer scheduledId) {
        return instBoScheduledMaintainMapper.getById(scheduledId);
    }

    @Override
    public BoScheduledMaintain getByGroupUuid(String groupUuid) {
        return instBoScheduledMaintainMapper.getByGroupUuid(groupUuid);
    }

    @Override
    public int updateByGroupUuid(BoScheduledMaintain entity) {
        if (entity.getGroupUuid().isEmpty()) {
            throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "groupUuid不能为空");
        }
        instBoScheduledMaintainMapper.deleteByGroupUuid(entity.getGroupUuid());
        return scheduledFun(entity);
    }

    @Override
    public int deleteByGroupUuid(String groupUuid) {
        return instBoScheduledMaintainMapper.deleteByGroupUuid(groupUuid);
    }

    @Override
    public Map<String, List<BoScheduledMaintainMonthVo>> selectMonth(Integer year, Integer month) {
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fmtMonth = String.format("%02d", month);
        String monthFirstDay = year + "-" + fmtMonth + "-01";
        List<BoScheduledMaintain> retData = instBoScheduledMaintainMapper.selectMonth(monthFirstDay);
        Map<String, List<BoScheduledMaintainMonthVo>> result = new Hashtable<>();
        BoScheduledMaintainMonthVo vo = null;
        List<BoScheduledMaintainMonthVo> valArr = null;
        for (BoScheduledMaintain item : retData) {
            vo = new BoScheduledMaintainMonthVo();
            BeanUtils.copyProperties(item, vo);
            String keyDay = sdf.format(item.getMaintainStartAt());
            if (!result.containsKey(keyDay)) {
                valArr = new ArrayList<>();
                valArr.add(vo);
                result.put(keyDay, valArr);
            } else {
                result.get(keyDay).add(vo);
            }
        }
        return result;
    }

    private int scheduledFun(BoScheduledMaintain entity) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(entity.getMaintainStartAt());
        instBoScheduledMaintainMapper.save(entity);


        long iEndStart = (entity.getScheduledEndAt().getTime() - entity.getMaintainStartAt().getTime()) / (1000 * 3600 * 24);

        int resultCount = 1;
        if (entity.getScheduledRule() == EScheduledRule.D) {
            if (iEndStart > 60 * entity.getScheduledInterval()) {
                throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "按日重复时，不建议超过60次的重复，请缩短时间跨度！");
            }

            cale.add(Calendar.DAY_OF_YEAR, entity.getScheduledInterval());
            Date loopMaintainStartAt = cale.getTime();
            while (loopMaintainStartAt.getTime() < entity.getScheduledEndAt().getTime()) {
                entity.setScheduledId(null);
                entity.setMaintainStartAt(loopMaintainStartAt);
                instBoScheduledMaintainMapper.save(entity);
                cale.add(Calendar.DAY_OF_YEAR, entity.getScheduledInterval());
                loopMaintainStartAt = cale.getTime();
                resultCount++;
            }
        } else if (entity.getScheduledRule() == EScheduledRule.W) {
            cale.add(Calendar.WEEK_OF_YEAR, entity.getScheduledInterval());
            Date loopMaintainStartAt = cale.getTime();
            while (loopMaintainStartAt.getTime() < entity.getScheduledEndAt().getTime()) {
                entity.setScheduledId(null);
                entity.setMaintainStartAt(loopMaintainStartAt);
                instBoScheduledMaintainMapper.save(entity);
                cale.add(Calendar.WEEK_OF_YEAR, entity.getScheduledInterval());
                loopMaintainStartAt = cale.getTime();
                resultCount++;
            }
        } else if (entity.getScheduledRule() == EScheduledRule.M) {
            cale.add(Calendar.MONTH, entity.getScheduledInterval());
            Date loopMaintainStartAt = cale.getTime();
            while (loopMaintainStartAt.getTime() < entity.getScheduledEndAt().getTime()) {
                entity.setScheduledId(null);
                entity.setMaintainStartAt(loopMaintainStartAt);
                instBoScheduledMaintainMapper.save(entity);
                cale.add(Calendar.MONTH, entity.getScheduledInterval());
                loopMaintainStartAt = cale.getTime();
                resultCount++;
            }
        } else if (entity.getScheduledRule() == EScheduledRule.Y) {
            cale.add(Calendar.YEAR, entity.getScheduledInterval());
            Date loopMaintainStartAt = cale.getTime();
            while (loopMaintainStartAt.getTime() < entity.getScheduledEndAt().getTime()) {
                entity.setScheduledId(null);
                entity.setMaintainStartAt(loopMaintainStartAt);
                instBoScheduledMaintainMapper.save(entity);
                cale.add(Calendar.YEAR, entity.getScheduledInterval());
                loopMaintainStartAt = cale.getTime();
                resultCount++;
            }
        }

        return resultCount;
    }

    @Override
    public PageInfo<BoScheduledMaintain> selectByAssignment(int page, int size, int uid) {
        PageHelper.startPage(page, size);
        List<BoScheduledMaintain> pageResult = instBoScheduledMaintainMapper.selectByAssignment(uid);
        PageInfo<BoScheduledMaintain> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public int workerDeal(int id) {
        BoScheduledMaintain entity = instBoScheduledMaintainMapper.getById(id);
        entity.setDealStatus(EDealStatus.F);
        return instBoScheduledMaintainMapper.update(entity);
    }

    @Override
    public PageInfo<ScheduledMaintainVo> selectScheduledMaintainComplex(int page, int size, int uid, int dealState) {
        Map<String, Object> paramMap = new HashMap<>();

        if (dealState != -1 && dealState != 0 && dealState != 5 && dealState != 10 && dealState != 20 && dealState != 30) {
            throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "状态超出传值范围");
        }
        paramMap.put("uid", uid);
        paramMap.put("dealState", dealState);
        PageHelper.startPage(page, size);
        List<BoScheduledMaintain> pageResult = instBoScheduledMaintainMapper.selectScheduledMaintainComplex(paramMap);
        List<ScheduledMaintainVo> vos = new ArrayList<>();
        ScheduledMaintainVo item = null;
        for (BoScheduledMaintain wo : pageResult) {
            item = new ScheduledMaintainVo();
            BeanUtils.copyProperties(wo, item);
            if (wo.getDealStatus().equals(EDealStatus.W)) { //deal_status = 'CR' or deal_status = 'MD'
                item.setStatusDescr("待处理");
            } else {
                item.setStatusDescr("已处理");
            }
            vos.add(item);
        }
        PageInfo<ScheduledMaintainVo> reslut = new PageInfo(vos);
        return reslut;
    }

    @Override
    public PageInfo<ScheduledMaintainStaticVo> search(Map<String, Object> prama, int page, int size) {
        PageHelper.startPage(page, size);
        List<ScheduledMaintainStaticVo> pageResult = instBoScheduledMaintainMapper.search(prama);

        for (ScheduledMaintainStaticVo sm : pageResult) {
            Map userMap = userClient.getUserById(Optional.ofNullable(sm.getAssignment()).orElse(0));
            if (null != userMap) {
                sm.assignmentName = userMap.get("username").toString();
            }
        }

        PageInfo<ScheduledMaintainStaticVo> reslut = new PageInfo(pageResult);
        return reslut;
    }
}