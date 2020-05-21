package com.arcplus.fm.service.impl;

import com.arcplus.fm.common.GlobalError;
import com.arcplus.fm.common.ServiceException;
import com.arcplus.fm.constant.EDealStatus;
import com.arcplus.fm.constant.EDealWith;
import com.arcplus.fm.constant.EFromBiz;
import com.arcplus.fm.constant.EWoStatus;
import com.arcplus.fm.entity.dto.BoWorkorderCodeDto;
import com.arcplus.fm.entity.dto.WorkorderDealAfterDto;
import com.arcplus.fm.entity.dto.WorkorderDealDto;
import com.arcplus.fm.entity.dto.WorkorderDetailDto;
import com.arcplus.fm.entity.dto.WorkorderLiteDto;
import com.arcplus.fm.entity.vo.FloorCountVo;
import com.arcplus.fm.entity.vo.WorkorderVo;
import com.arcplus.fm.entity.BoRepair;
import com.arcplus.fm.entity.BoWorkorderAttachment;
import com.arcplus.fm.entity.BoWorkorderProcess;
import com.arcplus.fm.feign.UserClient;
import com.arcplus.fm.mapper.BoRepairMapper;
import com.arcplus.fm.mapper.BoWorkorderAttachmentMapper;
import com.arcplus.fm.mapper.BoWorkorderProcessMapper;
import com.arcplus.fm.util.NumberProduceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.BoWorkorderService;
import com.arcplus.fm.entity.BoWorkorder;
import com.arcplus.fm.mapper.BoWorkorderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务-设备资产
 *
 * @author jv_team
 * @email 
 * @date 2020-04-21 10:11:05
 */
@Service
public class BoWorkorderServiceImpl implements BoWorkorderService  {
    @Autowired
    BoWorkorderMapper instBoWorkorderMapper;

    @Autowired
    BoWorkorderAttachmentMapper boWorkorderAttachmentMapper;

    @Autowired
    BoRepairMapper boRepairMapper;

    @Autowired
    BoWorkorderProcessMapper boWorkorderProcessMapper;

    @Autowired
    UserClient userClient;

    @Override
    public BoWorkorder save(BoWorkorder entity,String prefix) {
        entity.setWoCode(NumberProduceUtil.getNum(prefix,4));
        instBoWorkorderMapper.save(entity);
        return entity;
    }

    @Override
    public int update(BoWorkorder entity) {
        return instBoWorkorderMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instBoWorkorderMapper.delete(id);
    }


    @Override
    public BoWorkorder getById(int id) {
        return instBoWorkorderMapper.getById(id);
    }

