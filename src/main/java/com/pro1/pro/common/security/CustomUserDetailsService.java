package com.pro1.pro.common.security;

import com.pro1.pro.common.security.domain.CustomUser;
import com.pro1.pro.domain.Member;
import com.pro1.pro.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository repository;
    //사용자정의 유저 상세 클래스 메서드
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("userName = " + userName);

        //userName은 사용자명이 아니라 사용자 아이디를 의미한다.
        Member member = repository.findByUserId(userName).get(0);

        log.info("member =" + member);

        return member == null ? null : new CustomUser(member);
    }


}
