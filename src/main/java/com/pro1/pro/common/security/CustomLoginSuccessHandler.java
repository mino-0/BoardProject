package com.pro1.pro.common.security;

import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.common.util.NetUtils;
import com.pro1.pro.common.domain.LoginLog;
import com.pro1.pro.domain.Member;
import com.pro1.pro.common.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private  LoginLogService service;
    //로그인 성공 처리자 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();
        log.info("userId =" + member.getUserId());

        //로그인에 성공한 사용자의 IP정보와 사용자 정보를 로깅처리한다.
        String remoteAddr = NetUtils.getIp(request);

        log.info("remoteAddr = " + remoteAddr);

        LoginLog loginLog = new LoginLog();

        loginLog.setUserNo(member.getUserNo());
        loginLog.setUserId(member.getUserId());
        loginLog.setRemoteAddr(remoteAddr);

        try {
            service.register(loginLog);
        } catch (Exception e) {

        }

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
