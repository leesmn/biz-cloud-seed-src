package com.gov.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gov.security.entity.AuditLog;
import com.gov.security.service.IAuditLogService;
import com.gov.security.mapper.AuditLogMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 李浩洋
 * @since 2019-10-27
 */
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements
    IAuditLogService {

}