    @Override
    public WorkorderDetailDto getDetailById(int id) {
        WorkorderDetailDto workorderDetailDto = new WorkorderDetailDto();
        BoWorkorder workorder = instBoWorkorderMapper.getById(id);
        BoRepair repair = boRepairMapper.getRepairByCode(workorder.getRepairCode());
        List<BoWorkorderAttachment> dealAttachment  = boWorkorderAttachmentMapper.selectByWoCode(workorder.getWoCode());
        List<BoWorkorderProcess> workorderProcess =  boWorkorderProcessMapper.selectByWoCode(workorder.getWoCode());

        workorderDetailDto.setRepair(repair);
        WorkorderVo workorderVo = new WorkorderVo();
        BeanUtils.copyProperties(workorder,workorderVo);
        if(workorderVo.getDealStatus().equals(EWoStatus.CR)){ //deal_status = 'CR' or deal_status = 'MD'
            workorderVo.setStatusDescr("待接单");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.AM) || workorderVo.getDealStatus().equals(EWoStatus.MR) || workorderVo.getDealStatus().equals(EWoStatus.MA) || workorderVo.getDealStatus().equals(EWoStatus.MD)){
            workorderVo.setStatusDescr("已分配");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.MB)){
            workorderVo.setStatusDescr("维修中");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.AR)){
            workorderVo.setStatusDescr("已驳回");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.MO)){
            workorderVo.setStatusDescr("已完成");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.AC)) {        //deal_status = 'AC' or deal_status = 'CL' or deal_status = 'AD'
            workorderVo.setStatusDescr("已审核");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.AD)) {//班组拒绝
            workorderVo.setStatusDescr("已拒绝");
        }else if(workorderVo.getDealStatus().equals(EWoStatus.CL)) {
            workorderVo.setStatusDescr("已结束");
        }else{
            workorderVo.setStatusDescr("未知状态");
        }
        workorderDetailDto.setWorkorder(workorderVo);
        workorderDetailDto.setWorkorderProcesses(workorderProcess);
        for(BoWorkorderAttachment attachment:dealAttachment){
            if(attachment.getAttachmentType().equals(0)){
                if(workorderDetailDto.getDealBeforAttachment()==null){
                    workorderDetailDto.setDealBeforAttachment(new ArrayList<>());
                }
                workorderDetailDto.getDealBeforAttachment().add(attachment);
            }else{
                if(workorderDetailDto.getDealAfterAttachment()==null){
                    workorderDetailDto.setDealAfterAttachment(new ArrayList<>());
                }
                workorderDetailDto.getDealAfterAttachment().add(attachment);
            }
        }
        return workorderDetailDto;
    }

    @Override
    public PageInfo<BoWorkorder> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<BoWorkorder> pageResult = instBoWorkorderMapper.selectAll();
        PageInfo<BoWorkorder> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Transactional
    @Override
    public int processWorkOrder(Object entity,int step,int oprId,String oprName) {
        BoRepair boRepair = null;
        if(step==1){//由报修生成工单，并分派班组长
            WorkorderLiteDto workorderLiteDto = (WorkorderLiteDto)entity;
            boRepair = boRepairMapper.getRepairByCode(workorderLiteDto.getRepairCode());
            if(workorderLiteDto.getRepairCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            if(null == workorderLiteDto.getAssignmentGroupAdmin() || workorderLiteDto.getAssignmentGroupAdmin() < 0){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"班组长不能为空");
            }
            if(null == workorderLiteDto.getFailureTypeId() || workorderLiteDto.getFailureTypeId() < 0){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"事故类型不能为空");
            }
            if(null == workorderLiteDto.getPriorityLevel() || workorderLiteDto.getPriorityLevel() < 0){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"优先级不能为空");
            }
            if(null == boRepair){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }

            BoWorkorder newBoWorkOrder = new BoWorkorder();
            newBoWorkOrder.setFromBiz(EFromBiz.R);
            newBoWorkOrder.setRepairCode(workorderLiteDto.getRepairCode());
            newBoWorkOrder.setAssignmentGroupAdmin(workorderLiteDto.getAssignmentGroupAdmin());
            if(null == workorderLiteDto.getAssignmentAdminName()){
                Map userMap =  userClient.getUserById(workorderLiteDto.getAssignmentGroupAdmin());
                if(null == userMap){
                    throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"班组长不存在");
                }
                String assignmentAdminName = userMap.get("username").toString();
                newBoWorkOrder.setAssignmentAdminName(assignmentAdminName);
            }
            newBoWorkOrder.setFailureTypeId(workorderLiteDto.getFailureTypeId());
            newBoWorkOrder.setPriorityLevel(workorderLiteDto.getPriorityLevel());
            newBoWorkOrder.setDealStatus(EWoStatus.CR);

            newBoWorkOrder.setWoCode(NumberProduceUtil.getNum("CM",4));
            instBoWorkorderMapper.save(newBoWorkOrder);

            //改变报修记录处理状态和处理方式
            boRepair.setDealStatus(EDealStatus.P);
            boRepair.setDealWith(EDealWith.W);
            boRepairMapper.update(boRepair);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(newBoWorkOrder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("创建工单");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==2){//指派维修工
            BoWorkorder workorder = (BoWorkorder)entity;
            if(workorder.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            if(workorder.getAssignment()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"维修工人不能为空");
            }

            workorder.setDealStatus(EWoStatus.AM);

            if(null == workorder.getAssignmentName()){
                Map userMap =  userClient.getUserById(workorder.getAssignment());
                if(null == userMap){
                    throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"执行人不存在");
                }
                String assignmentAdminName = userMap.get("username").toString();
                workorder.setAssignmentName(assignmentAdminName);
            }

            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("班组指派维修工");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==-2){//班组拒绝
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.AD);
            instBoWorkorderMapper.updateByCode(workorder);

            boRepair = boRepairMapper.getRepairByCode(workorder.getRepairCode());
            boRepair.setDealStatus(EDealStatus.W);
            boRepair.setDealWith(EDealWith.O);
            boRepairMapper.update(boRepair);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setProcessRemark(workorderCode.getRemark());
            boWorkorderProcess.setDealDescribe("班组拒绝");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==3){//维修已读
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.MR);
            workorder.setWorkerRead(1);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("维修工已读");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }
        else if(step==4){//维修工接单
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.MA);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("维修工接单");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==-4){//维修工拒绝
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.MD);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("维修工拒绝");
            boWorkorderProcess.setProcessRemark(workorderCode.getRemark());
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==5){//维修工处理前
            WorkorderDealDto workorderDealDto = (WorkorderDealDto)entity;
            if(workorderDealDto.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderDealDto.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.MB);
            instBoWorkorderMapper.updateByCode(workorder);

            for(BoWorkorderAttachment attachment : workorderDealDto.getAttachmentArray()){
                attachment.setWoCode(workorderDealDto.getWoCode());
                attachment.setAttachmentType(0);
                boWorkorderAttachmentMapper.save(attachment);
            }

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("维修工处理前");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==6){//维修工处理后
            WorkorderDealAfterDto workorderDealDto = (WorkorderDealAfterDto)entity;
            if(workorderDealDto.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderDealDto.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.MO);
            workorder.setMaterialCost(workorderDealDto.getMaterialCost());
            workorder.setLaborCost(workorderDealDto.getLaborCost());
            workorder.setTotalCost(workorderDealDto.getMaterialCost().add(workorderDealDto.getLaborCost()));
            workorder.setRemark(workorderDealDto.getRemark());
            workorder.setSignPicture(workorderDealDto.getSignPicture());
            instBoWorkorderMapper.updateByCode(workorder);

            for(BoWorkorderAttachment attachment : workorderDealDto.getAttachmentArray()){
                attachment.setWoCode(workorderDealDto.getWoCode());
                attachment.setAttachmentType(1);
                boWorkorderAttachmentMapper.save(attachment);
            }

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("维修工处理后");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==7){//班组审核通过
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.AC);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("班组审核通过");
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==-7){//班组驳回
            BoWorkorderCodeDto workorderCode = (BoWorkorderCodeDto)entity;
            if(workorderCode==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            BoWorkorder workorder = instBoWorkorderMapper.getWorkorderByCode(workorderCode.getWoCode());
            if(workorder==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不存在");
            }
            workorder.setDealStatus(EWoStatus.AR);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("班组审核通过");
            boWorkorderProcess.setProcessRemark(workorderCode.getRemark());
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }else if(step==8){//回访
            BoWorkorder workorder = (BoWorkorder)entity;
            if(workorder.getWoCode()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"报修单号不能为空");
            }
            if(workorder.getVisitResult()==null){
                throw new ServiceException(GlobalError.ERROR_DATA_NO_EXIST.getCode(),"回访结果不能为空");
            }
            workorder.setDealStatus(EWoStatus.CL);
            instBoWorkorderMapper.updateByCode(workorder);

            BoWorkorderProcess boWorkorderProcess = new BoWorkorderProcess();
            boWorkorderProcess.setWoCode(workorder.getWoCode());
            boWorkorderProcess.setCreateBy(oprId);
            boWorkorderProcess.setCreateName(oprName);
            boWorkorderProcess.setDealDescribe("回访客户");
            boWorkorderProcess.setProcessRemark(workorder.getOtherComment());
            boWorkorderProcessMapper.save(boWorkorderProcess);
        }
        return 1;
    }

    @Override
    public PageInfo<WorkorderVo> selectWorkorderComplex(int page, int size, int adminRole, int uid,int dealState) {
        Map<String,Object> paramMap = new HashMap<>();
        if(adminRole!=0 && adminRole!=1){
            throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "业务角色超出范围");
        }
        if(dealState!=-1 && dealState!=0 && dealState!=5 && dealState!=10 && dealState!=20 && dealState!=30){
            throw new ServiceException(GlobalError.ERROR_INVALID_REQUEST.getCode(), "状态超出传值范围");
        }
        paramMap.put("adminRole",adminRole);
        paramMap.put("uid",uid);
        paramMap.put("dealState",dealState);
        PageHelper.startPage(page,size);
        List<BoWorkorder> pageResult = instBoWorkorderMapper.selectWorkorderComplex(paramMap);
        List<WorkorderVo> vos = new ArrayList<>();
        WorkorderVo item = null;
        for(BoWorkorder wo:pageResult){
            item = new WorkorderVo();
            BeanUtils.copyProperties(wo,item);
            if(adminRole==1){ //deal_status = 'AM' or deal_status = 'MR' or deal_status = 'MB' or deal_status = 'AR'
                if(wo.getDealStatus().equals(EWoStatus.CR)){ //deal_status = 'CR' or deal_status = 'MD'
                    item.setStatusDescr("待接单");
                }else if(wo.getDealStatus().equals(EWoStatus.AM) || wo.getDealStatus().equals(EWoStatus.MR) || wo.getDealStatus().equals(EWoStatus.MA) || wo.getDealStatus().equals(EWoStatus.MD)){
                    item.setStatusDescr("已分配");
                }else if(wo.getDealStatus().equals(EWoStatus.MB)){
                    item.setStatusDescr("维修中");
                }else if(wo.getDealStatus().equals(EWoStatus.AR)){
                    item.setStatusDescr("已驳回");
                }else if(wo.getDealStatus().equals(EWoStatus.MO)){
                    item.setStatusDescr("已完成");
                }else if(wo.getDealStatus().equals(EWoStatus.AC)) {        //deal_status = 'AC' or deal_status = 'CL' or deal_status = 'AD'
                    item.setStatusDescr("已审核");
                }else if(wo.getDealStatus().equals(EWoStatus.AD)) {//班组拒绝
                    item.setStatusDescr("已拒绝");
                }else if(wo.getDealStatus().equals(EWoStatus.CL)) {
                    item.setStatusDescr("已结束");
                }else{
                    item.setStatusDescr("未知状态");
                }
            }else{//deal_status = 'AM' or deal_status = 'MD'
                if(wo.getDealStatus().equals(EWoStatus.AM)){
                    item.setStatusDescr("未读");
                }else if(wo.getDealStatus().equals(EWoStatus.MR)){ //deal_status = 'CR' or deal_status = 'MD'
                    item.setStatusDescr("已读");
                }else if(wo.getDealStatus().equals(EWoStatus.MD)){ //deal_status = 'CR' or deal_status = 'MD'
                    item.setStatusDescr("已拒绝");
                }else if(wo.getDealStatus().equals(EWoStatus.MA)){
                    item.setStatusDescr("未开始");
                }else if(wo.getDealStatus().equals(EWoStatus.MB)){
                    item.setStatusDescr("维修中");
                }else if(wo.getDealStatus().equals(EWoStatus.AR)){
                    item.setStatusDescr("已驳回");
                }else if(wo.getDealStatus().equals(EWoStatus.MO)){
                    item.setStatusDescr("未审核");
                }else if(wo.getDealStatus().equals(EWoStatus.AC)) {        //deal_status = 'AC' or deal_status = 'CL' or deal_status = 'AD'
                    item.setStatusDescr("已审核");
                }else if(wo.getDealStatus().equals(EWoStatus.CL)) {
                    item.setStatusDescr("已结束");
                }else{
                    item.setStatusDescr("未知状态");
                }
            }
            vos.add(item);
        }
        PageInfo<WorkorderVo> reslut = new PageInfo(vos);
        return reslut;
    }

    @Override
    public PageInfo<BoWorkorder> selectByFloor(int page, int size, int buildId, int floorId) {
        PageHelper.startPage(page,size);
        List<BoWorkorder> pageResult = instBoWorkorderMapper.selectByFloor(buildId,floorId);
        PageInfo<BoWorkorder> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public List<FloorCountVo> countByFloor(int buildId) {
        return instBoWorkorderMapper.countByFloor(buildId);
    }


    @Override
    public PageInfo<BoWorkorder> search(Map<String, Object> param, int page, int size) {
        PageHelper.startPage(page, size);
        List<BoWorkorder> pageResult = instBoWorkorderMapper.search(param);

        PageInfo<BoWorkorder> result = new PageInfo(pageResult);
        return result;
    }
}