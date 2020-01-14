package com.hz.gather.master.core.service.impl;

import com.hz.gather.master.core.common.dao.BaseDao;
import com.hz.gather.master.core.common.enums.ENUM_ERROR;
import com.hz.gather.master.core.common.exception.ServiceException;
import com.hz.gather.master.core.common.service.impl.BaseServiceImpl;
import com.hz.gather.master.core.mapper.*;
import com.hz.gather.master.core.model.entity.*;
import com.hz.gather.master.core.model.user.FundListModel;
import com.hz.gather.master.core.protocol.request.user.RequestEditUser;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;
import com.hz.gather.master.core.protocol.response.user.ResponseFundList;
import com.hz.gather.master.core.protocol.response.user.ResponseMyFriend;
import com.hz.gather.master.core.protocol.response.user.ResponseUserInfo;
import com.hz.gather.master.core.service.UserInfoService;
import com.hz.gather.master.util.ComponentUtil;
import com.hz.gather.master.util.PublicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private VcMemberPayMapper vcMemberPayMapper;


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

        responseUserInfo=PublicMethod.toResponseUserInfo(vcMember,vcMemberResource,timeLog,list);


        return responseUserInfo;
    }


    @Override
    public ResponseMyFriend queryMyFriend(Integer memberId) throws Exception {
        VcMemberResource    vcMemberResource  =  PublicMethod.toVcMemberResource(memberId);
        VcMemberResource  rsVcMemberResource = vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);

        VcMember    vcMember  = PublicMethod.toVcMemberSuperiorId(memberId);
        List<VcMember>   list = vcMemberMapper.selectBySuperiorId(vcMember);

        if(rsVcMemberResource ==null ){
            throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
        }
        ResponseMyFriend reponseMyFriend=PublicMethod.toVcMemberSuperiorId(rsVcMemberResource,list);
        return reponseMyFriend;
    }

    @Override
    public VcMember getMemeberInfo(Integer memberId) throws Exception {
        VcMember vcMember  = PublicMethod.toVcMember(memberId);
        VcMember vcMember1  = vcMemberMapper.selectByPrimaryKey(vcMember);
        return vcMember1;
    }

    @Override
    public Integer editMemeberInfo(Integer memberId, RequestEditUser editUser) throws Exception {
        VcMember vcMember  = PublicMethod.toVcMember(memberId,editUser);
        Integer   count  = vcMemberMapper.updateByPrimaryKeySelective(vcMember);
        return count;
    }

    @Override
    public ResponseFundList getUMoneList(Integer memberId,List<UMoneyList> list) throws Exception {
        ResponseFundList  responseFundList = new  ResponseFundList();
        VcMemberResource    vcMemberResource  =  PublicMethod.toVcMemberResource(memberId);
        VcMemberResource   rsVcMemberResource =  vcMemberResourceMapper.selectByPrimaryKey(vcMemberResource);
        if (rsVcMemberResource==null){
            throw  new ServiceException(ENUM_ERROR.A00016.geteCode(),ENUM_ERROR.A00016.geteDesc());
        }
        responseFundList.setCash_money(rsVcMemberResource.getCashMoney()+"");
        responseFundList.setSurplus_money(rsVcMemberResource.getSurplusMoney()+"");
        responseFundList.setTotal_money(rsVcMemberResource.getTotalMoney()+"");

        List<Object>   list1   = new ArrayList<>();
        for (UMoneyList  uMoneyList:list){
            FundListModel  fundListModel = new FundListModel();
            fundListModel.setReward_type(uMoneyList.getRewardType()+"");
            fundListModel.setSymbol_type(uMoneyList.getSymbolType()+"");
            fundListModel.setMoney(uMoneyList.getMoney()+"");
            fundListModel.setCreate_time(uMoneyList.getCreateTime()+"");
            list1.add(fundListModel);
        }
        responseFundList.setList(list1);
        return responseFundList;
    }


}
