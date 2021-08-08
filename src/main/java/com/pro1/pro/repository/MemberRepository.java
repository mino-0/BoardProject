package com.pro1.pro.repository;

import com.pro1.pro.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("SELECT m.userNo, m.userId, m.userPw,m.userName,cd.codeName,m.coin,m.regDate "
            + "FROM Member m "
            + "INNER Join CodeDetail cd ON cd.codeValue = m.job "
            + "inner join CodeGroup  cg on cg.groupCode = cd.groupCode "
            + "Where cg.groupCode = 'A01' ORDER BY m.regDate DESC ")
    public List<Object[]> listAllMember();
}
