package com.pro1.pro.common.security;

import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    //로그인 성공 처리자 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();
        log.info("userId =" + member.getUserId());

        super.onAuthenticationSuccess(request,response,authentication);
    }
}
