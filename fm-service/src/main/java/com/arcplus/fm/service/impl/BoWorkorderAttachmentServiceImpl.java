package com.arcplus.fm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.BoWorkorderAttachmentService;
import com.arcplus.fm.entity.BoWorkorderAttachment;
import com.arcplus.fm.mapper.BoWorkorderAttachmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 工单附件表
 *
 * @author jv_team
 * @email 
 * @date 2020-05-09 17:19:20
 */
@Service
public class BoWorkorderAttachmentServiceImpl implements BoWorkorderAttachmentService  {
    @Autowired
    BoWorkorderAttachmentMapper instBoWorkorderAttachmentMapper;

    @Override
    public BoWorkorderAttachment save(BoWorkorderAttachment entity) {
        instBoWorkorderAttachmentMapper.save(entity);
        return entity;
    }

    @Override
    public int update(BoWorkorderAttachment entity) {
        return instBoWorkorderAttachmentMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instBoWorkorderAttachmentMapper.delete(id);
    }

    @Override
    public PageInfo<BoWorkorderAttachment> selectAllPage(int page,int size) {
        PageHelper.startPage(page,size);
        List<BoWorkorderAttachment> pageResult = instBoWorkorderAttachmentMapper.selectAll();
        PageInfo<BoWorkorderAttachment> reslut = new PageInfo(pageResult);
        return reslut;
    }
}