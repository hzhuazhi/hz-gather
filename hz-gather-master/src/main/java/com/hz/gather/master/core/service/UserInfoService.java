package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.UMoneyList;
import com.hz.gather.master.core.model.entity.UMoneyLog;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberResource;
import com.hz.gather.master.core.protocol.request.user.RequestEditUser;
import com.hz.gather.master.core.protocol.response.user.*;

import java.util.List;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/7 17:29
 * @Version 1.0
 */
public interface UserInfoService<T> extends BaseService<T> {
    /***
     * 根据memberId 查询  memberInfo
     * @param memberId
     * @return
     */
    public VcMember queryMemberInfo(Integer  memberId);

    /***
     * 根据memberId 查询  ResourceInfo
     * @param memberId
     * @return
     */
    public VcMemberResource queryMemberResourceInfo(Integer  memberId);


    /***
     * 根据用户id 查询用户信息
     * @param memberId
     * @return
     */
    public ResponseUserInfo toResponseUserInfo(Integer  memberId)throws Exception;

    /***
     * 查询用户朋友信息
     * @param memberId
     * @return
     */
    public ResponseMyFriend queryMyFriend(Integer  memberId)throws Exception;

    /***
     * 查询用户自己详细信息
     * @param memberId
     * @return
     */
    public VcMember getMemeberInfo(Integer  memberId)throws Exception;

    /***
     * 用户编辑信息
     * @param memberId
     * @return
     */
    public Integer editMemeberInfo(Integer  memberId, RequestEditUser editUser)throws Exception;


    /***
     * 查询用户资金信息
     * @param memberId
     * @return
     */
    public ResponseFundList getUMoneList(Integer  memberId,List<UMoneyList> list)throws Exception;

    /**
     * @Description: TODO
     * @return
     * @author long
     * @date 2020/1/14 17:30
    */
    public List<UMoneyLogResp> getUMoneyList(UMoneyList model);


    /**
     * @Description: 修改用户是否开启问答修改密码  0、未开启 1、开启
     * @param model - memberId、is_questions
     * @return int
     * @author yoko
     * @date 2020/1/16 14:07
    */
    public int updateMemberIsQuestions(VcMember model);

    public Integer updatePayPassword(Integer memberId,String payPassword);



}
