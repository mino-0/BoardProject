package com.pro1.pro.service;

import com.pro1.pro.domain.Member;

import java.util.List;

public interface MemberService {
    public void register(Member member) throws Exception;
    public List<Member> list()throws Exception;
}
