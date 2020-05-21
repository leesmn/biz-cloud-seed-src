package com.arcplus.fm.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arcplus.fm.entity.vo.ContractVo;
import com.arcplus.fm.entity.CoBill;
import com.arcplus.fm.mapper.CoBillMapper;
import com.arcplus.fm.util.NumberProduceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arcplus.fm.service.CoContractService;
import com.arcplus.fm.entity.CoContract;
import com.arcplus.fm.mapper.CoContractMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 共同-合同
 *
 * @author jv_team
 * @email
 * @date 2020-05-07 15:02:03
 */
@Service
public class CoContractServiceImpl implements CoContractService {
    @Autowired
    CoContractMapper instCoContractMapper;

    @Autowired
    CoBillMapper coBillMapper;

    @Override
    @Transactional
    public CoContract save(CoContract entity) {
        entity.setContractNo(NumberProduceUtil.getNum("", 4));
        int contractId = instCoContractMapper.save(entity);

        SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date x = entity.getFromDate();

        int period = 0;
        if ("M".equals(entity.getSettlementMethod())) {
            period = 1;
        }
        if ("Q".equals(entity.getSettlementMethod())) {
            period = 3;
        }

        while (x.before(entity.getToDate())) {

            if (period == 0)
                break;

            CoBill bill = new CoBill();
            bill.setContractId(contractId);
            bill.setBillCode(NumberProduceUtil.getNum("", 4));
            bill.setBillPeriod(yyyyMM.format(x));
            bill.setPaymentObject(entity.getContractTarget());
            bill.setRealIncomeCost(entity.getContractCost());
            bill.setExtraCost(new BigDecimal(0));
            bill.setInvoiceStatus("N");
            bill.setCreateBy(entity.getCreateBy());
            coBillMapper.save(bill);

            calendar.setTime(x);
            calendar.add(Calendar.MONTH, period);
            x = calendar.getTime();

        }

        return entity;
    }

    @Override
    public int update(CoContract entity) {
        return instCoContractMapper.update(entity);
    }

    @Override
    public int delete(int id) {
        return instCoContractMapper.delete(id);
    }

    @Override
    public PageInfo<CoContract> selectAllPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<CoContract> pageResult = instCoContractMapper.selectAll();
        PageInfo<CoContract> reslut = new PageInfo(pageResult);
        return reslut;
    }

    @Override
    public PageInfo<ContractVo> search(Map<String, Object> param, int page, int size) {

        instCoContractMapper.updateContractEffective();

        instCoContractMapper.updateContractExpire();

        PageHelper.startPage(page, size);
        List<ContractVo> pageResult = instCoContractMapper.search(param);
        pageResult.forEach(x -> {
            List<ContractVo> children = instCoContractMapper.searchAdditionalContractList(x.getContractId());
            x.additionalContractCount = children.size();
            x.child = children;
        });
        PageInfo<ContractVo> reslut = new PageInfo(pageResult);
        return reslut;
    }
}