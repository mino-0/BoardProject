package com.pro1.pro.repository;

import com.pro1.pro.domain.CodeDetail;
import com.pro1.pro.domain.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {
    @Query("SELECT max(cd.sortSeq) FROM CodeDetail  cd where cd.groupCode=?1")
    public List<Object[]> getMaxSortSeq(String groupCode);
}
