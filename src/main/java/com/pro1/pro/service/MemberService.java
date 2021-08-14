package com.pro1.pro.service;

import com.pro1.pro.domain.Member;

import java.util.List;

public interface MemberService {
    public void register(Member member) throws Exception;

    public List<Member> list() throws Exception;

    public Member read(Long userNo) throws Exception;

    public void modify(Member member) throws Exception;

    public void remove(Long userNo) throws Exception;

    public long countAll() throws Exception;

    public void setupAdmin(Member member) throws Exception;

    public int getCoin(Long userNo) throws Exception;


}
