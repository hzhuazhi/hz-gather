package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.UCashOutLogMapper;
import com.hz.gather.master.core.mapper.UCashOutProcedLogMapper;
import com.hz.gather.master.core.mapper.VcMemberMapper;
import com.hz.gather.master.core.mapper.VcMemberResourceMapper;
import com.hz.gather.master.core.model.entity.UCashOutLog;
import com.hz.gather.master.core.model.entity.UCashOutProcedLog;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/3 10:00
 * @Version 1.0
 */
@Service
@Transactional
public class TransactionalServiceImpl<T> extends BaseServiceImpl<T> implements TransactionalService<T> {
    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;
    @Autowired
    private UCashOutProcedLogMapper uCashOutProcedLogMapper;
    @Autowired
    private UCashOutLogMapper uCashOutLogMapper;

    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public void userRegister(VcMember vcMember, VcMemberResource vcMemberResource, VcMemberResource  uqVcMemberResource) {
        vcMemberMapper.insertSelective(vcMember);
        vcMemberResourceMapper.insertSelective(vcMemberResource);
        vcMemberResourceMapper.updateUpPeople(uqVcMemberResource);
    }

    @Override
    public void addCashOut(UCashOutLog uCashOutLog, UCashOutProcedLog uCashOutProcedLog, VcMemberResource uqVcMemberResource) {
        if(uCashOutProcedLog!=null){
            uCashOutProcedLogMapper.insertSelective(uCashOutProcedLog);
        }
        uCashOutLogMapper.insertSelective(uCashOutLog);
        vcMemberResourceMapper.updateByPrimaryKeySelective(uqVcMemberResource);
    }
}
