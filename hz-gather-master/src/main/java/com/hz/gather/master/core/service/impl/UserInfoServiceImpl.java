package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.*;
import com.hz.gather.master.core.model.entity.UBatchLog;
import com.hz.gather.master.core.model.entity.ULimitedTimeLog;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.protocol.response.user.ReponseMyFriend;
import com.hz.gather.master.core.protocol.response.user.ResponseUserInfo;
import com.hz.gather.master.core.service.LoginService;
import com.hz.gather.master.core.service.UserInfoService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 17:29
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl<T> extends BaseServiceImpl<T> implements UserInfoService<T> {

    @Autowired
    private VcMemberMapper vcMemberMapper;

    @Autowired
    private VcMemberResourceMapper vcMemberResourceMapper;

    @Autowired
    private ULimitedTimeLogMapper uLimitedTimeLogMapper;

    @Autowired
    private UBatchLogMapper uBatchLogMapper;


    @Override
    public BaseDao<T> getDao() {
        return null;
    }

    @Override
    public VcMember queryMemberInfo(Integer memberId) {
        VcMember  vcMember =PublicMethod.toVcMember(memberId);
        VcMember  rsVcMember=vcMemberMapper.selectByPrimaryKey(vcMember);
        return rsVcMember;
    }

    @Override
    public VcMemberResource queryMemberResourceInfo(Integer memberId) {
        VcMemberResource vcMemberResource   =  PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  vcMemberResource1  =  vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        return vcMemberResource1;
    }

    @Override
    public ResponseUserInfo toResponseUserInfo(Integer memberId)throws Exception {
        ResponseUserInfo  responseUserInfo  = new ResponseUserInfo();
        VcMember  vcMember = ComponentUtil.userInfoService.queryMemberInfo(memberId);
        VcMemberResource vcMemberResource =ComponentUtil.userInfoService.queryMemberResourceInfo(memberId);
        if(vcMember==null||vcMemberResource==null){
            throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
        }
        List<UBatchLog>     list = null;
        ULimitedTimeLog  timeLog = null;
        //状态显示vip的时候
        if(vcMember.getGradeType()==1){
            ULimitedTimeLog  limitedTimeLog = new ULimitedTimeLog();
            timeLog  = uLimitedTimeLogMapper.selectByMaxBatchNum(limitedTimeLog);
            if(timeLog==null){
                throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
            }
            UBatchLog    uBatchLog   =   PublicMethod.toUBatchLog(timeLog.getBatchNum());
            list =   uBatchLogMapper.selectByBatchNum(uBatchLog);

//            ULimitedTimeLog
        }

        PublicMethod.toResponseUserInfo(vcMember,vcMemberResource,timeLog,list);


        return responseUserInfo;
    }


    @Override
    public ReponseMyFriend queryMyFriend(Integer memberId) throws Exception {

        VcMemberResource    vcMemberResource  =  PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  rsVcMemberResource = vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);

        VcMember    vcMember  = PublicMethod.toVcMemberSuperiorId(memberId);
        List<VcMember>   list = vcMemberMapper.selectBySuperiorId(vcMember);


        return null;
    }
}
