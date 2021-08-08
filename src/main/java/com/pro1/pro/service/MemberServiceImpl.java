package com.pro1.pro.service;

import com.pro1.pro.domain.Member;
import com.pro1.pro.domain.MemberAuth;
import com.pro1.pro.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;

    @Override
    public void register(Member member) throws Exception {
        Member memberEntity = new Member();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_MEMBER");
        memberEntity.addAuth(memberAuth);

        repository.save(memberEntity);
    }

    @Override
    public List<Member> list() throws Exception {
        List<Object[]> valueArrays = repository.listAllMember();
        List<Member> memberList = new ArrayList<Member>();
        for (Object[] valueArray : valueArrays) {
            Member member = new Member();

            member.setUserNo((Long) valueArray[0]);
            member.setUserId((String) valueArray[1]);
            member.setUserPw((String) valueArray[2]);
            member.setUserName((String) valueArray[3]);
            member.setJob((String) valueArray[4]);
            member.setCoin((int) valueArray[5]);
            member.setRegDate((LocalDateTime) valueArray[6]);

            memberList.add(member);
        }
        return memberList;
    }
}
