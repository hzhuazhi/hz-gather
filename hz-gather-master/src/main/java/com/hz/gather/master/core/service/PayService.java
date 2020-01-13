package com.hz.gather.master.core.service;

import com.hz.gather.master.core.common.service.BaseService;
import com.hz.gather.master.core.model.entity.VcMember;
import com.hz.gather.master.core.model.entity.VcMemberPay;
import com.hz.gather.master.core.protocol.response.user.ResponeseHavaPay;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author long
 * @Date 2020/1/9 21:50
 * @Version 1.0
 */
public interface PayService<T> extends BaseService<T> {
    /***
     * 查询用户是否有绑定支付宝
     * @param memberId
     * @return
     */
    public ResponeseHavaPay getHavaPay(Integer  memberId)throws Exception;

    /***
     * 是否还能添加支付宝
     * @param memberId
     * @return
     */
    public boolean isAddPayZFB(Integer  memberId)throws Exception;


    /***
     * 添加支付宝用户信息
     * @param memberId
     * @return
     */
    public Integer addPayZFB(Integer  memberId,String  payId,String  payName)throws Exception;

    /***
     * 查询拥有支付明细
     * @param memberId
     * @return
     */
    public List<VcMemberPay> queryPayZFBList(Integer  memberId)throws Exception;

    /***
     * 查询用户和 支付宝id 绑定是否一致
     * @param memberId
     * @return
     */
    public List<VcMemberPay> checkMemberIdToAliPayId(Integer  memberId,String aliPayId)throws Exception;


    /***
     * 查询用户和 支付宝id 绑定是否一致
     * @param memberId
     * @return
     */
    public boolean checkMemberIdMoney(Integer  memberId,Double money)throws Exception;


    /***
     * 添加用户提现表信息
     * @param memberId  用户id
     * @param aliPayNo  支付宝账户
     * @param alname  支付宝真实名称
     * @param outTradeNo  支付订单信息
     * @param money   金额
     * @return
     */
    public void addUCashOutLog(Integer  memberId,String aliPayNo,String alname,String outTradeNo,Double money)throws Exception;


    /**
     * 查看用户的password是否有效
     * @param memberId
     * @param password
     * @return
     * @throws Exception
     */
    public  boolean   queryPayPassword(Integer  memberId,String password)throws Exception;


    /**
     * 查看用户的password是否有效
     * @param memberId
     * @param outTradeNo  支付订单号
     * @return
     * @throws Exception
     */
    public  boolean   paymentSuccess(Integer  memberId,String outTradeNo)throws Exception;


    /**
     * 根据benefitMemberId 查询用户详细信息
     * @param benefitMemberId
     * @return
     * @throws Exception
     */
    public  List<VcMember>   queryMemberInfo(String benefitMemberId )throws Exception;

    /**
     * 修改状态信息
     * @param list    所以的list
     * @param superiorId   上级id
     * @return
     * @throws Exception
     */
    public  boolean    updateMemberInfo(List<VcMember> list,Integer  superiorId)throws Exception;


    /***
     * 永久VIP
     * @param memberId
     * @return
     */
    public  Integer    updateTypePermanentVIP(Integer  memberId,Integer type,Double money);

    /***
     * 零时VIP
     * @param memberId
     * @return
     */
    public  Integer    updateTypeNOPermanentVIP(Integer  memberId,Integer type,Double money);

}
