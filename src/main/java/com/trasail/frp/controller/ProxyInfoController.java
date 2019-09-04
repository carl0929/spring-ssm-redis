package com.trasail.frp.controller;

import com.trasail.frp.common.utils.AnalyseResult;
import com.trasail.frp.common.utils.ErrorCodes;
import com.trasail.frp.entity.ChannelInfo;
import com.trasail.frp.entity.ProxyUser;
import com.trasail.frp.entity.ProxyUserBeans;
import com.trasail.frp.entity.ReturnEntity;
import com.trasail.frp.entity.UserInfo;
import com.trasail.frp.service.IChannelInfoService;
import com.trasail.frp.service.IProxyInfoSerice;
import com.trasail.frp.service.ISafeRequestParserService;
import com.trasail.frp.service.ISessionManageService;
import com.trasail.frp.service.IUserInfoService;
import com.trasail.frp.service.ProxyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ProxyInfoController {

    @Autowired
    private IProxyInfoSerice proxyInfoSerice;

    @Autowired
    private ISafeRequestParserService mParserService;

    @Autowired
    private IUserInfoService mUserInfoService;

    @Autowired
    private ISessionManageService mSessionManageService;

    @Autowired
    private ProxyUserService proxyUserService;

    @Autowired
    private IChannelInfoService channelInfoService;

    @RequestMapping(value = "request/proxy")
    public void recordProxyinfo(HttpServletRequest request){
        String uid = request.getParameter("uid");
        String proxyName = request.getParameter("proxyName");
        String country = request.getParameter("country");
        String op = request.getParameter("op");
        String env = request.getParameter("env");
        //验证用户信息

        //根据国家及流量类型选出可做任务的用户


        //查询用户是否有可做的任务

        //查询用户是否在线

        //组织返回信息给设备

        //调用sdk后台接口,通知该用户所做任务正在执行（sdk后台修改流水）

        //结束

    }


    @RequestMapping(value = "test")
    @ResponseBody
    public String test(HttpServletRequest request){
        ProxyUser proxyUser = new ProxyUser();
        proxyUser.setsName("name");
        proxyUser.setsPwd("password");
        proxyUser.setAppName("appname");
        proxyUser.setCountry("th");
        proxyUser.setUserId("usseridddd");
        proxyUser.setOp("operator");
        proxyUser.setEnv(1);
        boolean b = proxyUserService.insertProxyUserInfo(proxyUser);
        if(b){
            return "ok";
        }else {
            return "not ok! ";
        }

    }

    @RequestMapping(value = "t2")
    @ResponseBody
    public String test2(HttpServletRequest request){
        ChannelInfo chanInfoByChanNo = channelInfoService.getChanInfoByChanNo("20190119002744");
        return chanInfoByChanNo.toString();
    }


    @RequestMapping(value = "report/proxy")
    public String requestProxy(HttpServletRequest request){

        AnalyseResult<ProxyUserBeans.Request, ProxyUserBeans.Response> requestData =
                mParserService.deserializeRequestFirstLayer(
                        request, ProxyUserBeans.Request.class, true);
        // 如果失败，直接发送响应
        if(!requestData.mIsSucceed) {
            ProxyUserBeans.Response responseObj = new ProxyUserBeans.Response();
            responseObj.setErrorInfo("请求数据解析失败！");
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("request请求数据解析失败，数据为：{0}",requestData.mRequestObj));
            AnalyseResult<Object, ProxyUserBeans.Response> response =
                    mParserService.generateResponseStr(
                            requestData.mErrCode, requestData.mChanId, responseObj);
            return response.mResult;
        }

        // 未解析到有效数据
        if(null == requestData.mRequestObj) {
            ProxyUserBeans.Response responseObj = new ProxyUserBeans.Response();
            responseObj.setErrorInfo("请求数据解析成功，但无请求数据！");
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("request请求数据解析成功，但无请求数据：{0}",requestData.mRequestObj));
            AnalyseResult<Object, ProxyUserBeans.Response> response =
                    mParserService.generateResponseStr(
                            requestData.mErrCode, requestData.mChanId, responseObj);
            return response.mResult;
        }

        UserInfo user = mUserInfoService.findRecordByKWs(
                requestData.mRequestObj.getUserId(), requestData.mRequestObj.getAppName(), requestData.mChanId);
        if(null == user) {
            AnalyseResult<Object, ProxyUserBeans.Response> response =
                    mParserService.generateResponseStr(ErrorCodes.M_ERR_CODE_USER_UNREGISTER, requestData.mChanId, null);
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("report用户未注册，数据为：{0}",requestData.mRequestObj));
            return response.mResult;
        }

        // 查询用户是否已经登陆
        ReturnEntity retRst = mSessionManageService.checkSessionEstablished(
                requestData.mRequestObj.getUserId(), requestData.mRequestObj.getAppName(),
                requestData.mChanId, requestData.mRequestObj.getSessionId());
        if(!retRst.mIsSucceed) {
            AnalyseResult<Object, ProxyUserBeans.Response> response =
                    mParserService.generateResponseStr(
                            retRst.mErrorCode, requestData.mChanId, null);
            Logger.getAnonymousLogger().log(Level.INFO, MessageFormat.format("report用户未登录，数据为：{0}",requestData.mRequestObj));
            return response.mResult;
        }

        //执行插入操作
        ProxyUser proxyUser = new ProxyUser();
        proxyUser.setsName(requestData.mRequestObj.getsName());
        proxyUser.setsPwd(requestData.mRequestObj.getsPwd());
        proxyUser.setAppName(requestData.mRequestObj.getAppName());
        proxyUser.setCountry(requestData.mRequestObj.getCountry());
        proxyUser.setUserId(requestData.mRequestObj.getUserId());
        proxyUser.setOp(requestData.mRequestObj.getOp());
        proxyUser.setEnv(requestData.mRequestObj.getEnv());
        if(proxyUserService.insertProxyUserInfo(proxyUser)){
            return "ok";
        }else {
            return "fail";
        }

    }




}
